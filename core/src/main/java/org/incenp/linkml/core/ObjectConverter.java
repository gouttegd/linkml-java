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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A generic converter to convert “raw objects” (as obtained from a JSON/YAML
 * parser) into instances of LinkML classes.
 */
public class ObjectConverter implements IConverter {

    private static final Logger logger = LoggerFactory.getLogger(ObjectConverter.class);

    private final static String MAP_EXPECTED = "Invalid value type, map expected";
    private final static String LIST_EXPECTED = "Invalid value type, list expected";
    private final static String STRING_EXPECTED = "Invalid value type, string expected";
    private final static String OBJECT_EXPECTED = "Invalid value type, '%s' expected";
    private final static String NO_IDENTIFIER = "Missing identifier for type '%s'";
    private final static String UNKNOWN_TYPE = "Unknown designated type '%s'";

    protected ClassInfo klass;
    private PrefixDeclarationExtractor prefixExtractor;

    /**
     * Creates a new converter for objects of the specified type.
     * 
     * @param klass The type into which to convert raw objects.
     */
    public ObjectConverter(Class<?> klass) {
        this.klass = ClassInfo.get(klass);
        prefixExtractor = PrefixDeclarationExtractor.getExtractor(this.klass);
    }

    @Override
    public Class<?> getType() {
        return klass.getType();
    }

    /**
     * Converts a raw map into an instance of a LinkML object, where the object
     * already exists.
     * 
     * @param rawMap The raw map to convert.
     * @param dest   The instance of the LinkML object whose slots should be filled
     *               with values from the raw map.
     * @param ctx    The global converter context.
     * @throws LinkMLRuntimeException If a slot of the <code>dest</code> object
     *                                cannot be assigned.
     */
    public void convertTo(Map<String, Object> rawMap, Object dest, ConverterContext ctx) throws LinkMLRuntimeException {
        // If this object has a prefix expansion slot, process it before everything else
        if ( prefixExtractor != null ) {
            for ( Map.Entry<String, String> prefix : prefixExtractor.extractPrefixes(rawMap, dest, ctx).entrySet() ) {
                ctx.addPrefix(prefix.getKey(), prefix.getValue());
            }
        }

        Map<String, Object> extensions = null;
        for ( Map.Entry<String, Object> entry : rawMap.entrySet() ) {
            Slot slot = klass.getSlot(entry.getKey());
            if ( slot == null ) {
                if ( klass.hasExtensionSlot() ) {
                    if ( extensions == null ) {
                        extensions = new HashMap<>();
                        klass.getExtensionSlot().setValue(dest, extensions);
                    }
                    extensions.put(entry.getKey(), entry.getValue());
                } else {
                    logger.debug("Ignoring unknown or unsupported slot '{}'", entry.getKey());
                }
            } else {
                ctx.getConverter(slot).convertForSlot(entry.getValue(), dest, slot, ctx);
            }
        }
    }

    /**
     * Converts a raw object into an instance of a LinkML object, where the object
     * already exists.
     * <p>
     * This method is similar to {@link #convertTo(Map, Object, ConverterContext)},
     * but accepts a <code>Object</code>-typed raw value and takes care of checking
     * that the value is actually a map. If the raw object is <code>null</code>,
     * this method is a no-op.
     * 
     * @param raw  The raw object to convert.
     * @param dest The instance of the LinkML object whose slots should be filled
     *             with values from the raw object.
     * @param ctx  The global converter context.
     * @throws LinkMLRuntimeException If either (1) <code>raw</code> is not a map,
     *                                or (2) a slot of the <code>dest</code> object
     *                                cannot be assigned.
     */
    public void convertTo(Object raw, Object dest, ConverterContext ctx)
            throws LinkMLRuntimeException {
        if ( raw == null ) {
            return;
        }

        convertTo(toMap(raw), dest, ctx);
    }

    @Override
    public Object convert(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        return convert(toMap(raw), ctx);
    }

    /**
     * Converts a raw map into a new instance of a LinkML object.
     * <p>
     * If the object to create is an “identifiable” object, then the identifier is
     * expected to be found within the provided raw map.
     * 
     * @param raw The raw map to convert.
     * @param ctx The global converter context.
     * @return The newly created object.
     * @throws LinkMLRuntimeException If (1) the object cannot be created, (2) one
     *                                of its slots cannot be assigned, or (3) the
     *                                object requires an identifier that was not
     *                                found within the raw map.
     */
    public Object convert(Map<String, Object> raw, ConverterContext ctx) throws LinkMLRuntimeException {
        ObjectConverter conv = this;
        if ( klass.hasTypeDesignator() ) {
            Slot designatorSlot = klass.getTypeDesignatorSlot();
            // FIXME: Several issues here.
            // First, we only supporting referencing a type by name. According to the LinkML
            // doc, a type designator can also be a URI (or a CURIE), in which case it
            // refers to the class by its class URI.
            // Second, we assume that all classes derived from a LinkML schema lives in the
            // same package. This is not ideal but, if all we have to refer to a class is an
            // unqualified name, it does not seem like an unreasonable assumption.
            // Third, LinkML allows the type designator slot to be multivalued, in which
            // case we should pick “the most specific class” among all the classes listed
            // (what to do if the list contains several classes at the same inheritance
            // level is unspecified). Currently we only support the single-valued case.
            Object designator = raw.get(designatorSlot.getLinkMLName());
            if ( designator != null ) {
                String pkgName = getType().getPackage().getName();
                String clsName = ctx.getConverter(designatorSlot).convert(designator, ctx).toString();
                try {
                    Class<?> designatedType = Class.forName(pkgName + "." + clsName);
                    conv = (ObjectConverter) ctx.getConverter(designatedType);
                } catch ( ClassNotFoundException e ) {
                    // We treat that as a value error because providing a correct name in the class
                    // designator is the responsibility of whoever produced the data.
                    throw new LinkMLValueError(String.format(UNKNOWN_TYPE, pkgName));
                }
            }
        }
        String id = null;
        if ( klass.isGloballyUnique() ) {
            Object identifier = raw.get(klass.getIdentifierSlot().getLinkMLName());
            if ( identifier == null ) {
                throw new LinkMLValueError(String.format(NO_IDENTIFIER, getType().getName()));
            }
            id = getGlobalIdentifier(identifier, ctx);
        }
        return conv.convert(raw, id, ctx);
    }

    /**
     * Converts a raw map into a new instance of a LinkML object.
     * 
     * @param raw The raw map to convert.
     * @param id  The global identifier of the new object. May be <code>null</code>
     *            if the object is not a “ globally identifiable” object (it has no
     *            identifier slot).
     * @param ctx The global converter context.
     * @return The newly created object.
     * @throws LinkMLRuntimeException If the object cannot be created, or one of its
     *                                slots cannot be assigned.
     */
    public Object convert(Map<String, Object> raw, String id, ConverterContext ctx)
            throws LinkMLRuntimeException {
        Object object = null;
        if ( id != null ) {
            object = ctx.getObject(klass.getType(), id, true);
        } else {
            object = klass.newInstance();
        }
        convertTo(raw, object, ctx);
        return object;
    }

    /**
     * Converts the value of a single slot and assigns it to the target object.
     * 
     * @param raw  The raw value of the slot.
     * @param slot The slot to which the value should be assigned.
     * @param dest The object whose slots should be assigned.
     * @param ctx  The global converter context.
     * @throws LinkMLRuntimeException If (1) the raw value does not match the
     *                                expected type for the slot, or (2) the
     *                                converted value cannot be assigned.
     */
    @Override
    public void convertForSlot(Object raw, Object dest, Slot slot, ConverterContext ctx)
            throws LinkMLRuntimeException {
        InliningMode inlining = slot.getInliningMode();
        if ( slot.isMultivalued() ) {
            List<Object> value = new ArrayList<>();
            if ( inlining == InliningMode.NO_INLINING ) {
                ctx.getObjects(getType(), getGlobalIdentifierList(raw, ctx), value);
            } else {
                for ( Object rawItem : normaliseList(raw, inlining) ) {
                    value.add(convert(rawItem, ctx));
                }
            }
            slot.setValue(dest, value);
        } else {
            // Single-valued object
            if ( inlining == InliningMode.NO_INLINING ) {
                ctx.getObject(slot, getGlobalIdentifier(raw, ctx), dest);
            } else {
                // Non-identifiable object or inlined identifiable object; in both cases, can
                // only be expected to be represented as a map
                slot.setValue(dest, convert(toMap(raw), ctx));
            }
        }
    }

    /**
     * Converts the given raw object into an identifier for the target object type.
     * <p>
     * This invokes whatever converter is configured for the identifier slot of the
     * target type. Most often it should be the {@link StringConverter} (as
     * identifier slots are mostly expected to be string-typed), but it could also
     * be the {@link CurieConverter}, if the identifier slot is typed as a
     * <code>uriOrCurie</code>.
     * 
     * @param raw The raw identifier to convert.
     * @param ctx The global converter context.
     * @return The converted identifier.
     * @throws LinkMLRuntimeException If any error occurs during the conversion
     *                                attempt.
     */
    protected String getGlobalIdentifier(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        return ctx.getConverter(klass.getIdentifierSlot()).convert(raw, ctx).toString();
    }

    /**
     * Converts the given list of raw objects into identifiers for the target object
     * type.
     * <p>
     * This method does basically the same thing as
     * {@link #getIdentifier(Object, ConverterContext)} for every raw object in the
     * provided list.
     * 
     * @param raw The raw object to convert. It is expected to be a list containing
     *            the raw identifiers.
     * @param ctx The global converter context.
     * @return The list of converter identifiers.
     * @throws LinkMLRuntimeException If any error occurs during the conversion
     *                                attempt. This includes the case where the
     *                                provided raw object is not a list.
     */
    protected List<String> getGlobalIdentifierList(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        IConverter conv = ctx.getConverter(klass.getIdentifierSlot());
        ArrayList<String> list = new ArrayList<>();
        for ( Object rawItem : toList(raw) ) {
            list.add(conv.convert(rawItem, ctx).toString());
        }
        return list;
    }

    @Override
    public Object serialise(Object object, ConverterContext ctx) throws LinkMLRuntimeException {
        return serialise(object, true, ctx);
    }

    /**
     * Converts a LinkML object into a raw object. This serializes an object into a
     * map, with the option of including the object’s unique identifier into the
     * map.
     * 
     * @param object         The LinkML object to convert.
     * @param withIdentifier If <code>false</code>, the slot for the object’s unique
     *                       identifier (whether is it local or global) is
     *                       <em>not</em> serialised; this avoids repeating the
     *                       identifier when the object is serialised in such a way
     *                       that the identifier is already provided elsewhere
     *                       (typically as a dict, yielding what the LinkML
     *                       documentation calls the “CompactDict” serialisation).
     *                       This has no effect if the object has no identifier or
     *                       key slot.
     * @param ctx            The global converter context.
     * @return The map that represents the original LinkML object.
     * @throws LinkMLRuntimeException If the converter cannot convert the given
     *                                object.
     */
    public Map<String, Object> serialise(Object object, boolean withIdentifier, ConverterContext ctx)
            throws LinkMLRuntimeException {
        if ( !getType().isInstance(object) ) {
            throw new LinkMLValueError(String.format(OBJECT_EXPECTED, getType().getName()));
        }

        Map<String, Object> raw = new HashMap<>();
        for ( Slot slot : klass.getSlots() ) {
            if ( (slot.isIdentifier()) && !withIdentifier ) {
                continue;
            }

            Object slotValue;
            if ( slot.isTypeDesignator() ) {
                // Ignore whatever may be contained in the slot and use the actual type name
                slotValue = getType().getSimpleName();
            } else {
                slotValue = slot.getValue(object);
                if ( slotValue == null ) {
                    continue;
                }
            }

            if ( slot.isExtensionStore() ) {
                for ( Map.Entry<String, Object> extension : toMap(slotValue).entrySet() ) {
                    raw.put(extension.getKey(), extension.getValue());
                }
            } else {
                raw.put(slot.getLinkMLName(), ctx.getConverter(slot).serialiseForSlot(slotValue, slot, ctx));
            }
        }

        return raw;
    }

    @Override
    public Object serialiseForSlot(Object object, Slot slot, ConverterContext ctx) throws LinkMLRuntimeException {
        InliningMode inlining = slot.getInliningMode();
        if ( slot.isMultivalued() ) {
            List<Object> items = toList(object);
            // FIXME: Check behaviour regarding identifier/key
            if ( inlining == InliningMode.LIST ) {
                List<Object> list = new ArrayList<>();
                for ( Object item : items ) {
                    list.add(serialise(item, ctx));
                }
                return list;
            } else if ( inlining == InliningMode.DICT ) {
                boolean simpleDict = klass.isEligibleForSimpleDict(true);
                Map<Object, Object> map = new HashMap<>();
                for ( Object item : items ) {
                    Object rawItem = simpleDict ? klass.getPrimarySlot().getValue(item) : serialise(item, false, ctx);
                    map.put(toIdentifier(item, ctx), rawItem);
                }
                return map;
            } else { // No inlining
                List<Object> list = new ArrayList<>();
                for ( Object item : items ) {
                    list.add(toIdentifier(item, ctx));
                }
                return list;
            }
        } else {
            // Single-valued object
            if ( inlining == InliningMode.NO_INLINING ) {
                return toIdentifier(object, ctx);
            } else {
                // Not globally identifiable object or inlined object; in both cases, can only
                // be serialised as a map
                return serialise(object, ctx);
            }
        }
    }

    /**
     * Gets the unique identifier for the given object.
     * <p>
     * The identifier may be a local identifier (key) or a global one (proper
     * identifier in the LinkML sense).
     * 
     * @param object The object for which to get the identifier.
     * @return The unique identifier for the object.
     * @throws LinkMLRuntimeException If the object is not of the expected type, or
     *                                if it has no identifier value.
     */
    protected Object toIdentifier(Object object, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( !getType().isInstance(object) ) {
            throw new LinkMLValueError(String.format(OBJECT_EXPECTED, getType().getName()));
        }
        Slot identifierSlot = klass.getIdentifierSlot();
        Object identifier = identifierSlot.getValue(object);
        if ( identifier == null ) {
            throw new LinkMLValueError(String.format(NO_IDENTIFIER, getType().getName()));
        }
        return ctx.getConverter(identifierSlot).serialise(identifier, ctx);
    }

    /**
     * Checks that a raw object is a String-keyed map, and casts it as such.
     * 
     * @param value The raw object to cast.
     * @return The input object, cast into a String-keyed map.
     * @throws LinkMLRuntimeException If the raw object is not a String-keyed map.
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> toMap(Object value) throws LinkMLRuntimeException {
        if ( !(value instanceof Map) ) {
            throw new LinkMLValueError(MAP_EXPECTED);
        }
        Map<Object, Object> map = (Map<Object, Object>) value;
        for ( Object key : map.keySet() ) {
            if ( !(key instanceof String) ) {
                throw new LinkMLValueError(STRING_EXPECTED);
            }
        }
        return (Map<String, Object>) value;
    }

    /**
     * Checks that a raw object is a list, and casts it as such.
     * 
     * @param value The raw object to cast.
     * @return The input object, cast into a list.
     * @throws LinkMLRuntimeException If the raw object is not in fact a list.
     */
    @SuppressWarnings("unchecked")
    protected List<Object> toList(Object value) throws LinkMLRuntimeException {
        if ( !(value instanceof List) ) {
            throw new LinkMLValueError(LIST_EXPECTED);
        }
        return (List<Object>) value;
    }

    /**
     * Normalises a raw multi-valued slot.
     * <p>
     * This is mostly a convenience method to make the job of
     * {@link #convertForSlot(Object, Object, Slot, ConverterContext)} easier. Given
     * the raw value of a multi-valued slot, it always provides a list
     * representation of it, whether the value has indeed been serialised as a list
     * or as any kind of dict.
     * <p>
     * This incidentally implements the <a href=
     * "https://linkml.io/linkml-model/latest/docs/specification/06mapping/#collection-form-normalization">non-repair
     * collection form normalisations</a> described in the LinkML specification.
     * 
     * @param raw  The raw value to convert.
     * @param slot The slot for which the value is intended. This is used to compare
     *             the actual serialisation form with the form expected as per the
     *             original schema.
     * @return A list with the raw values. This may be the original value (simply
     *         casted as a list) if it already was a list to begin with, or a new
     *         list transformed from whatever actual serialisation was used.
     * @throws LinkMLRuntimeException (1) If the slot expected a list serialisation
     *                                and we got any kind of dict, or the other way
     *                                round. (2) If we got something that looks like
     *                                a SimpleDict, for a class that does not
     *                                support this kind of serialisation. (3) Or if
     *                                we get anything else than a list or a dict.
     */
    @SuppressWarnings("unchecked")
    protected List<Object> normaliseList(Object raw, InliningMode expected) throws LinkMLRuntimeException {
        if ( raw instanceof List ) {
            if ( expected != InliningMode.LIST ) {
                throw new LinkMLValueError(MAP_EXPECTED);
            }
            return (List<Object>) raw;
        } else if ( raw instanceof Map ) {
            if ( expected == InliningMode.LIST ) {
                throw new LinkMLValueError(LIST_EXPECTED);
            }

            Slot identifierSlot = klass.getIdentifierSlot();
            List<Object> normalisedList = new ArrayList<>();
            for ( Map.Entry<String, Object> rawItem : toMap(raw).entrySet() ) {
                String id = rawItem.getKey();
                Map<String, Object> rawValue = null;
                if ( rawItem.getValue() == null ) {
                    // "Empty" object, fabricate an ExpandedDict with just the identifier
                    rawValue = new HashMap<>();
                    rawValue.put(identifierSlot.getLinkMLName(), id);
                } else if ( rawItem.getValue() instanceof Map ) {
                    // Compact or ExpandedDict
                    rawValue = toMap(rawItem.getValue());
                    if ( !rawValue.containsKey(identifierSlot.getLinkMLName()) ) {
                        // CompactDict; inject the key
                        rawValue.put(identifierSlot.getLinkMLName(), id);
                    }
                } else if ( klass.isEligibleForSimpleDict(false) ) {
                    // Assume SimpleDict, transform into an Expanded Dict
                    rawValue = new HashMap<>();
                    rawValue.put(identifierSlot.getLinkMLName(), id);
                    rawValue.put(klass.getPrimarySlot().getLinkMLName(), rawItem.getValue());
                } else {
                    throw new LinkMLValueError(MAP_EXPECTED);
                }
                normalisedList.add(rawValue);
            }
            return normalisedList;
        } else {
            throw new LinkMLValueError(LIST_EXPECTED);
        }
    }
}
