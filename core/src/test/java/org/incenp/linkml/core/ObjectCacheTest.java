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
