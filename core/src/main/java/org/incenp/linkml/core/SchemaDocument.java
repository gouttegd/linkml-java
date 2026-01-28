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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.incenp.linkml.model.SchemaDefinition;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Represents an entire LinkML schema.
 */
public class SchemaDocument {

    private SchemaDefinition rootSchema;

    /**
     * Creates a new instance from the specified file.
     * 
     * @param source The file from which to parse the LinkML schema.
     * @throws IOException            If we cannot read the specified file.
     * @throws InvalidSchemaException If the file is not a valid schema.
     */
    public SchemaDocument(File source) throws IOException, InvalidSchemaException {
        BufferedReader reader = new BufferedReader(new FileReader(source));
        rootSchema = parseSchemaDefinition(reader);
    }

    /**
     * Gets the top-level schema.
     * <p>
     * This is the schema contained directly in the file given to the constructor.
     */
    public SchemaDefinition getRootSchema() {
        return rootSchema;
    }

    private SchemaDefinition parseSchemaDefinition(Reader reader)
            throws IOException, InvalidSchemaException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Object rawDef;
        try {
            rawDef = mapper.readValue(reader, Map.class);
        } catch ( StreamReadException | DatabindException e ) {
            throw new InvalidSchemaException("Cannot read YAML document", e);
        }

        ConverterContext ctx = ConverterContext.getLinkMLContext();
        ObjectConverter schemaDefConverter = ctx.getConverter(SchemaDefinition.class);

        SchemaDefinition def = new SchemaDefinition();
        try {
            schemaDefConverter.convert(rawDef, def, ctx);
            ctx.finalizeAssignments();
        } catch ( LinkMLRuntimeException e ) {
            throw new InvalidSchemaException("Cannot parse schema from YAML document", e);
        }

        return def;
    }

}
