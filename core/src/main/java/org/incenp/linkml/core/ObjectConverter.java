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
    private final static String CREATE_ERROR = "Cannot create object of type '%s'";
    private final static String NO_IDENTIFIER = "Missing identifier for type '%s'";
    private final static String NO_SIMPLE_DICT = "Type '%s' is not compatible with simple dict inlining";
    private final static String UNKNOWN_TYPE = "Unknown designated type '%s'";

    private Class<?> targetType;
    private Map<String, Slot> slots = new HashMap<>();
    private Slot identifierSlot;
    private Slot designatorSlot;
    private Slot extensionSlot;
    private Slot primarySlot;

    /**
     * Creates a new converter for objects of the specified type.
     * 
     * @param klass The type into which to convert raw objects.
     */
    public ObjectConverter(Class<?> klass) {
        targetType = klass;
        for ( Slot slot : Slot.getSlots(klass) ) {
            slots.put(slot.getLinkMLName(), slot);
            if ( slot.isIdentifier() ) {
                identifierSlot = slot;
            } else if ( slot.isExtensionStore() ) {
                extensionSlot = slot;
            } else if ( slot.isTypeDesignator() ) {
                designatorSlot = slot;
            }
        }
        primarySlot = Slot.getPrimaryValueSlot(slots.values());
    }

    @Override
    public Class<?> getType() {
        return targetType;
    }

    /**
     * Gets the specified slot.
     * <p>
     * This is mostly intended for the benefit of derived classes, so that they do
     * not have to call {@link Slot#getSlot(Class, String)} themselves (which would
     * be a duplication of the job done in the constructor of this class).
     * 
     * @param name The name of the slot.
     * @return The corresponding slot, or <code>null</code> if the class for which
     *         this object is a converter does not have any slot with that name.
     */
    protected Slot getSlot(String name) {
        return slots.get(name);
    }

    /**
     * Indicates whether this converter converts raw objects into “identifiable
     * objects”.
     * <p>
     * An “identifiable object” is an instance of a LinkML class that has an
     * identifier slot.
     */
    public boolean hasIdentifier() {
        return identifierSlot != null;
    }

    /**
     * Indicates whether the class this converter is intended for has a type
     * designator slot.
     * <p>
     * A “type designator slot” is a slot that references the exact class of an
     * instance of an object.
     */
    public boolean hasTypeDesignator() {
        return designatorSlot != null;
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
        Map<String, Object> extensions = null;
        for ( Map.Entry<String, Object> entry : rawMap.entrySet() ) {
            Slot slot = slots.get(entry.getKey());
            if ( slot == null ) {
                if ( extensionSlot != null ) {
                    if ( extensions == null ) {
                        extensions = new HashMap<>();
                        extensionSlot.setValue(dest, extensions);
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
        if ( hasTypeDesignator() ) {
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
        if ( hasIdentifier() ) {
            Object identifier = raw.get(identifierSlot.getLinkMLName());
            if ( identifier == null ) {
                throw new LinkMLValueError(String.format(NO_IDENTIFIER, targetType.getName()));
            }
            id = getIdentifier(identifier, ctx);
        }
        return conv.convert(raw, id, ctx);
    }

    /**
     * Converts a raw map into a new instance of a LinkML object.
     * 
     * @param raw The raw map to convert.
     * @param id  The global identifier of the new object. May be <code>null</code>
     *            if the object is not an “identifiable” object (it has no
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
            object = ctx.getObject(targetType, id, true);
        } else {
            try {
                object = targetType.newInstance();
            } catch ( InstantiationException | IllegalAccessException e ) {
                throw new LinkMLInternalError(String.format(CREATE_ERROR, targetType.getName()), e);
            }
        }
        convertTo(raw, object, ctx);
        return object;
    }

    /**
     * Converts the value of a single slot and assigns it to the target object.
     * <p>
     * This is the core of the converter. Its complexity stems mostly from the fact
     * that is has to deal with the various types of “inlining”.
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
        if ( slot.isMultivalued() ) {
            List<Object> value = new ArrayList<>();
            if ( hasIdentifier() ) {
                switch ( slot.getInliningMode() ) {
                case NO_INLINING:
                    ctx.getObjects(targetType, getIdentifierList(raw, ctx), value);
                    break;

                case LIST:
                    for ( Object rawItem : toList(raw) ) {
                        value.add(convert(rawItem, ctx));
                    }
                    break;

                case DICT:
                    for ( Map.Entry<String, Object> rawItem : toMap(raw).entrySet() ) {
                        Object item = null;
                        if ( rawItem.getValue() == null ) {
                            item = ctx.getObject(targetType, getIdentifier(rawItem.getKey(), ctx), true);
                        } else {
                            item = convert(toMap(rawItem.getValue()), getIdentifier(rawItem.getKey(), ctx), ctx);
                        }
                        value.add(item);
                    }
                    break;

                case SIMPLE_DICT:
                    if ( primarySlot == null ) {
                        throw new LinkMLInternalError(String.format(NO_SIMPLE_DICT, targetType.getName()));

                    }
                    IConverter primaryConv = ctx.getConverter(primarySlot);
                    for ( Map.Entry<String, Object> rawItem : toMap(raw).entrySet() ) {
                        Object item = ctx.getObject(targetType, getIdentifier(rawItem.getKey(), ctx), true);
                        primaryConv.convertForSlot(rawItem.getValue(), item, primarySlot, ctx);
                        value.add(item);
                    }
                    break;
                }

            } else {
                // Multi-valued non-identifiable object (necessary inlined as a list)
                for ( Object rawItem : toList(raw) ) {
                    value.add(convert(rawItem, ctx));
                }
            }
            slot.setValue(dest, value);
        } else {
            // Single-valued object
            if ( hasIdentifier() && slot.getInliningMode() == InliningMode.NO_INLINING ) {
                ctx.getObject(slot, getIdentifier(raw, ctx), dest);
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
    protected String getIdentifier(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        return ctx.getConverter(identifierSlot).convert(raw, ctx).toString();
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
    protected List<String> getIdentifierList(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        IConverter conv = ctx.getConverter(identifierSlot);
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
     *                       identifier is <em>not</em> serialised; this avoids
     *                       repeating the identifier when the object is serialised
     *                       in such a way that the identifier is already provided
     *                       elsewhere (typically as a dict, yielding what the
     *                       LinkML documentation calls the “CompactDict”
     *                       serialisation). This has no effect if the object has no
     *                       identifier slot.
     * @param ctx            The global converter context.
     * @return The map that represents the original LinkML object.
     * @throws LinkMLRuntimeException If the converter cannot convert the given
     *                                object.
     */
    public Map<String, Object> serialise(Object object, boolean withIdentifier, ConverterContext ctx)
            throws LinkMLRuntimeException {
        if ( !targetType.isInstance(object) ) {
            throw new LinkMLValueError(String.format(OBJECT_EXPECTED, targetType.getName()));
        }

        Map<String, Object> raw = new HashMap<>();
        for ( Slot slot : slots.values() ) {
            if ( slot.isIdentifier() && !withIdentifier ) {
                continue;
            }

            Object slotValue = slot.getValue(object);
            if ( slotValue == null ) {
                continue;
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
        if ( slot.isMultivalued() ) {
            List<Object> items = toList(object);
            InliningMode inlining = slot.getInliningMode();
            if ( !hasIdentifier() || inlining == InliningMode.LIST ) {
                List<Object> list = new ArrayList<>();
                for ( Object item : items ) {
                    list.add(serialise(item, ctx));
                }
                return list;
            } else if ( inlining == InliningMode.DICT || inlining == InliningMode.SIMPLE_DICT ) {
                if ( inlining == InliningMode.SIMPLE_DICT && primarySlot == null ) {
                    throw new LinkMLInternalError(String.format(NO_SIMPLE_DICT, targetType.getName()));
                }
                Map<Object, Object> map = new HashMap<>();
                for ( Object item : items ) {
                    Object rawItem = inlining == InliningMode.SIMPLE_DICT ? primarySlot.getValue(item)
                            : serialise(item, false, ctx);
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
            if ( hasIdentifier() && slot.getInliningMode() == InliningMode.NO_INLINING ) {
                return toIdentifier(object, ctx);
            } else {
                // Non-identifiable object or inlined identifiable object; in both cases, can
                // only be serialised as a map
                return serialise(object, ctx);
            }
        }
    }

    /**
     * Gets the unique identifier for the given object.
     * 
     * @param object The object for which to get the identifier.
     * @return The unique identifier for the object.
     * @throws LinkMLRuntimeException If the object is not of the expected type, or
     *                                if it has no identifier value.
     */
    protected Object toIdentifier(Object object, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( !targetType.isInstance(object) ) {
            throw new LinkMLValueError(String.format(OBJECT_EXPECTED, targetType.getName()));
        }
        Object identifier = identifierSlot.getValue(object);
        if ( identifier == null ) {
            throw new LinkMLValueError(String.format(NO_IDENTIFIER, targetType.getName()));
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
}
