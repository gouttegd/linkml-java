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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.core.annotations.Converter;

/**
 * Global context for converting LinkML objects (as parsed from a JSON/YAML
 * document) into their corresponding Java objects, and the other way round.
 * <h1>Purposes</h1>
 * <p>
 * An object of this class serves mostly three purposes:
 * <ul>
 * <li>providing {@link IConverter} objects as needed;
 * <li>allow dereferencing “global” LinkML objects;
 * <li>providing a resolution/compaction service for CURIEs.
 * </ul>
 * 
 * <h2>Provider of {@link IConverter} objects</h2>
 * <p>
 * The context holds a list of all the known LinkML classes and types, along
 * with their respective converter objects. So whenever converting an object of
 * type <em>Foo</em> is required, the appropriate converter needed for such a
 * conversion can be obtained from the context (through either
 * {@link #getConverter(Class)} or {@link #getConverter(Slot)}).
 * <p>
 * The context has built-in knowledge of LinkML’s basic scalar types. For the
 * other types (classes or enumerations), the client code can provide its own
 * converters through the {@link #addConverter(IConverter)} method. In the
 * absence of an explicitly registered converter for a type, the context will
 * automatically provide a default. It is expected that in most cases, the
 * default converter should be enough.
 * 
 * <h2>Dereferencing global LinkML objects</h2>
 * <p>
 * “Dereferencing” is what must happen when converting the value of a LinkML
 * slot that (1) is expected to contain a globally unique object (an object
 * belonging to a class that has an <code>identifier</code> slot) and (2) is not
 * “inlined”. In such a case, in JSON/YAML serialisations the slot will actually
 * contain only the <em>identifier</em> of the object, the actual object
 * expectedly being represented elsewhere in the instance data. Dereferencing is
 * the process of finding the global object corresponding to a given identifier,
 * so that it can be assigned to the slot.
 * <p>
 * One problem that may arise when dereferencing is that an identifier may point
 * to a global object that has not been parsed and converted yet, depending on
 * (1) where the global object is defined and (2) the order in which we process
 * the JSON/YAML tree. For example, when parsing/converting a LinkML schema:
 * when parsing a class definition, we are likely to encounter a reference to a
 * slot definition, but we may not have parsed that slot definition yet. (And we
 * cannot solve that problem simply by ensuring that we always parse slot
 * definitions first, because slot definitions may in turn contains references
 * to a class definitions.)
 * <p>
 * This context object maintains a cache (implemented by the {@link ObjectCache}
 * class) of all the global objects, and will take care of resolving references
 * to those objects – either immediately if possible (if the referenced object
 * has been seen before and therefore is already in the cache), or in a
 * post-parsing finalisation step (when we are sure that all objects contained
 * in the parsed document have been seen, so all references should be
 * resolvable).
 * 
 * <h2>CURIE resolution</h2>
 * <p>
 * It is expected that short identifiers, otherwise known as “CURIEs”, will be
 * used a lot within LinkML instance data. This context object acts as a prefix
 * manager to help automatically resolve CURIEs into their full-length form
 * (when deserialising) and conversely compact IRIs into their short form (when
 * serialising).
 * 
 * <h1>Lifecycle</h1>
 * <p>
 * As can be inferred from the duties outlined in the previous section, the
 * converter object has a central role throughout the LinkML-Java runtime. One
 * such object must be created before doing virtually anything with LinkML
 * instance data.
 * <p>
 * The <em>same</em> converter object must be used for all operations pertaining
 * to the same set of LinkML instance data, even if the instance data is
 * physically spread over several JSON or YAML files. For example, when parsing
 * a LinkML schema, the same context must be used for the root schema and for
 * all the imported schemas, if any.
 * <p>
 * Conversely, in most cases it will not be desirable to reuse an existing
 * context to manipulate instance data that are not related to the instance data
 * for which the existing context has already been used.
 * <p>
 * Of note, once instance data have been parsed, the parsed objects are
 * self-sufficient; that is, they do <em>not</em> require the context to be kept
 * around. However, if the data is to be serialised back, it might be a good
 * idea to reuse the same context for serialisation as the one that was used for
 * deserialisation, because it will ensure that the same prefixes are used for
 * the compaction of CURIEs.
 */
public class ConverterContext {

    private Map<Class<?>, IConverter> converters = new HashMap<>();
    private Map<Slot, IConverter> slotConverters = new HashMap<>();
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
        converters.put(ZonedDateTime.class, new DatetimeConverter());
        converters.put(LocalDate.class, new DateConverter());
        converters.put(LocalTime.class, new TimeConverter());

        // For the primitive types, use the same converters as their boxed counterparts
        converters.put(Boolean.TYPE, converters.get(Boolean.class));
        converters.put(Integer.TYPE, converters.get(Integer.class));
        converters.put(Float.TYPE, converters.get(Float.class));
        converters.put(Double.TYPE, converters.get(Double.class));

        // We need a special "converter" for Object-typed fields (which represent slots
        // whose range is set to the linkml:Any class).
        converters.put(Object.class, new TransparentConverter());
    }

    /**
     * Registers a converter for objects of the given class.
     * 
     * @param type The class for which to register a converter. A default converter
     *             will be automatically created.
     * @deprecated Use either {@link #addConverter(IConverter)} with an explicitly
     *             instantiated {@link ObjectConverter}, or
     *             {@link #getConverter(Class)} (which will automatically trigger
     *             the registration of a newly instantiated converter if needed).
     */
    @Deprecated
    public void addConverter(Class<?> type) {
        converters.put(type, new ObjectConverter(type));
    }

    /**
     * Registers a pre-built converter.
     * <p>
     * This may be used to override the default converter that would normally be
     * automatically instantiated the first time a converter for a given type is
     * requested through {@link #getConverter(Class)}.
     * 
     * @param converter The converter to register.
     */
    public void addConverter(IConverter converter) {
        converters.put(converter.getType(), converter);
    }

    /**
     * Gets the converter for objects of the given type.
     * <p>
     * If no converter able to handle the type has been previously registered, then
     * the context will attempt to automatically instantiate and register a default
     * converter. The default converter will be either:
     * <ul>
     * <li>the custom converter as indicated by a {@link Converter} annotation on
     * the type (or any parent type);
     * <li>the default {@link EnumConverter} if the type is an enumeration type;
     * <li>the default {@link ObjectConverter} if the type is anything else.
     * </ul>
     * 
     * @param type The type to query.
     * @return The registered converter for the type.
     * @throws LinkMLRuntimeException If the type is configured to use a custom
     *                                converter that could not be instantiated.
     */
    public IConverter getConverter(Class<?> type) throws LinkMLRuntimeException {
        IConverter conv = converters.get(type);
        if ( conv == null ) {
            Class<?> parent = type;
            do {
                Converter annot = parent.getAnnotation(Converter.class);
                if ( annot != null ) {
                    conv = getCustomConverter(annot.value(), type);
                }
                parent = parent.getSuperclass();
            } while ( conv == null && parent != null );

            if ( conv == null ) {
                if ( type.isEnum() ) {
                    conv = new EnumConverter(type);
                } else {
                    conv = new ObjectConverter(type);
                }
            }

            converters.put(type, conv);
        }
        return conv;
    }

    /**
     * Gets the converter for the type of object expected by the given slot.
     * <p>
     * This is mostly equivalent to calling {@link #getConverter(Class)} on the
     * “inner type” of the slot, except that it takes into account any
     * {@link Converter} annotation carried by the slot.
     * 
     * @param slot The slot for which a converter is needed.
     * @return The appropriate converter.
     * @throws LinkMLRuntimeException If the slot (or its type) is configured to use
     *                                a custom converter, but the custom converter
     *                                could not be instantiated.
     */
    public IConverter getConverter(Slot slot) throws LinkMLRuntimeException {
        IConverter conv = slotConverters.get(slot);
        if ( conv == null ) {
            Class<?> type = slot.getCustomConverter();
            if ( type != null ) {
                conv = getCustomConverter(type, slot.getInnerType());
            } else {
                conv = getConverter(slot.getInnerType());
            }
            slotConverters.put(slot, conv);
        }
        return conv;
    }

    // Code common to both variants of getConverter
    private IConverter getCustomConverter(Class<?> converterType, Class<?> targetType) throws LinkMLRuntimeException {
        // A custom converter must have either (1) a default constructor (no argument)
        // or (2) a constructor that takes the type of object to convert as its single
        // argument. (It may have other constructors as well but one of those two must
        // be available)
        Constructor<?> defaultConstructor = null;
        Constructor<?> typedConstructor = null;
        for ( Constructor<?> cons : converterType.getDeclaredConstructors() ) {
            if ( cons.getParameterCount() == 0 ) {
                defaultConstructor = cons;
            } else if ( cons.getParameterCount() == 1 ) {
                if ( cons.getParameters()[0].getType().equals(Class.class) ) {
                    typedConstructor = cons;
                }
            }
        }

        Object conv = null;
        try {
            if ( typedConstructor != null ) {
                conv = typedConstructor.newInstance(targetType);
            } else {
                conv = defaultConstructor.newInstance();
            }
        } catch ( InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e ) {
            throw new LinkMLInternalError("Cannot instantiate custom converter", e);
        }

        if ( conv == null ) {
            throw new LinkMLInternalError("Cannot instantiate custom converter: no suitable constructor");
        } else if ( !(conv instanceof IConverter) ) {
            throw new LinkMLInternalError("Cannot instantiate custom converter: invalid type");
        }

        return (IConverter) conv;
    }

    /**
     * Registers a prefix. Any prefix declared here will become resolvable by the
     * {@link #resolve(String)} method, or conversely usable by the
     * {@link #compact(String)} method.
     * <p>
     * One of the responsibilities of the <code>ConverterContext</code> is to
     * provide a prefix resolution service, so that any shortened identifier
     * (“CURIE“) found within LinkML instance data can be resolved into a
     * full-length identifier.
     * <p>
     * This method is used internally throughout LinkML-Java to feed the prefix
     * resolution service. Client code may also use it as needed to provide its own
     * prefixes (for example, if the client code has out-of-band knowledge of which
     * prefixes will be used within the instance data it will manipulate).
     * 
     * @param name   The prefix name.
     * @param prefix The corresponding IRI prefix.
     */
    public void addPrefix(String name, String prefix) {
        if ( name.endsWith(":") ) {
            name = name.substring(0, name.length() - 1);
        }
        if ( !name.isEmpty() && !prefix.isEmpty() ) {
            prefixMap.put(name, prefix);
        }
    }

    /**
     * Resolves a shortened identifier (“CURIE”) into a full-length IRI.
     * 
     * @param name The shortened identifier to resolve.
     * @return The resolved IRI, or the original string if (1) it was not a
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
     * {@link #getObjects(Class, List, List)} methods, which could not be performed
     * at the time that method was called because the requested object was not known
     * to the context yet.
     * <p>
     * If a referenced object is still unknown when delayed assignments are
     * performed, an empty object of the desired type and with the referenced
     * identifier will be automatically created. Use
     * {@link #finalizeAssignments(boolean)} to treat missing references as an error
     * instead.
     * <p>
     * It is safe to call this method repeatedly – delayed assignments that were
     * already performed by a previous call will not be repeated.
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
            if ( da.name == null ) {
                continue;

            }
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
            da.name = null;
        }

        delayedAssignments.removeIf(da -> da.name == null);
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
