/*
 * LinkML-Java - LinkML library for Java
 * Copyright © 2026 Damien Goutte-Gattat
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   (1) Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *
 *   (2) Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.  
 *
 *   (3)The name of the author may not be used to endorse or promote
 *   products derived from this software without specific prior written
 *   permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.incenp.linkml.core;

import java.util.ArrayList;
import java.util.List;

import org.incenp.linkml.core.sample.ContainerOfReferences;
import org.incenp.linkml.core.sample.SimpleDict;
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

    @Test
    void testFailingOnInvalidReferences() throws LinkMLRuntimeException {
        ConverterContext ctx = new ConverterContext();

        ContainerOfReferences cor = new ContainerOfReferences();
        Slot singleSlot = Slot.getSlot(ContainerOfReferences.class, "single");

        ctx.getObject(singleSlot, "sic1", cor);
        Assertions.assertNull(cor.getSingle());
        try {
            ctx.finalizeAssignments(true);
            Assertions.fail("Exception not thrown for a missing reference");
        } catch ( LinkMLRuntimeException e ) {
            Assertions.assertEquals("Cannot dereference 'sic1': no such object", e.getMessage());
        }

        ctx.getObject(SimpleDict.class, "sic1", true);
        try {
            ctx.finalizeAssignments(true);
            Assertions.fail("Exception not thrown for an invalid reference");
        } catch ( LinkMLRuntimeException e ) {
            Assertions.assertEquals("Cannot dereference 'sic1': no such object", e.getMessage());
        }
    }

    @Test
    void testCreatingEmptyObjectsOnDereferencing() throws LinkMLRuntimeException {
        ConverterContext ctx = new ConverterContext();

        ContainerOfReferences cor = new ContainerOfReferences();
        Slot singleSlot = Slot.getSlot(ContainerOfReferences.class, "single");

        ctx.getObject(singleSlot, "sic1", cor);
        Assertions.assertNull(cor.getSingle());

        ctx.finalizeAssignments();
        Assertions.assertNotNull(cor.getSingle());
        Assertions.assertEquals("sic1", cor.getSingle().getId());
    }

    @Test
    void testPrefixManager() {
        ConverterContext ctx = new ConverterContext();
        ctx.addPrefix("pfx1", "expansion1/");
        ctx.addPrefix("pfx2:", "expansion1/longer/prefix/");

        Assertions.assertEquals("expansion1/value", ctx.resolve("pfx1:value"));
        Assertions.assertEquals("expansion1/longer/prefix/value", ctx.resolve("pfx2:value"));
        Assertions.assertEquals("pfx3:value", ctx.resolve("pfx3:value"));

        Assertions.assertEquals("pfx1:value", ctx.compact("expansion1/value"));
        Assertions.assertEquals("pfx2:value", ctx.compact("expansion1/longer/prefix/value"));
        Assertions.assertEquals("expansion3/value", ctx.compact("expansion3/value"));
    }
}
