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

import java.util.HashMap;

import org.incenp.linkml.schema.model.PermissibleValue;
import org.incenp.linkml.schema.model.Prefix;
import org.incenp.linkml.schema.model.SchemaDefinition;
import org.incenp.linkml.schema.model.SlotDefinition;
import org.incenp.linkml.schema.model.TypeDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SlotTest {

    @Test
    void testMultivalued() throws LinkMLRuntimeException {
        Slot singleValuedSlot = Slot.getSlot(SchemaDefinition.class, "defaultPrefix");
        Assertions.assertFalse(singleValuedSlot.isMultivalued());

        Slot multiValuedSlot = Slot.getSlot(SchemaDefinition.class, "types");
        Assertions.assertTrue(multiValuedSlot.isMultivalued());
    }

    @Test
    void testGetLinkMLName() throws LinkMLRuntimeException {
        Slot sameNameSlot = Slot.getSlot(SchemaDefinition.class, "version");
        Assertions.assertEquals("version", sameNameSlot.getLinkMLName());

        Slot diffNameSlot = Slot.getSlot(SchemaDefinition.class, "defaultPrefix");
        Assertions.assertEquals("default_prefix", diffNameSlot.getLinkMLName());
    }

    @Test
    void testIsIdentifier() throws LinkMLRuntimeException {
        Slot identifierSlot = Slot.getSlot(SlotDefinition.class, "name");
        Assertions.assertTrue(identifierSlot.isIdentifier());

        Slot nonIdentifierSlot = Slot.getSlot(SlotDefinition.class, "description");
        Assertions.assertFalse(nonIdentifierSlot.isIdentifier());
    }

    @Test
    void testGetInnerType() throws LinkMLRuntimeException {
        Slot singleValuedSlot = Slot.getSlot(SchemaDefinition.class, "defaultRange");
        Assertions.assertEquals(TypeDefinition.class, singleValuedSlot.getInnerType());

        Slot multiValuedSlot = Slot.getSlot(SchemaDefinition.class, "types");
        Assertions.assertEquals(TypeDefinition.class, multiValuedSlot.getInnerType());
    }

    @Test
    void testGetInliningMode() throws LinkMLRuntimeException {
        Slot noInliningSlot = Slot.getSlot(SchemaDefinition.class, "defaultRange");
        Assertions.assertEquals(InliningMode.NO_INLINING, noInliningSlot.getInliningMode());

        Slot inlinedAsDictSlot = Slot.getSlot(SchemaDefinition.class, "slotDefinitions");
        Assertions.assertEquals(InliningMode.DICT, inlinedAsDictSlot.getInliningMode());
    }

    @Test
    void testGetBooleanSlot() throws LinkMLRuntimeException {
        Slot booleanSlot = Slot.getSlot(SlotDefinition.class, "isIdentifier");
        Assertions.assertNotNull(booleanSlot);

        booleanSlot = Slot.getSlot(SlotDefinition.class, "inlined");
        Assertions.assertNotNull(booleanSlot);
    }

    @Test
    void testGetIdentifierSlot() {
        Slot identifierSlot = Slot.getIdentifierSlot(SlotDefinition.class);
        Assertions.assertNotNull(identifierSlot);
        Assertions.assertEquals("name", identifierSlot.getLinkMLName());

        identifierSlot = Slot.getIdentifierSlot(PermissibleValue.class);
        Assertions.assertNull(identifierSlot);
    }

    @Test
    void testGetPrimaryValueSlot() {
        Slot primarySlot = Slot.getPrimaryValueSlot(Prefix.class);
        Assertions.assertEquals("prefix_reference", primarySlot.getLinkMLName());

        primarySlot = Slot.getPrimaryValueSlot(TypeDefinition.class);
        Assertions.assertNull(primarySlot);
    }

    @Test
    void testAccessors() throws LinkMLRuntimeException {
        SlotDefinition sd = new SlotDefinition();
        Slot nameSlot = Slot.getSlot(SlotDefinition.class, "name");
        nameSlot.setValue(sd, "the slot name");
        Assertions.assertEquals("the slot name", sd.getName());
        Assertions.assertEquals("the slot name", nameSlot.getValue(sd));
    }

    @Test
    void testGetAllSlotsForClass() {
        HashMap<String, Slot> slots = new HashMap<>();
        for (Slot slot : Slot.getSlots(SlotDefinition.class)) {
            slots.put(slot.getLinkMLName(), slot);
        }
        Assertions.assertTrue(slots.containsKey("range"));
        Assertions.assertTrue(slots.containsKey("is_a"));
        Assertions.assertTrue(slots.containsKey("name"));
    }
}
