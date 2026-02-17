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
    private final static String SCALAR_EXPECTED = "Invalid value type, scalar expected";
    private final static String CREATE_ERROR = "Cannot create object of type '%s'";
    private final static String NO_IDENTIFIER = "Missing identifier for type '%s'";
    private final static String NO_SIMPLE_DICT = "Type '%s' is not compatible with simple dict inlining";

    private Class<?> targetType;
    private Map<String, Slot> slots = new HashMap<>();
    private Slot identifierSlot;
    private Slot extensionSlot;

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
            }
        }
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
                ctx.getConverter(slot.getInnerType()).convertForSlot(entry.getValue(), dest, slot, ctx);
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
        String id = null;
        if ( hasIdentifier() ) {
            Object identifier = raw.get(identifierSlot.getLinkMLName());
            if ( identifier == null ) {
                throw new LinkMLValueError(String.format(NO_IDENTIFIER, targetType.getName()));
            }
            id = identifier.toString();
        }
        return convert(raw, id, ctx);
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
                    ctx.getObjects(targetType, toStringList(raw), value);
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
                            item = ctx.getObject(targetType, rawItem.getKey(), true);
                        } else {
                            item = convert(toMap(rawItem.getValue()), rawItem.getKey(), ctx);
                        }
                        value.add(item);
                    }
                    break;

                case SIMPLE_DICT:
                    Slot primarySlot = Slot.getPrimaryValueSlot(targetType);
                    if ( primarySlot == null ) {
                        throw new LinkMLInternalError(String.format(NO_SIMPLE_DICT, targetType.getName()));

                    }
                    IConverter primaryConv = ctx.getConverter(primarySlot.getInnerType());
                    for ( Map.Entry<String, Object> rawItem : toMap(raw).entrySet() ) {
                        Object item = ctx.getObject(targetType, rawItem.getKey(), true);
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
                ctx.getObject(slot, toString(raw), dest);
            } else {
                // Non-identifiable object or inlined identifiable object; in both cases, can
                // only be expected to be represented as a map
                slot.setValue(dest, convert(toMap(raw), ctx));
            }
        }
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
     * Checks that a raw object is a list of scalars, and converts it to a list of
     * strings.
     * 
     * @param value The raw object to check and convert.
     * @return A list of strings. Note that this is a <em>new</em> list, not the
     *         original object.
     * @throws LinkMLRuntimeException If the raw object is not a list of scalar
     *                                values.
     */
    @SuppressWarnings("unchecked")
    protected List<String> toStringList(Object value) throws LinkMLRuntimeException {
        if ( !(value instanceof List) ) {
            throw new LinkMLValueError(LIST_EXPECTED);
        }
        ArrayList<String> list = new ArrayList<>();
        for ( Object rawItem : (List<Object>) value ) {
            if ( rawItem instanceof List || rawItem instanceof Map ) {
                throw new LinkMLValueError(SCALAR_EXPECTED);
            }
            list.add(rawItem.toString());
        }
        return list;
    }

    /**
     * Checks that a raw object is a scalar value, and converts it to a string.
     * <p>
     * For the purpose of the converter, a <em>scalar</em> value is anything that is
     * neither a list nor a map.
     * 
     * @param value The raw object to check and convert.
     * @return The string value of the raw object.
     * @throws LinkMLRuntimeException If the raw object is not a scalar value.
     */
    protected String toString(Object value) throws LinkMLRuntimeException {
        if ( value instanceof List || value instanceof Map ) {
            throw new LinkMLValueError(SCALAR_EXPECTED);
        }
        return value.toString();
    }
}
