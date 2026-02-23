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

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.core.annotations.Converter;

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
    private Map<Class<?>, IConverter> customConverters = new HashMap<>();
    private ObjectCache objectCache = new ObjectCache();
    private List<DelayedAssignment> delayedAssignments = new ArrayList<>();
    private Map<String, String> prefixMap = new HashMap<>();
    private Map<String, String> iri2CurieCache = new HashMap<>();

    public ConverterContext() {
        // Register converters for all the basic types.
        converters.put(String.class, new StringConverter());
        converters.put(Boolean.class, new BooleanConverter());
        converters.put(Integer.class, new IntegerConverter());
        converters.put(Float.class, new FloatConverter());
        converters.put(Double.class, new DoubleConverter());
        converters.put(URI.class, new URIConverter());

        // For the primitive types, use the same converters as their boxed counterparts
        converters.put(Boolean.TYPE, converters.get(Boolean.class));
        converters.put(Integer.TYPE, converters.get(Integer.class));
        converters.put(Float.TYPE, converters.get(Float.class));
        converters.put(Double.TYPE, converters.get(Double.class));

    }

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
     * @throws LinkMLInternalError
     */
    public IConverter getConverter(Class<?> type) throws LinkMLRuntimeException {
        IConverter conv = null;
        Converter annot = type.getAnnotation(Converter.class);
        if ( annot != null ) {
            conv = getCustomConverter(annot.value());
        } else {
            conv = converters.get(type);
            if ( conv == null ) {
                if ( type.isEnum() ) {
                    conv = new EnumConverter(type);
                } else {
                    conv = new ObjectConverter(type);
                }
                converters.put(type, conv);
            }
        }
        return conv;
    }

    /**
     * Gets the converter for the type of object expected by the given slot.
     * 
     * @param slot The slot for which a converter is needed.
     * @return The appropriate converter.
     * @throws LinkMLRuntimeException If (1) the slot is configured to use a custom
     *                                converter, but the custom converter could not
     *                                be instantiated; or (2) if there is no known
     *                                converter for the slot type.
     */
    public IConverter getConverter(Slot slot) throws LinkMLRuntimeException {
        IConverter conv = null;
        Class<?> type = slot.getCustomConverter();
        if ( type != null ) {
            conv = getCustomConverter(type);
        } else {
            conv = getConverter(slot.getInnerType());
        }

        return conv;
    }

    // Code common to both variants of getConverter
    private IConverter getCustomConverter(Class<?> converterType) throws LinkMLRuntimeException {
        IConverter conv = customConverters.get(converterType);
        if ( conv == null ) {
            try {
                conv = (IConverter) converterType.newInstance();
                customConverters.put(converterType, conv);
            } catch ( InstantiationException | IllegalAccessException e ) {
                throw new LinkMLInternalError("Cannot instantiate custom converter", e);
            }
        }
        return conv;
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
     * Compacts an IRI into a shortened identifier (“CURIE“).
     * 
     * @param iri The IRI to shorten.
     * @return The shortened identifier, or the original string if there no known
     *         suitable prefix for the given IRI.
     */
    public String compact(String iri) {
        String shortId = iri2CurieCache.get(iri);

        if ( shortId == null ) {
            String bestPrefix = null;
            int bestLength = 0;

            for ( String prefixName : prefixMap.keySet() ) {
                String prefix = prefixMap.get(prefixName);
                if ( iri.startsWith(prefix) && prefix.length() > bestLength ) {
                    bestPrefix = prefixName;
                    bestLength = prefix.length();
                }
            }

            if ( bestPrefix != null ) {
                shortId = bestPrefix + ":" + iri.substring(bestLength);
                iri2CurieCache.put(iri, shortId);
            }
        }

        return shortId != null ? shortId : iri;
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
     * @throws LinkMLRuntimeException If the object type is not suitable for global
     *                                objects, or if we cannot create the object as
     *                                needed.
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
     * @throws LinkMLRuntimeException If the object type is not suitable for global
     *                                objects, or if the retrieved object cannot be
     *                                assigned to the slot.
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
     * @throws LinkMLRuntimeException If the object type is not suitable for global
     *                                objects.
     */
    public void getObjects(Class<?> type, List<String> names, List<Object> target) throws LinkMLRuntimeException {
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
     * <p>
     * If a referenced object is still unknown when delayed assignments are
     * performed, an empty object of the desired type and with the referenced
     * identifier will be automatically created. Use
     * {@link #finalizeAssignments(boolean)} to treat missing references as an error
     * instead.
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
                throw new LinkMLValueError(String.format("Cannot dereference '%s': no such object", da.name));
            } else {
                // Create an empty object
                value = getObject(da.type, da.name, true);
                da.setValue(value);
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
