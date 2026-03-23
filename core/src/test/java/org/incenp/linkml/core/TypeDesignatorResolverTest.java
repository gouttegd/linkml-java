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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.incenp.linkml.core.sample.BaseSelfDesignatedClass;
import org.incenp.linkml.core.sample.BaseURISelfDesignatedClass;
import org.incenp.linkml.core.sample.DerivedCurieSelfDesignatedClass;
import org.incenp.linkml.core.sample.DerivedSelfDesignatedClass;
import org.incenp.linkml.core.sample.DerivedURISelfDesignatedClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeDesignatorResolverTest {

    private final static String TEST_NS = "https://incenp.org/dvlpt/linkml-java/tests/samples#";

    TypeDesignatorResolver resolver = new TypeDesignatorResolver();

    @Test
    void testResolveSingleDesignator() {
        ClassInfo base = ClassInfo.get(BaseSelfDesignatedClass.class);
        ClassInfo resolved = resolver.resolve("DerivedSelfDesignatedClass", base);
        Assertions.assertEquals(resolved, ClassInfo.get(DerivedSelfDesignatedClass.class));

        resolved = resolver.resolve("BaseSelfDesignatedClass", base);
        Assertions.assertEquals(resolved, base);

        resolved = resolver.resolve("SimpleDict", base);
        Assertions.assertNull(resolved);
    }

    @Test
    void testResolveSingleURIDesignator() {
        ClassInfo base = ClassInfo.get(BaseURISelfDesignatedClass.class);
        ClassInfo resolved = resolver.resolve(TEST_NS + "DerivedURISelfDesignatedClass", base);
        Assertions.assertEquals(resolved, ClassInfo.get(DerivedURISelfDesignatedClass.class));
    }

    @Test
    void testResolveMultivaluedDesignator() {
        ClassInfo base = ClassInfo.get(BaseSelfDesignatedClass.class);
        ClassInfo resolved = null;
        List<String> designators = new ArrayList<>();

        // Nominal case
        designators.add("BaseSelfDesignatedClass");
        designators.add("DerivedSelfDesignatedClass");
        resolved = resolver.resolve(designators, base);
        Assertions.assertEquals(resolved, ClassInfo.get(DerivedSelfDesignatedClass.class));

        // Order of designators should not matter
        designators.add("DerivedSelfDesignatedClass");
        designators.add("BaseSelfDesignatedClass");
        resolved = resolver.resolve(designators, base);
        Assertions.assertEquals(resolved, ClassInfo.get(DerivedSelfDesignatedClass.class));

        // Only one designator that is the base class itself
        designators.clear();
        designators.add("BaseSelfDesignatedClass");
        resolved = resolver.resolve(designators, base);
        Assertions.assertEquals(resolved, base);

        // Only one designator that is the leaf class
        designators.clear();
        designators.add("DerivedSelfDesignatedClass");
        resolved = resolver.resolve(designators, base);
        Assertions.assertEquals(resolved, ClassInfo.get(DerivedSelfDesignatedClass.class));

        // Spurious designators pointing to out-of-tree classes are ignored
        designators.add("BaseSelfDesignatedClass");
        designators.add("SimpleIdentifiableClass");
        designators.add("DerivedSelfDesignatedClass");
        designators.add("SimpleClass");
        resolved = resolver.resolve(designators, base);
        Assertions.assertEquals(resolved, ClassInfo.get(DerivedSelfDesignatedClass.class));
    }

    @Test
    void testGetSimpleDesignator() throws LinkMLRuntimeException {
        Assertions.assertEquals("BaseSelfDesignatedClass",
                resolver.getDesignator(ClassInfo.get(BaseSelfDesignatedClass.class)));

        Assertions.assertEquals("DerivedSelfDesignatedClass",
                resolver.getDesignator(ClassInfo.get(DerivedSelfDesignatedClass.class)));
    }

    @Test
    void testGetSimpleURIDesignator() throws LinkMLRuntimeException {
        Assertions.assertEquals(URI.create(TEST_NS + "DerivedURISelfDesignatedClass"),
                resolver.getDesignator(ClassInfo.get(DerivedURISelfDesignatedClass.class)));
    }

    @Test
    void testGetSimpleCurieDesignator() throws LinkMLRuntimeException {
        Assertions.assertEquals(TEST_NS + "DerivedCurieSelfDesignatedClass",
                resolver.getDesignator(ClassInfo.get(DerivedCurieSelfDesignatedClass.class)));
    }

    @Test
    void testGetMultivaluedDesignator() throws LinkMLRuntimeException {
        List<Object> designators = resolver.getDesignators(ClassInfo.get(DerivedSelfDesignatedClass.class));
        Assertions.assertEquals(2, designators.size());
        Assertions.assertEquals("BaseSelfDesignatedClass", designators.get(0));
        Assertions.assertEquals("DerivedSelfDesignatedClass", designators.get(1));
    }
}
