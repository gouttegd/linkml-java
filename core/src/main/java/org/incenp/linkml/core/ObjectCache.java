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

import java.util.HashMap;
import java.util.Map;

/**
 * Cache for LinkML objects.
 * <p>
 * Several LinkML objects (especially all definitions, e.g. class definitions,
 * slot definitions, etc.) live in a global namespace where they are identified
 * by the value of their <em>identifier slot</em>. This object represents that
 * global namespace.
 */
public class ObjectCache {

    private static final String CREATE_ERROR = "Cannot create global object '%s' of type '%s'";
    private static final String NO_IDENTIFIER = "Missing identifier for type '%s'";

    private Map<Class<?>, Map<String, Object>> cache = new HashMap<>();
    private Map<Class<?>, Slot> slotCache = new HashMap<>();

    /**
     * Looks up for an object with the specified name.
     * 
     * @param <T>    The type of object to return.
     * @param type   The type of object to return.
     * @param name   The name of the object to return.
     * @param create If <code>true</code>, the object will be created and added to
     *               the cache if it did not already exist.
     * @return The object that was in the cache or have been newly created, or
     *         <code>null</code> if (1) the object that was in the cache was not of
     *         the expected type, or (2) the object was not in the cache and
     *         <code>create</code> is <code>false</code>.
     * @throws LinkMLRuntimeException If the specified type of object is not one
     *                                that can be cached (because it has no
     *                                identifier slot), or if the object could not
     *                                be created as needed.
     */
    public <T> T getObject(Class<T> type, String name, boolean create) throws LinkMLRuntimeException {
        Slot identifierSlot = getIdentifierSlot(type);
        if ( identifierSlot == null ) {
            throw new LinkMLInternalError(String.format(NO_IDENTIFIER, type.getName()));
        }

        Class<?> baseType = identifierSlot.getDeclaringClass();
        Map<String, Object> typeCache = cache.get(baseType);
        if ( typeCache == null ) {
            typeCache = new HashMap<>();
            cache.put(baseType, typeCache);
        }

        Object cached = typeCache.get(name);
        if ( cached != null ) {
            return type.isInstance(cached) ? type.cast(cached) : null;
        }

        if ( !create ) {
            return null;
        }

        try {
            cached = type.newInstance();
        } catch ( InstantiationException | IllegalAccessException e ) {
            throw new LinkMLInternalError(String.format(CREATE_ERROR, name, type.getName()), e);
        }

        identifierSlot.setValue(cached, name);
        typeCache.put(name, cached);

        return type.cast(cached);
    }

    /**
     * Looks up for an object with the specified name.
     * 
     * @param <T>  The type of object to return.
     * @param type The type of object to return.
     * @param name The name of the object to return.
     * @return The requested object, or <code>null</code> if the object was not in
     *         the cache or was not of the expected type.
     * @throws LinkMLRuntimeException If the specified type of object is not one
     *                                that can be cached, because it has no
     *                                identifier slot.
     */
    public <T> T getObject(Class<T> type, String name) throws LinkMLRuntimeException {
        return getObject(type, name, false);
    }

    /**
     * Gets the number of objects in the cache.
     */
    public int getSize() {
        return cache.size();
    }

    private Slot getIdentifierSlot(Class<?> type) {
        // Finding the identifier slot for a type requires iterating through all the
        // fields for that type, so we keep a cache of identifier slots by type.
        Slot slot = slotCache.get(type);
        if ( slot == null ) {
            slot = Slot.getIdentifierSlot(type);
            if ( slot != null ) {
                slotCache.put(type, slot);
            }
        }
        return slot;
    }
}
