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

import java.util.ArrayList;
import java.util.List;

import org.incenp.linkml.model.SlotDefinition;
import org.incenp.linkml.model.TypeDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConverterContextTest {

    @Test
    void testLinkMLContext() {
        ConverterContext linkmlContext = ConverterContext.getLinkMLContext();

        Assertions.assertTrue(linkmlContext.isComplexType(SlotDefinition.class));
        Assertions.assertTrue(linkmlContext.hasIdentifier(SlotDefinition.class));
    }

    @Test
    void testGetObject() throws LinkMLRuntimeException {
        ConverterContext ctx = new ConverterContext();
        SlotDefinition sd = ctx.getObject(SlotDefinition.class, "Foo", false);
        Assertions.assertNull(sd);

        sd = ctx.getObject(SlotDefinition.class, "Foo", true);
        Assertions.assertNotNull(sd);
        Assertions.assertEquals("Foo", sd.getName());

        SlotDefinition sd2 = ctx.getObject(SlotDefinition.class, "Foo", true);
        Assertions.assertNotNull(sd2);
        Assertions.assertTrue(sd == sd2);
    }

    @Test
    void testImmediateAssignment() throws LinkMLRuntimeException {
        ConverterContext ctx = new ConverterContext();
        SlotDefinition sd = new SlotDefinition();

        TypeDefinition td = ctx.getObject(TypeDefinition.class, "string", true);
        Slot rangeSlot = Slot.getSlot(SlotDefinition.class, "range");
        ctx.getObject(rangeSlot, "string", sd);

        Assertions.assertTrue(td == sd.getRange());
    }

    @Test
    void testDelayedAssignment() throws LinkMLRuntimeException {
        ConverterContext ctx = new ConverterContext();
        SlotDefinition sd = new SlotDefinition();
        Slot rangeSlot = Slot.getSlot(SlotDefinition.class, "range");

        ctx.getObject(rangeSlot, "string", sd);
        Assertions.assertNull(sd.getRange());

        TypeDefinition td = ctx.getObject(TypeDefinition.class, "string", true);

        ctx.finalizeAssignments();

        Assertions.assertTrue(td == sd.getRange());
    }

    @Test
    void testMultiValuedDelayedAssignment() throws LinkMLRuntimeException {
        ConverterContext ctx = new ConverterContext();
        SlotDefinition def1 = ctx.getObject(SlotDefinition.class, "foo", true);

        List<Object> list = new ArrayList<>();
        List<String> names = new ArrayList<String>();
        names.add("foo");
        names.add("bar");
        ctx.getObjects(SlotDefinition.class, names, list);

        // Only "foo" should have been assigned, since "bar" is currently unknown
        Assertions.assertEquals(1, list.size());
        Assertions.assertTrue(list.get(0) == def1);

        SlotDefinition def2 = ctx.getObject(SlotDefinition.class, "bar", true);
        ctx.finalizeAssignments();

        // Now we should get both "foo" and "bar"
        Assertions.assertEquals(2, list.size());
        Assertions.assertTrue(list.get(1) == def2);
    }
}
