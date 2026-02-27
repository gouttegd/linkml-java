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
 *   (3) Neither the name of the copyright holder nor the names its
 *   contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.incenp.linkml.core;

import java.util.HashMap;

import org.incenp.linkml.core.sample.ContainerOfInlinedObjects;
import org.incenp.linkml.core.sample.ContainerOfReferences;
import org.incenp.linkml.core.sample.ContainerOfSimpleObjects;
import org.incenp.linkml.core.sample.ExtensibleSimpleClass;
import org.incenp.linkml.core.sample.SimpleClass;
import org.incenp.linkml.core.sample.SimpleIdentifiableClass;
import org.incenp.linkml.core.sample.SimpleKeyableClass;
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
    void testGetLinkURI() throws LinkMLRuntimeException {
        Slot bazSlot = Slot.getSlot(SimpleClass.class, "baz");
        Assertions.assertEquals("https://example.org/baz", bazSlot.getLinkedURI());
    }

    @Test
    void testIsIdentifier() throws LinkMLRuntimeException {
        Slot identifierSlot = Slot.getSlot(SimpleIdentifiableClass.class, "id");
        Assertions.assertTrue(identifierSlot.isIdentifier());
        Assertions.assertTrue(identifierSlot.isGlobalIdentifier());
        Assertions.assertFalse(identifierSlot.isLocalIdentifier());

        identifierSlot = Slot.getSlot(SimpleKeyableClass.class, "id");
        Assertions.assertTrue(identifierSlot.isIdentifier());
        Assertions.assertFalse(identifierSlot.isGlobalIdentifier());
        Assertions.assertTrue(identifierSlot.isLocalIdentifier());

        Slot nonIdentifierSlot = Slot.getSlot(SimpleIdentifiableClass.class, "foo");
        Assertions.assertFalse(nonIdentifierSlot.isIdentifier());
        Assertions.assertFalse(nonIdentifierSlot.isGlobalIdentifier());
        Assertions.assertFalse(nonIdentifierSlot.isLocalIdentifier());
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
        ClassInfo ci = ClassInfo.get(ContainerOfReferences.class);
        Assertions.assertEquals(InliningMode.NO_INLINING, ci.getSlot("single").getInliningMode());
        Assertions.assertEquals(InliningMode.NO_INLINING, ci.getSlot("multiple").getInliningMode());

        ci = ClassInfo.get(ContainerOfInlinedObjects.class);
        Assertions.assertEquals(InliningMode.DICT, ci.getSlot("singleInlined").getInliningMode());
        Assertions.assertEquals(InliningMode.LIST, ci.getSlot("inlinedAsList").getInliningMode());
        Assertions.assertEquals(InliningMode.DICT, ci.getSlot("inlinedAsDict").getInliningMode());
        Assertions.assertEquals(InliningMode.DICT, ci.getSlot("localSingleInlined").getInliningMode());
        Assertions.assertEquals(InliningMode.LIST, ci.getSlot("localInlinedAsList").getInliningMode());
        Assertions.assertEquals(InliningMode.DICT, ci.getSlot("localInlinedAsDict").getInliningMode());

        ci = ClassInfo.get(SimpleClass.class);
        Assertions.assertEquals(InliningMode.IRRELEVANT, ci.getSlot("foo").getInliningMode());
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

    @Test
    void testDeclaringClass() throws LinkMLRuntimeException {
        Slot identifierSlot = Slot.getSlot(ExtensibleSimpleClass.class, "foo");
        Assertions.assertEquals(SimpleClass.class, identifierSlot.getDeclaringClass());
    }
}
