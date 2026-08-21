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

import org.incenp.linkml.core.samples.base.BaseCurieSelfDesignatedClass;
import org.incenp.linkml.core.samples.base.BaseSelfDesignatedClass;
import org.incenp.linkml.core.samples.base.DerivedCurieSelfDesignatedClass;
import org.incenp.linkml.core.samples.base.DerivedSelfDesignatedClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AliasTypeDesignatorResolverTest {

    private AliasTypeDesignatorResolver resolver = new AliasTypeDesignatorResolver();

    @Test
    void testAliasResolution() throws LinkMLRuntimeException {
        ClassInfo derivedCurieClass = ClassInfo.get(DerivedCurieSelfDesignatedClass.class);
        resolver.registerAlias(derivedCurieClass, "derived");

        ClassInfo derivedStringClass = ClassInfo.get(DerivedSelfDesignatedClass.class);
        resolver.registerAlias(derivedStringClass, "derived");

        Assertions.assertEquals(derivedCurieClass,
                resolver.resolve("derived", ClassInfo.get(BaseCurieSelfDesignatedClass.class)));

        Assertions.assertEquals(derivedStringClass,
                resolver.resolve("derived", ClassInfo.get(BaseSelfDesignatedClass.class)));
    }

    @Test
    void testFallbackResolution() throws LinkMLRuntimeException {
        ClassInfo base = ClassInfo.get(BaseSelfDesignatedClass.class);
        ClassInfo derived = ClassInfo.get(DerivedSelfDesignatedClass.class);

        Assertions.assertEquals(derived, resolver.resolve("DerivedSelfDesignatedClass", base));
    }

    @Test
    void testReverseResolution() throws LinkMLRuntimeException {
        ClassInfo derivedCurieClass = ClassInfo.get(DerivedCurieSelfDesignatedClass.class);
        resolver.registerAlias(derivedCurieClass, "derived");

        ClassInfo derivedStringClass = ClassInfo.get(DerivedSelfDesignatedClass.class);
        resolver.registerAlias(derivedStringClass, "derived");

        Assertions.assertEquals("derived", resolver.getDesignator(derivedCurieClass));
        Assertions.assertEquals("derived", resolver.getDesignator(derivedStringClass));
    }

    @Test
    void testFallbackReverseResolution() throws LinkMLRuntimeException {
        Assertions.assertEquals("DerivedSelfDesignatedClass",
                resolver.getDesignator(ClassInfo.get(DerivedSelfDesignatedClass.class)));
    }
}
