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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global context for converting LinkML objects (as parsed from a JSON/YAML
 * document) into their corresponding Java objects.
 * <p>
 * An object of this class serves mostly two purposes.
 * <p>
 * First, it holds a list of all the known LinkML classes, along with their
 * respective converter objects. So whenever a converting encounters a slot
 * expecting a value of type <em>Foo</em> (where <em>Foo</em> is a LinkML
 * class), it can obtain from the context the converter it needs to convert the
 * value of the slot into an instance of the <em>Foo</em> class.
 * <p>
 * Second, it provides a solution to the problem of dereferencing entities that
 * may not have been parsed/converted yet. For example, when parsing a class
 * definition, we are likely to encounter a reference to a slot definition, but
 * we may not have parsed that slot definition yet. We cannot solve that simply
 * by ensuring that we always parse slot definitions first, because slot
 * definitions may in turn contain references to a class definition (typically
 * within the <code>range</code> slot). This context object will take care of
 * resolving those references, either immediately if possible (if the referenced
 * object has already been seen before), or in a post-parsing finalization step
 * (when we are sure that all objects contained in the document have been seen,
 * so all references should be resolvable).
 */
public class ConverterContext {

    private Map<Class<?>, IConverter> converters = new HashMap<>();
    private ObjectCache objectCache = new ObjectCache();
    private List<DelayedAssignment> delayedAssignments = new ArrayList<>();
    private Map<String, String> prefixMap = new HashMap<>();

    /**
     * Registers a converter for objects of the given class.
     * 
     * @param type The class for which to register a converter. A default converter
     *             will be automatically created.
     */
    public void addConverter(Class<?> type) {
        converters.put(type, new ObjectConverter(type));
    }

    /**
     * Registers a pre-built converter.
     * 
     * @param converter The converter to register.
     */
    public void addConverter(IConverter converter) {
        converters.put(converter.getType(), converter);
    }

    /**
     * Gets the converter for objects of the given type.
     * 
     * @param type The type to query.
     * @return The registered converter for the type, or <code>null</code> if no
     *         converter has been registered for that type.
     */
    public IConverter getConverter(Class<?> type) {
        return converters.get(type);
    }

    /**
     * Registers a prefix.
     * <p>
     * Any prefix declared here will become resolvable by the
     * {@link #resolve(String)} method.
     * 
     * @param name   The prefix name.
     * @param prefix The corresponding IRI prefix.
     */
    public void addPrefix(String name, String prefix) {
        prefixMap.put(name, prefix);
    }

    /**
     * Resolves a shortened identifier (“CURIE”) into a full-length IRI.
     * 
     * @param name The shortened identifier to resolve.
     * @return The resolved IRI, or the original String if (1) it was not a
     *         shortened identifier to begin with or (2) the prefix is unknown.
     */
    public String resolve(String name) {
        if ( !name.contains(":") ) {
            // Not a CURIE, return as is
            return name;
        }

        String[] items = name.split(":", 2);
        String prefix = prefixMap.get(items[0]);
        if ( prefix != null ) {
            return prefix + items[1];
        }

        // Non-resolvable prefix name, return as is
        return name;
    }

    /**
     * Dereferences a global object, optionally creating it if does not already
     * exist.
     * 
     * @param <T>    The type of object to dereference.
     * @param type   The type of object to dereference.
     * @param name   The name to resolve into a dereferenced object.
     * @param create If <code>true</code>, a new object of the given type and with
     *               the given name will be created and added to the global context
     *               if an object with that name did not already exist.
     * @return The dereferenced object, or <code>null</code> if the object did not
     *         already exist and <code>create</code> is <code>false</code>.
     * @throws LinkMLRuntimeException If we cannot create the object as needed.
     */
    public <T> T getObject(Class<T> type, String name, boolean create) throws LinkMLRuntimeException {
        return objectCache.getObject(type, name, create);
    }

    /**
     * Dereferences a global object and assigns it to a slot on another object.
     * <p>
     * If the desired object does not exist in the context, the assignment will take
     * place when the {@link #finalizeAssignments()} method is called.
     * 
     * @param slot   The slot to which to assign the dereferenced object.
     * @param name   The name to resolve into a dereferenced object.
     * @param target The object to which to assign the dereferenced object.
     * @return The dereferenced object, or <code>null</code> if the object does not
     *         exist in the context at the time this method is called.
     * @throws LinkMLRuntimeException If the retrieved object cannot be assigned to
     *                                the slot.
     */
    public Object getObject(Slot slot, String name, Object target) throws LinkMLRuntimeException {
        Object value = objectCache.getObject(slot.getInnerType(), name, false);
        if ( value == null ) {
            delayedAssignments.add(new DelayedAssignment(name, target, slot));
        } else {
            slot.setValue(target, value);
        }
        return value;
    }

    /**
     * Dereferences several global objects and assigns them to a list.
     * <p>
     * If any of the referenced objects does not exist in the context, it will be
     * assigned to the target list when the {@link #finalizeAssignments()} method is
     * called.
     * 
     * @param type   The type of the objects to dereference.
     * @param names  The names to resolve into dereferenced objects.
     * @param target The list to which the dereferenced objects should be assigned.
     */
    public void getObjects(Class<?> type, List<String> names, List<Object> target) {
        for ( String name : names ) {
            Object value = objectCache.getObject(type, name);
            if ( value == null ) {
                delayedAssignments.add(new DelayedAssignment(name, target, type));
            } else {
                target.add(value);
            }
        }
    }

    /**
     * Performs all delayed assignments.
     * <p>
     * A delayed assignment is an assignment requested through the
     * {@link #getObject(Slot, String, Object)} or
     * {@link #getObjects(Slot, List, Object)} methods, which could not be performed
     * at the time that method was called because the requested object was not known
     * to the context yet.
     * 
     * @throws LinkMLRuntimeException If an assignment cannot be performed.
     */
    public void finalizeAssignments() throws LinkMLRuntimeException {
        finalizeAssignments(false);
    }

    /**
     * Performs all delayed assignments, optionally failing if an assignment refers
     * to a missing object.
     * 
     * @param failOnMissing If <code>true</code> and a delayed assignment refers to
     *                      an object that is either unknown in the global context,
     *                      or of a different type than expected, this will be
     *                      treated as a fatal error.
     * @throws LinkMLRuntimeException If an assignment cannot be performed
     *                                (including for an invalid reference, if
     *                                <code>failOnMissing</code> is
     *                                <code>true</code>.
     */
    public void finalizeAssignments(boolean failOnMissing) throws LinkMLRuntimeException {
        for ( DelayedAssignment da : delayedAssignments ) {
            Object value = getObject(da.type, da.name, false);
            if ( value != null ) {
                da.setValue(value);
            } else if ( failOnMissing ) {
                // Check if there is another object with that name
                if ( getObject(Object.class, da.name, false) != null ) {
                    throw new LinkMLRuntimeException(String.format("Cannot dereference '%s': invalid type", da.name));
                } else {
                    throw new LinkMLRuntimeException(String.format("Cannot dereference '%s': no such object", da.name));
                }
            }
        }
    }

    /*
     * Represents the assignment of a global object to a slot, that cannot be
     * performed immediately because the requested global object is not known yet.
     * 
     * This is a private class for now because it is not expected to be needed
     * anywhere outside of this context. That may change in the future.
     */
    private class DelayedAssignment {
        Class<?> type; // The type of object to assign.
        String name; // The name of the global object to dereference and assign.
        Object targetObject; // The object to which to assign the dereferenced value.
        Slot targetSlot; // The slot (within the target object) to which to assign the value.
        List<Object> targetList; // The list to which to assign the dereferenced value (when the target slot is a
                                 // multi-valued slot).

        DelayedAssignment(String name, Object target, Slot slot) {
            this.type = slot.getInnerType();
            this.name = name;
            this.targetObject = target;
            this.targetSlot = slot;
        }

        DelayedAssignment(String name, List<Object> target, Class<?> type) {
            this.type = type;
            this.name = name;
            this.targetList = target;
        }

        void setValue(Object value) throws LinkMLRuntimeException {
            if ( targetList != null ) {
                targetList.add(value);
            } else {
                targetSlot.setValue(targetObject, value);
            }
        }
    }
}
