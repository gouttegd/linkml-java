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

import org.incenp.linkml.core.sample.ContainerOfReferences;
import org.incenp.linkml.core.sample.SimpleIdentifiableClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConverterContextTest {

    @Test
    void testGetObject() throws LinkMLRuntimeException {
        ConverterContext ctx = new ConverterContext();
        SimpleIdentifiableClass sic = ctx.getObject(SimpleIdentifiableClass.class, "Foo", false);
        Assertions.assertNull(sic);

        sic = ctx.getObject(SimpleIdentifiableClass.class, "Foo", true);
        Assertions.assertNotNull(sic);
        Assertions.assertEquals("Foo", sic.getId());

        SimpleIdentifiableClass sic2 = ctx.getObject(SimpleIdentifiableClass.class, "Foo", true);
        Assertions.assertNotNull(sic2);
        Assertions.assertTrue(sic == sic2);
    }

    @Test
    void testImmediateAssignment() throws LinkMLRuntimeException {
        ConverterContext ctx = new ConverterContext();
        ContainerOfReferences cor = new ContainerOfReferences();
        SimpleIdentifiableClass sic = ctx.getObject(SimpleIdentifiableClass.class, "sic1", true);
        Slot singleSlot = Slot.getSlot(ContainerOfReferences.class, "single");
        ctx.getObject(singleSlot, "sic1", cor);

        Assertions.assertTrue(sic == cor.getSingle());
    }

    @Test
    void testDelayedAssignment() throws LinkMLRuntimeException {
        ConverterContext ctx = new ConverterContext();
        ContainerOfReferences cor = new ContainerOfReferences();
        Slot singleSlot = Slot.getSlot(ContainerOfReferences.class, "single");

        ctx.getObject(singleSlot, "sic1", cor);
        Assertions.assertNull(cor.getSingle());

        SimpleIdentifiableClass sic = ctx.getObject(SimpleIdentifiableClass.class, "sic1", true);

        ctx.finalizeAssignments();

        Assertions.assertTrue(sic == cor.getSingle());
    }

    @Test
    void testMultiValuedDelayedAssignment() throws LinkMLRuntimeException {
        ConverterContext ctx = new ConverterContext();

        SimpleIdentifiableClass sic1 = ctx.getObject(SimpleIdentifiableClass.class, "sic1", true);

        List<Object> list = new ArrayList<>();
        List<String> names = new ArrayList<>();
        names.add("sic1");
        names.add("sic2");
        ctx.getObjects(SimpleIdentifiableClass.class, names, list);

        // Only "sic1" should have been assigned, since "sic2" is currently unknown
        Assertions.assertEquals(1, list.size());
        Assertions.assertTrue(sic1 == list.get(0));

        SimpleIdentifiableClass sic2 = ctx.getObject(SimpleIdentifiableClass.class, "sic2", true);
        ctx.finalizeAssignments();

        // Now we should get both "sic1" and "sic2"
        Assertions.assertEquals(2, list.size());
        Assertions.assertTrue(sic2 == list.get(1));
    }
}
