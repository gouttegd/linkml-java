/*
 * LinkML-Java - LinkML library for Java
 * Copyright © 2026 Damien Goutte-Gattat
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.incenp.linkml.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.model.Prefix;
import org.incenp.linkml.model.SchemaDefinition;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Represents an entire LinkML schema.
 */
public class SchemaDocument {

    private final static String TYPES_SCHEMA = "https://w3id.org/linkml/types.yaml";
    private final static String INVALID_YAML = "Cannot read YAML document";
    private final static String INVALID_LINKML = "Cannot parse schema from YAML document";
    private final static String UNRESOLVABLE_IMPORT = "Cannot resolve import name '%s'";

    private SchemaDefinition rootSchema;
    private List<SchemaDefinition> importedSchemas = new ArrayList<>();

    /**
     * Creates a new instance from the specified file.
     * 
     * @param source The file from which to parse the LinkML schema.
     * @throws IOException            If we cannot read the specified file.
     * @throws InvalidSchemaException If the file is not a valid schema.
     */
    public SchemaDocument(File source) throws IOException, InvalidSchemaException {
        rootSchema = parseSchema(source);
    }

    /**
     * Gets the top-level schema.
     * <p>
     * This is the schema contained directly in the file given to the constructor.
     */
    public SchemaDefinition getRootSchema() {
        return rootSchema;
    }

    /**
     * Gets the schemas that are (directly or not) imported by the top-level schema.
     */
    public Collection<SchemaDefinition> getImports() {
        return importedSchemas;
    }

    // The entry point for the actual parsing code
    private SchemaDefinition parseSchema(File file)
            throws IOException, InvalidSchemaException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ConverterContext ctx = ConverterContext.getLinkMLContext();

        // Parse the top-level schema and all its imports recursively
        SchemaDefinition schema = parseSchema(new FileSchemaSource(file), mapper, ctx);

        try {
            // All schemas have been read, so we can resolve all references
            ctx.finalizeAssignments();
        } catch ( LinkMLRuntimeException e ) {
            // We consider a missing reference within a schema as a fatal error. This is
            // stricter than what LinkML-Py does, but I believe this is the correct thing to
            // do -- a schema with missing references should not be trusted for anything.
            throw new InvalidSchemaException(INVALID_LINKML, e);
        }

        return schema;
    }

    // Parses a schema and its imports
    private SchemaDefinition parseSchema(ISchemaSource source, ObjectMapper mapper, ConverterContext ctx)
            throws IOException, InvalidSchemaException {
        SchemaDefinition schema = new SchemaDefinition();
        Object raw;
        try {
            raw = mapper.readValue(source.getStream(), Map.class);
        } catch ( StreamReadException | DatabindException e ) {
            // Failure to parse the YAML is considered a invalid schema, not a I/O error
            throw new InvalidSchemaException(INVALID_YAML, e);
        }

        try {
            ctx.getConverter(SchemaDefinition.class).convertTo(raw, schema, ctx);
        } catch ( LinkMLRuntimeException e ) {
            throw new InvalidSchemaException(INVALID_LINKML, e);
        }

        if ( schema.getImports() != null ) {
            for ( String importName : schema.getImports() ) {
                // FIXME: We should avoid reading the same schema twice (if the same schema is
                // mentioned several time in the import chain).
                ISchemaSource importSource = resolveImport(importName, schema, source.getBase());
                SchemaDefinition importedSchema = parseSchema(importSource, mapper, ctx);
                importedSchemas.add(importedSchema);
            }
        }

        return schema;
    }

    private ISchemaSource resolveImport(String name, SchemaDefinition schema, String base)
            throws InvalidSchemaException {
        if ( !name.contains(":") ) {
            // Local file, relative to the directory containing the importing schema
            if ( base != null ) {
                return new FileSchemaSource(base + File.separator + name + ".yaml");
            } else {
                return new FileSchemaSource(name + ".yaml");
            }
        }

        // If not a local file, then it should be a CURIE
        String[] items = name.split(":", 2);
        if ( schema.getPrefixes() != null ) {
            for ( Prefix pfx : schema.getPrefixes() ) {
                if ( pfx.getPrefixName().equals(items[0]) ) {
                    String url = pfx.getIriPrefix() + items[1] + ".yaml";
                    if ( url.equals(TYPES_SCHEMA) ) {
                        // Redirect the standard linkml:types schema to the embedded version. That
                        // schema is expected to be imported in virtually all LinkML schemas, so we
                        // don't want to have to always fetch it from a remote server.
                        // FIXME: We should probably provide a way to override this behaviour.
                        return new EmbeddedSchemaSource("types.yaml");
                    }
                    try {
                        return new URLSchemaSource(url);
                    } catch ( MalformedURLException e ) {
                        throw new InvalidSchemaException(String.format(UNRESOLVABLE_IMPORT, name), e);
                    }
                }
            }
        }

        // Failure to resolve the CURIE into a URL is a fatal error
        throw new InvalidSchemaException(String.format(UNRESOLVABLE_IMPORT, name));
    }
}
