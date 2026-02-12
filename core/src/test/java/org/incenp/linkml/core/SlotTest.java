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

import org.incenp.linkml.core.sample.ContainerOfInlinedObjects;
import org.incenp.linkml.core.sample.ContainerOfReferences;
import org.incenp.linkml.core.sample.ContainerOfSimpleObjects;
import org.incenp.linkml.core.sample.ExtraSimpleDict;
import org.incenp.linkml.core.sample.SimpleClass;
import org.incenp.linkml.core.sample.SimpleDict;
import org.incenp.linkml.core.sample.SimpleIdentifiableClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SlotTest {

    @Test
    void testMultivalued() throws LinkMLRuntimeException {
        Slot singleValuedSlot = Slot.getSlot(SimpleClass.class, "foo");
        Assertions.assertFalse(singleValuedSlot.isMultivalued());

        Slot multiValuedSlot = Slot.getSlot(SimpleClass.class, "foos");
        Assertions.assertTrue(multiValuedSlot.isMultivalued());
    }

    @Test
    void testGetLinkMLName() throws LinkMLRuntimeException {
        Slot sameNameSlot = Slot.getSlot(SimpleClass.class, "foo");
        Assertions.assertEquals("foo", sameNameSlot.getLinkMLName());

        Slot diffNameSlot = Slot.getSlot(SimpleClass.class, "bar");
        Assertions.assertEquals("the_bar", diffNameSlot.getLinkMLName());
    }

    @Test
    void testIsIdentifier() throws LinkMLRuntimeException {
        Slot identifierSlot = Slot.getSlot(SimpleIdentifiableClass.class, "id");
        Assertions.assertTrue(identifierSlot.isIdentifier());

        Slot nonIdentifierSlot = Slot.getSlot(SimpleIdentifiableClass.class, "foo");
        Assertions.assertFalse(nonIdentifierSlot.isIdentifier());
    }

    @Test
    void testGetInnerType() throws LinkMLRuntimeException {
        Slot singleValuedSlot = Slot.getSlot(ContainerOfSimpleObjects.class, "single");
        Assertions.assertEquals(SimpleClass.class, singleValuedSlot.getInnerType());

        Slot multiValuedSlot = Slot.getSlot(ContainerOfSimpleObjects.class, "multiple");
        Assertions.assertEquals(SimpleClass.class, multiValuedSlot.getInnerType());
    }

    @Test
    void testGetInliningMode() throws LinkMLRuntimeException {
        Slot noInliningSlot = Slot.getSlot(ContainerOfReferences.class, "multiple");
        Assertions.assertEquals(InliningMode.NO_INLINING, noInliningSlot.getInliningMode());

        Slot inlinedAsDictSlot = Slot.getSlot(ContainerOfInlinedObjects.class, "inlinedAsDict");
        Assertions.assertEquals(InliningMode.DICT, inlinedAsDictSlot.getInliningMode());
    }


    void testGetBooleanSlot() throws LinkMLRuntimeException {
        Slot booleanSlot = Slot.getSlot(SimpleClass.class, "baz");
        Assertions.assertNotNull(booleanSlot);
    }

    @Test
    void testGetIdentifierSlot() {
        Slot identifierSlot = Slot.getIdentifierSlot(SimpleIdentifiableClass.class);
        Assertions.assertNotNull(identifierSlot);
        Assertions.assertEquals("id", identifierSlot.getLinkMLName());

        identifierSlot = Slot.getIdentifierSlot(SimpleClass.class);
        Assertions.assertNull(identifierSlot);
    }

    @Test
    void testGetPrimaryValueSlot() {
        Slot primarySlot = Slot.getPrimaryValueSlot(SimpleDict.class);
        Assertions.assertEquals("value", primarySlot.getLinkMLName());

        primarySlot = Slot.getPrimaryValueSlot(ExtraSimpleDict.class);
        Assertions.assertEquals("value", primarySlot.getLinkMLName());

        primarySlot = Slot.getPrimaryValueSlot(SimpleClass.class);
        Assertions.assertNull(primarySlot);
    }

    @Test
    void testAccessors() throws LinkMLRuntimeException {
        SimpleClass sc = new SimpleClass();
        Slot fooSlot = Slot.getSlot(SimpleClass.class, "foo");
        fooSlot.setValue(sc, "a string");
        Assertions.assertEquals("a string", sc.getFoo());
        Assertions.assertEquals("a string", fooSlot.getValue(sc));
    }

    @Test
    void testGetAllSlotsForClass() {
        HashMap<String, Slot> slots = new HashMap<>();
        for ( Slot slot : Slot.getSlots(SimpleClass.class) ) {
            slots.put(slot.getLinkMLName(), slot);
        }
        Assertions.assertTrue(slots.containsKey("foo"));
        Assertions.assertTrue(slots.containsKey("the_bar"));
        Assertions.assertTrue(slots.containsKey("baz"));
        Assertions.assertTrue(slots.containsKey("foos"));
    }
}
