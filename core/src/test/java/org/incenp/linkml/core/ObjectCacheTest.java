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

import org.incenp.linkml.core.sample.ExtendedIdentifiableClass;
import org.incenp.linkml.core.sample.SimpleDict;
import org.incenp.linkml.core.sample.SimpleIdentifiableClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectCacheTest {

    @Test
    void testCachedObjectIsNotCreatedTwice() throws LinkMLRuntimeException {
        ObjectCache cache = new ObjectCache();
        SimpleIdentifiableClass sic1 = cache.getObject(SimpleIdentifiableClass.class, "sic1", true);

        Assertions.assertEquals("sic1", sic1.getId());
        Assertions.assertEquals(1, cache.getSize());
        sic1.setFoo("a string");

        SimpleIdentifiableClass sic2 = cache.getObject(SimpleIdentifiableClass.class, "sic1", true);
        Assertions.assertTrue(sic1 == sic2);
        Assertions.assertEquals("a string", sic2.getFoo());
        Assertions.assertEquals(1, cache.getSize());
    }

    @Test
    void testLookupWithoutCreate() throws LinkMLRuntimeException {
        ObjectCache cache = new ObjectCache();
        SimpleIdentifiableClass sic1 = cache.getObject(SimpleIdentifiableClass.class, "sic1", true);

        SimpleIdentifiableClass sic2 = cache.getObject(SimpleIdentifiableClass.class, "sic1");
        Assertions.assertTrue(sic1 == sic2);
    }

    @Test
    void testDontReturnObjectOfDifferentType() throws LinkMLRuntimeException {
        ObjectCache cache = new ObjectCache();
        cache.getObject(SimpleIdentifiableClass.class, "sic1", true);

        SimpleDict sd = cache.getObject(SimpleDict.class, "sic1", false);
        Assertions.assertNull(sd);
    }

    @Test
    void testCachingByType() throws LinkMLRuntimeException {
        ObjectCache cache = new ObjectCache();
        SimpleIdentifiableClass sic1 = cache.getObject(SimpleIdentifiableClass.class, "id1", true);

        // We can store an object of a different type with the same ID
        SimpleDict sd1 = cache.getObject(SimpleDict.class, "id1", true);

        // We can retrieve the correct type of object
        Object o1 = cache.getObject(SimpleIdentifiableClass.class, "id1");
        Object o2 = cache.getObject(SimpleDict.class, "id1");
        Assertions.assertTrue(o1 != o2);
        Assertions.assertTrue(sic1 == o1);
        Assertions.assertTrue(sd1 == o2);
    }

    @Test
    void testChachingDerivedObjects() throws LinkMLRuntimeException {
        ObjectCache cache = new ObjectCache();
        // Caching an object of type ExtendedIdentifiableClass
        ExtendedIdentifiableClass eic1 = cache.getObject(ExtendedIdentifiableClass.class, "id1", true);

        // Retrieving it as a SimpleIdentifiableClass
        SimpleIdentifiableClass sic1 = cache.getObject(SimpleIdentifiableClass.class, "id1");
        Assertions.assertNotNull(sic1);
        Assertions.assertTrue(eic1 == sic1);
    }
}
