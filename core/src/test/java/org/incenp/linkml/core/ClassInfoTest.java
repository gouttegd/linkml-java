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

import org.incenp.linkml.core.sample.ExtraSimpleDict;
import org.incenp.linkml.core.sample.SimpleClass;
import org.incenp.linkml.core.sample.SimpleDict;
import org.incenp.linkml.core.sample.SimpleIdentifiableClass;
import org.incenp.linkml.core.sample.SimpleKeyableClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassInfoTest {

    @Test
    void testDetectClassType() {
        Assertions.assertNull(ClassInfo.get(Boolean.class));
        Assertions.assertNull(ClassInfo.get(Boolean.TYPE));
        Assertions.assertNull(ClassInfo.get(String.class));
        Assertions.assertNull(ClassInfo.get(InliningMode.class));
        Assertions.assertNotNull(ClassInfo.get(SimpleClass.class));
    }

    @Test
    void testDetectIdentifierSlots() {
        ClassInfo ci = ClassInfo.get(SimpleIdentifiableClass.class);
        Assertions.assertTrue(ci.hasIdentifier());
        Assertions.assertTrue(ci.isGloballyUnique());
        Assertions.assertFalse(ci.isLocallyUnique());

        ci = ClassInfo.get(SimpleKeyableClass.class);
        Assertions.assertTrue(ci.hasIdentifier());
        Assertions.assertFalse(ci.isGloballyUnique());
        Assertions.assertTrue(ci.isLocallyUnique());

        ci = ClassInfo.get(SimpleClass.class);
        Assertions.assertFalse(ci.hasIdentifier());
        Assertions.assertFalse(ci.isGloballyUnique());
        Assertions.assertFalse(ci.isLocallyUnique());
    }

    @Test
    void testGetPrimaryValueSlot() {
        Slot primarySlot = ClassInfo.get(SimpleDict.class).getPrimarySlot();
        Assertions.assertEquals("value", primarySlot.getLinkMLName());

        primarySlot = ClassInfo.get(ExtraSimpleDict.class).getPrimarySlot();
        Assertions.assertEquals("value", primarySlot.getLinkMLName());

        primarySlot = ClassInfo.get(SimpleClass.class).getPrimarySlot();
        Assertions.assertNull(primarySlot);
    }

    @Test
    void testDetectSimpleDictEligibility() {
        Assertions.assertTrue(ClassInfo.get(SimpleDict.class).isEligibleForSimpleDict(false));
        Assertions.assertTrue(ClassInfo.get(SimpleDict.class).isEligibleForSimpleDict(true));

        Assertions.assertTrue(ClassInfo.get(ExtraSimpleDict.class).isEligibleForSimpleDict(false));
        Assertions.assertFalse(ClassInfo.get(ExtraSimpleDict.class).isEligibleForSimpleDict(true));

        Assertions.assertFalse(ClassInfo.get(SimpleClass.class).isEligibleForSimpleDict(false));
        Assertions.assertFalse(ClassInfo.get(SimpleIdentifiableClass.class).isEligibleForSimpleDict(false));
    }
}
