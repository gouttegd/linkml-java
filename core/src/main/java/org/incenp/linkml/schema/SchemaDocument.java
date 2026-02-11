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

package org.incenp.linkml.schema;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.schema.model.ClassDefinition;
import org.incenp.linkml.schema.model.EnumDefinition;
import org.incenp.linkml.schema.model.SchemaDefinition;
import org.incenp.linkml.schema.model.SlotDefinition;
import org.incenp.linkml.schema.model.TypeDefinition;

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
    private Set<ISchemaSource> importedSources = new HashSet<>();
    private Map<String, ClassDefinition> allClasses = new HashMap<>();
    private Map<String, SlotDefinition> allSlots = new HashMap<>();
    private Map<String, EnumDefinition> allEnums = new HashMap<>();
    private Map<String, TypeDefinition> allTypes = new HashMap<>();
    private Map<String, Map<String, SlotDefinition>> allAttributes = new HashMap<>();
    private Map<String, Map<String, SlotDefinition>> allSlotUsages = new HashMap<>();

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

    /**
     * Gets all classes defined across the top-level schema and all imported
     * schemas.
     */
    public Collection<ClassDefinition> getAllClasses() {
        return allClasses.values();
    }

    /**
     * Gets all slots defined across the top-level schema and all imported schemas.
     */
    public Collection<SlotDefinition> getAllSlots() {
        return allSlots.values();
    }

    /**
     * Gets all enums defined across the top-level schema and all imported schemas.
     */
    public Collection<EnumDefinition> getAllEnums() {
        return allEnums.values();
    }

    /**
     * Gets all types defined across the top-level schema and all imported schemas.
     */
    public Collection<TypeDefinition> getAllTypes() {
        return allTypes.values();
    }

    /**
     * Gets the definition of the specified class.
     * <p>
     * This looks up the class across the entire imports closure.
     * 
     * @param name The name of the class.
     * @return The corresponding class definition, or <code>null</code> if there is
     *         no class with that name.
     */
    public ClassDefinition getClassDefinition(String name) {
        return allClasses.get(name);
    }

    /**
     * Gets the definition of the specified slot.
     * <p>
     * This looks up the slot across the entire imports closure.
     * 
     * @param name The name of the slot.
     * @return The corresponding slot definition, or <code>null</code> if there is
     *         no slot with that name.
     */
    public SlotDefinition getSlot(String name) {
        return allSlots.get(name);
    }

    /**
     * Gets the definition of the specified enum.
     * <p>
     * This looks up the enum across the entire imports closure.
     * 
     * @param name The name of the enum.
     * @return The corresponding enum definition, or <code>null</code> if there is
     *         no enum with that name.
     */
    public EnumDefinition getEnum(String name) {
        return allEnums.get(name);
    }

    /**
     * Gets the definition of the specified type.
     * <p>
     * This looks up the type across the entire imports closure.
     * 
     * @param name The name of the type.
     * @return The corresponding type definition, or <code>null</code> if there is
     *         no type with that name.
     */
    public TypeDefinition getType(String name) {
        return allTypes.get(name);
    }

    /**
     * Gets the definition of the specified attribute within a class.
     * <p>
     * This looks up the the class entire the entire import closure.
     * 
     * @param className The name of the class.
     * @param attrName  The name of the attribute.
     * @return The corresponding attribute definition, or <code>null</code> if there
     *         is no class with the indicated name or the class has no such
     *         attribute.
     */
    public SlotDefinition getAttribute(String className, String attrName) {
        Map<String, SlotDefinition> attrs = allAttributes.get(className);
        if ( attrs != null ) {
            return attrs.get(attrName);
        }
        return null;
    }

    /**
     * Gets the definition of the specified slot usage within a class.
     * <p>
     * This looks up the class entire the entire import closure.
     * 
     * @param className The name of the class.
     * @param slotName  The name of the slot.
     * @return The corresponding slot usage definition, or <code>null</code> if
     *         there is no class with the indicated name or the class has no slot
     *         usage definition with such a name.
     */
    public SlotDefinition getSlotUsage(String className, String slotName) {
        Map<String, SlotDefinition> slots = allSlotUsages.get(className);
        if ( slots != null ) {
            return slots.get(slotName);
        }
        return null;
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
            ctx.finalizeAssignments(true);
        } catch ( LinkMLRuntimeException e ) {
            throw new InvalidSchemaException(INVALID_LINKML, e);
        }

        return schema;
    }

    // Parses a schema and its imports
    private SchemaDefinition parseSchema(ISchemaSource source, ObjectMapper mapper, ConverterContext ctx)
            throws IOException, InvalidSchemaException {
        SchemaDefinition schema = null;
        Object raw;
        try {
            raw = mapper.readValue(source.getStream(), Map.class);
        } catch ( StreamReadException | DatabindException e ) {
            // Failure to parse the YAML is considered a invalid schema, not a I/O error
            throw new InvalidSchemaException(INVALID_YAML, e);
        }

        try {
            schema = (SchemaDefinition) ctx.getConverter(SchemaDefinition.class).convert(raw, ctx);
        } catch ( LinkMLRuntimeException e ) {
            throw new InvalidSchemaException(INVALID_LINKML, e);
        }

        importedSources.add(source);
        if ( schema.getImports() != null ) {
            for ( String importName : schema.getImports() ) {
                ISchemaSource importSource = resolveImport(importName, schema, source.getBase(), ctx);
                if ( !importedSources.contains(importSource) ) {
                    SchemaDefinition importedSchema = parseSchema(importSource, mapper, ctx);
                    importedSchemas.add(importedSchema);
                }
            }
        }

        // Fill rapid access tables
        if ( schema.getClasses() != null ) {
            for ( ClassDefinition cd : schema.getClasses() ) {
                allClasses.put(cd.getName(), cd);
                HashMap<String, SlotDefinition> attributes = new HashMap<>();
                if ( cd.getAttributes() != null ) {
                    for ( SlotDefinition sd : cd.getAttributes() ) {
                        attributes.put(sd.getName(), sd);
                    }
                }
                allAttributes.put(cd.getName(), attributes);

                HashMap<String, SlotDefinition> slotUsages = new HashMap<>();
                if ( cd.getSlotUsage() != null ) {
                    for ( SlotDefinition sd : cd.getSlotUsage() ) {
                        slotUsages.put(sd.getName(), sd);
                    }
                }
                allSlotUsages.put(cd.getName(), slotUsages);
            }
        }
        if ( schema.getSlotDefinitions() != null ) {
            for ( SlotDefinition sd : schema.getSlotDefinitions() ) {
                allSlots.put(sd.getName(), sd);
            }
        }
        if ( schema.getEnums() != null ) {
            for ( EnumDefinition ed : schema.getEnums() ) {
                allEnums.put(ed.getName(), ed);
            }
        }
        if ( schema.getTypes() != null ) {
            for ( TypeDefinition td : schema.getTypes() ) {
                allTypes.put(td.getName(), td);
            }
        }

        return schema;
    }

    private ISchemaSource resolveImport(String name, SchemaDefinition schema, String base, ConverterContext ctx)
            throws InvalidSchemaException {
        String extName = name + ".yaml";
        if ( !extName.contains(":") ) {
            // Local file, relative to the directory containing the importing schema
            if ( base != null ) {
                return new FileSchemaSource(base + File.separator + extName);
            } else {
                return new FileSchemaSource(extName);
            }
        }

        String resolved = ctx.resolve(extName);
        if ( resolved == extName ) {
            throw new InvalidSchemaException(String.format(UNRESOLVABLE_IMPORT, name));
        }
        if ( resolved.equals(TYPES_SCHEMA) ) {
            // Redirect the standard linkml:types schema to the embedded version. That
            // schema is expected to be imported in virtually all LinkML schemas, so we
            // don't want to have to always fetch it from a remote server.
            // FIXME: We should probably provide a way to override this behaviour.
            return new EmbeddedSchemaSource("types.yaml");
        }
        try {
            return new URLSchemaSource(resolved);
        } catch ( MalformedURLException e ) {
            throw new InvalidSchemaException(String.format(UNRESOLVABLE_IMPORT, name), e);
        }
    }
}
