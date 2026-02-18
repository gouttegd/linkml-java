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

import org.incenp.linkml.schema.model.ClassDefinition;
import org.incenp.linkml.schema.model.Prefix;
import org.incenp.linkml.schema.model.SlotDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SchemaDocumentTest {

    @Test
    void testReadingSimpleSchema() {
        File schemaFile = new File("src/test/resources/schemas/organisms-1.yaml");
        try {
            SchemaDocument doc = new SchemaDocument(schemaFile);

            Assertions.assertEquals("organisms", doc.getRootSchema().getName());
            Assertions.assertEquals("https://example.org/organisms", doc.getRootSchema().getId().toString());
            Assertions.assertEquals("string", doc.getRootSchema().getDefaultRange().getName());

            Assertions.assertEquals(2, doc.getRootSchema().getClasses().size());

            // This indirectly tests that the linkml:types schema has been imported
            Assertions.assertEquals("A character string", doc.getRootSchema().getDefaultRange().getDescription());

            ClassDefinition organismClass = doc.getClassDefinition("Organism");
            ClassDefinition organismCollectionClass = doc.getClassDefinition("OrganismCollection");

            Assertions.assertEquals(2, organismCollectionClass.getAttributes().size());
            SlotDefinition organismsSlotDefinition = doc.getAttribute(organismCollectionClass.getName(), "organisms");
            Assertions.assertEquals("organisms", organismsSlotDefinition.getName());
            Assertions.assertTrue(organismsSlotDefinition.getMultivalued());
            Assertions.assertTrue(organismsSlotDefinition.getInlined());
            Assertions.assertTrue(organismsSlotDefinition.getInlinedAsList());
            Assertions.assertNotNull(organismsSlotDefinition.getRange());
            Assertions.assertEquals(organismClass, organismsSlotDefinition.getRange());

            Assertions.assertEquals(3, organismClass.getAttributes().size());
            SlotDefinition idSlot = doc.getAttribute(organismClass.getName(), "id");
            SlotDefinition hasParentSlot = doc.getAttribute(organismClass.getName(), "has_parent");
            Assertions.assertTrue(idSlot.getIsIdentifier());
            Assertions.assertEquals(organismClass, hasParentSlot.getRange());
            Assertions.assertNull(hasParentSlot.getMultivalued());
            Assertions.assertNull(hasParentSlot.getInlined());
            Assertions.assertNull(hasParentSlot.getInlinedAsList());

            Assertions.assertEquals(2, organismClass.getSlots().size());

        } catch ( IOException | InvalidSchemaException e ) {
            Assertions.fail("Unexpected exception", e);
        }
    }

    @Test
    void testReadingSimpleSchema2() {
        File schemaFile = new File("src/test/resources/schemas/organisms.yaml");
        SchemaDocument doc = null;
        try {
            doc = new SchemaDocument(schemaFile);
        } catch ( IOException | InvalidSchemaException e ) {
            Assertions.fail("Unexpected exception", e);
        }

        SlotDefinition idSlot = doc.getSlot("id");
        Assertions.assertNotNull(idSlot);
        SlotDefinition idSlotUsage = doc.getSlotUsage("Organism", "id");
        Assertions.assertNotNull(idSlotUsage);
        Assertions.assertEquals(idSlot, idSlotUsage.getGlobalSlot());
    }

    @Test
    void testReadingPrefixesBlock() {
        File schemaFile = new File("src/test/resources/schemas/organisms-1.yaml");
        SchemaDocument schemaDoc = null;
        try {
            schemaDoc = new SchemaDocument(schemaFile);
        } catch ( IOException | InvalidSchemaException e ) {
            Assertions.fail("Unexpected exception", e);
        }

        Assertions.assertNotNull(schemaDoc.getRootSchema().getPrefixes());
        Assertions.assertEquals(1, schemaDoc.getRootSchema().getPrefixes().size());

        Prefix pfx = schemaDoc.getRootSchema().getPrefixes().get(0);
        Assertions.assertEquals("linkml", pfx.getPrefixName());
        Assertions.assertEquals("https://w3id.org/linkml/", pfx.getIriPrefix());
    }

    @Test
    void testReadingImportsBlock() {
        File schemaFile = new File("src/test/resources/schemas/organisms-1.yaml");
        SchemaDocument schemaDoc = null;
        try {
            schemaDoc = new SchemaDocument(schemaFile);
        } catch ( IOException | InvalidSchemaException e ) {
            Assertions.fail("Unexpected exception", e);
        }

        Assertions.assertNotNull(schemaDoc.getRootSchema().getImports());
        Assertions.assertEquals(1, schemaDoc.getRootSchema().getImports().size());
        Assertions.assertEquals("linkml:types", schemaDoc.getRootSchema().getImports().get(0));
    }

    @Test
    void testReadingTypes() {
        File schemaFile = new File("src/main/resources/types.yaml");
        SchemaDocument doc = null;
        try {
            doc = new SchemaDocument(schemaFile);
        } catch ( IOException | InvalidSchemaException e ) {
            Assertions.fail("Unexpected exception", e);
        }

        Assertions.assertEquals(19, doc.getRootSchema().getTypes().size());
    }

    @Test
    void testChainingImports() {
        File schemaFile = new File("src/test/resources/schemas/organism-collection.yaml");
        SchemaDocument doc = null;
        try {
            doc = new SchemaDocument(schemaFile);
        } catch ( IOException | InvalidSchemaException e ) {
            Assertions.fail("Unexpected exception", e);
        }

        Assertions.assertEquals(2, doc.getImports().size());
        Assertions.assertEquals("A character string", doc.getRootSchema().getDefaultRange().getDescription());
    }

    @Test
    void testNoInfiniteImportLoop() {
        File schemaFile = new File("src/test/resources/schemas/loop-a.yaml");
        SchemaDocument doc = null;
        try {
            doc = new SchemaDocument(schemaFile);
        } catch ( IOException | InvalidSchemaException e ) {
            Assertions.fail("Unexpected exception", e);
        }

        Assertions.assertEquals(2, doc.getImports().size());
    }

    @Test
    void testAttributesNotSharedAccrossClasses() {
        File schemaFile = new File("src/test/resources/schemas/organisms-1.yaml");
        SchemaDocument schemaDoc = null;
        try {
            schemaDoc = new SchemaDocument(schemaFile);
        } catch ( IOException | InvalidSchemaException e ) {
            Assertions.fail("Unexpected exception", e);
        }

        SlotDefinition organismCollectionNameSlot = schemaDoc.getAttribute("OrganismCollection", "name");
        SlotDefinition organismNameSlot = schemaDoc.getAttribute("Organism", "name");

        Assertions.assertNotNull(organismCollectionNameSlot);
        Assertions.assertNotNull(organismNameSlot);
        Assertions.assertTrue(organismCollectionNameSlot != organismNameSlot);
    }

    @Test
    void testFailOnMissingReferences() {
        File schemaFile = new File("src/test/resources/schemas/missing-ref.yaml");
        try {
            new SchemaDocument(schemaFile);
            Assertions.fail("Exception not thrown for a missing reference");
        } catch ( IOException e ) {
            Assertions.fail("Unexpected exception");
        } catch ( InvalidSchemaException e ) {
            Assertions.assertEquals("Cannot dereference 'inexisting_type': no such object", e.getCause().getMessage());
        }
    }

    @Test
    void testFailOnInvalidReferences() {
        File schemaFile = new File("src/test/resources/schemas/invalid-ref.yaml");
        try {
            new SchemaDocument(schemaFile);
            Assertions.fail("Exception not thrown for an invalid reference");
        } catch ( IOException e ) {
            Assertions.fail("Unexpected exception");
        } catch ( InvalidSchemaException e ) {
            Assertions.assertEquals("Cannot dereference 'Organism': invalid type", e.getCause().getMessage());
        }
    }
}
