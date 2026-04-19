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
import java.util.Map;

/**
 * Global cache for LinkML identifiable objects.
 * <p>
 * LinkML’s “globally unique objects” (objects belonging to a class that has an
 * <em>identifier</em> slot; in turn, that’s a slot whose definition contains a
 * <code>identifier: true</code> slot), as their name implies, are expected to
 * be unique within a given LinkML context. (In fact they are most often
 * expected to be universally unique without restriction to any context, but
 * whatever may happen outside of a LinkML context is out of control of this
 * runtime, so we do not worry about it.)
 * <p>
 * This class implements a cache where such objects, along with their
 * identifier, may be registered and looked up afterwards.
 * <p>
 * Of note, when a LinkML class with an identifier slot has subclasses, the
 * identifier space is shared between that class and all its subclasses. For
 * example, in LinkML’s own meta-schema, slot definitions, class definitions,
 * and enum definitions (among other items) all share the same identifier space
 * (meaning that a slot definition cannot have the same identifier as a class
 * definition or a slot definition), because they all represented by subclasses
 * of the <code>Element</code> class, which is the class that carries the
 * identifier slot.
 */
public class ObjectCache {

    private static final String NO_IDENTIFIER = "Missing identifier for type '%s'";
    private static final String TYPE_ERROR = "Expected type '%s' for object '%s', found '%s'";

    private Map<Class<?>, Map<String, Object>> cache = new HashMap<>();

    /**
     * Looks up for an object with the specified name.
     * 
     * @param <T>    The type of object to return.
     * @param type   The type of object to return.
     * @param name   The name of the object to return.
     * @param create If <code>true</code>, the object will be created and added to
     *               the cache if it did not already exist.
     * @return The object that was in the cache or have been newly created, or
     *         <code>null</code> if the object was not in the cache and
     *         <code>create</code> is <code>false</code>.
     * @throws LinkMLRuntimeException If the specified type of object is not one
     *                                that can be cached (because it has no
     *                                identifier slot), if another object with an
     *                                incompatible type already exists in the cache,
     *                                or if the object could not be created as
     *                                needed.
     */
    public <T> T getObject(Class<T> type, String name, boolean create) throws LinkMLRuntimeException {
        Slot identifierSlot = ClassInfo.get(type).getIdentifierSlot();
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
            if ( type.isInstance(cached) ) {
                return type.cast(cached);
            }
            throw new LinkMLInternalError(
                    String.format(TYPE_ERROR, type.getSimpleName(), name, cached.getClass().getSimpleName()));
        }

        if ( !create ) {
            return null;
        }

        cached = ClassInfo.get(type).newInstance();
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
}
