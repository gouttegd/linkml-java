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

import org.incenp.linkml.model.SlotDefinition;
import org.incenp.linkml.model.TypeDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectCacheTest {

    @Test
    void testCachedObjectIsNotCreatedTwice() throws LinkMLRuntimeException {
        ObjectCache cache = new ObjectCache();
        TypeDefinition def1 = cache.getObject(TypeDefinition.class, "string", true);

        Assertions.assertEquals("string", def1.getName());
        Assertions.assertEquals(1, cache.getSize());
        def1.setDescription("description");

        TypeDefinition def2 = cache.getObject(TypeDefinition.class, "string", true);
        Assertions.assertEquals(def1, def2);
        Assertions.assertEquals("description", def2.getDescription());
        Assertions.assertEquals(1, cache.getSize());
    }

    @Test
    void testLookupWithoutCreate() throws LinkMLRuntimeException {
        ObjectCache cache = new ObjectCache();
        TypeDefinition def1 = cache.getObject(TypeDefinition.class, "string", true);

        TypeDefinition def2 = cache.getObject(TypeDefinition.class, "string");
        Assertions.assertTrue(def1 == def2);

        SlotDefinition def3 = cache.getObject(SlotDefinition.class, "string");
        Assertions.assertNull(def3);
    }

    @Test
    void testDontReturnObjectOfDifferentType() throws LinkMLRuntimeException {
        ObjectCache cache = new ObjectCache();
        cache.getObject(TypeDefinition.class, "string", true);

        SlotDefinition def2 = cache.getObject(SlotDefinition.class, "string", false);
        Assertions.assertNull(def2);
    }
}
