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

import org.incenp.linkml.model.ClassDefinition;
import org.incenp.linkml.model.Prefix;
import org.incenp.linkml.model.SlotDefinition;
import org.incenp.linkml.model.TypeDefinition;
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

            Assertions.assertEquals(1, doc.getRootSchema().getTypes().size());
            TypeDefinition strType = doc.getRootSchema().getTypes().get(0);
            Assertions.assertEquals("string", strType.getName());
            Assertions.assertEquals("str", strType.getBase());

            ClassDefinition organismClass = null;
            ClassDefinition organismCollectionClass = null;

            for ( ClassDefinition cd : doc.getRootSchema().getClasses() ) {
                if ( cd.getName().equals("OrganismCollection") ) {
                    organismCollectionClass = cd;
                } else if ( cd.getName().equals("Organism") ) {
                    organismClass = cd;
                }
            }

            Assertions.assertEquals(2, organismCollectionClass.getAttributes().size());
            SlotDefinition organismsSlotDefinition = null;
            for ( SlotDefinition sd : organismCollectionClass.getAttributes() ) {
                if ( sd.getName().equals("organisms") ) {
                    organismsSlotDefinition = sd;
                }
            }
            Assertions.assertEquals("organisms", organismsSlotDefinition.getName());
            Assertions.assertTrue(organismsSlotDefinition.isMultivalued());
            Assertions.assertTrue(organismsSlotDefinition.isInlined());
            Assertions.assertTrue(organismsSlotDefinition.isInlinedAsList());
            Assertions.assertNotNull(organismsSlotDefinition.getRange());
            Assertions.assertEquals(organismClass, organismsSlotDefinition.getRange());

            Assertions.assertEquals(3, organismClass.getAttributes().size());
            SlotDefinition idSlot = null;
            SlotDefinition hasParentSlot = null;
            for ( SlotDefinition sd : organismClass.getAttributes() ) {
                if ( sd.getName().equals("id") ) {
                    idSlot = sd;
                } else if ( sd.getName().equals("has_parent") ) {
                    hasParentSlot = sd;
                }
            }
            Assertions.assertTrue(idSlot.isIdentifier());
            Assertions.assertEquals(organismClass, hasParentSlot.getRange());
            Assertions.assertFalse(hasParentSlot.isMultivalued());
            Assertions.assertFalse(hasParentSlot.isInlined());
            Assertions.assertFalse(hasParentSlot.isInlinedAsList());
        } catch ( IOException | InvalidSchemaException e ) {
            Assertions.fail("Unexpected exception", e);
        }
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
}
