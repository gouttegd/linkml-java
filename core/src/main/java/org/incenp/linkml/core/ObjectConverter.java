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

import org.incenp.linkml.model.InliningMode;

/**
 * A class to convert “raw objects” (as obtained from a JSON/YAML parser) into
 * instances of LinkML objects.
 * 
 * FIXME: Absolutely not complete yet, lots of cases still not covered.
 */
public class ObjectConverter {

    private Map<String, Slot> slots = new HashMap<>();
    private boolean hasIdentifier;

    /**
     * Creates a new converter for objects of the specified type.
     * 
     * @param klass The type into which to convert raw objects.
     */
    public ObjectConverter(Class<?> klass) {
        for ( Slot slot : Slot.getSlots(klass) ) {
            slots.put(slot.getLinkMLName(), slot);
            if ( slot.isIdentifier() ) {
                hasIdentifier = true;
            }
        }
    }

    /**
     * Indicates whether this converter converts raw objects into “identifiable
     * objects”.
     * <p>
     * An “identifiable object” is an instance of a LinkML class that has an
     * identifier slot.
     */
    public boolean hasIdentifier() {
        return this.hasIdentifier;
    }

    /**
     * Converts a raw object into an instance of a LinkML object.
     * 
     * @param rawObject The raw object to convert.
     * @param dest      The instance of the LinkML object whose slots should be
     *                  filled with values from the raw object.
     * @param ctx       The global converter context.
     * @throws LinkMLRuntimeException If a slot of the <code>dest</code> object
     *                                cannot be assigned.
     */
    public void convert(Object rawObject, Object dest, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( rawObject == null ) {
            return;
        }

        Map<String, Object> rawMap = toMap(rawObject);
        for ( Map.Entry<String, Object> entry : rawMap.entrySet() ) {
            Slot slot = slots.get(entry.getKey());
            if ( slot == null ) {
                // Ignore unknown key.
                // FIXME: Allow storing into a dedicated dict for unknown keys.
                continue;
            }

            Class<?> type = slot.getInnerType();
            if ( slot.isMultivalued() ) {
                // FIXME: Non-covered cases:
                // - complex multi-valued slots expecting non-identifiable values
                // - complex multi-valued slots expecting identifiable values inlined as list
                // - complex multi-valued slots expecting references
                if ( ctx.isComplexType(type) ) {
                    if ( ctx.hasIdentifier(type) ) {
                        InliningMode inlining = slot.getInliningMode();
                        if ( inlining == InliningMode.DICT ) {
                            ArrayList<Object> value = new ArrayList<>();

                            Map<String, Object> rawValue = toMap(entry.getValue());
                            for ( Map.Entry<String, Object> rawItem : rawValue.entrySet() ) {
                                Object item = ctx.getObject(type, rawItem.getKey(), true);
                                Map<String, Object> rawItemDict = toMap(rawItem.getValue());
                                ObjectConverter itemConverter = ctx.getConverter(type);
                                itemConverter.convert(rawItemDict, item, ctx);
                                value.add(item);
                            }

                            slot.setValue(dest, value);
                        } else if ( inlining == InliningMode.SIMPLE_DICT ) {
                            Slot primarySlot = Slot.getPrimaryValueSlot(type);
                            if ( primarySlot == null ) {
                                throw new LinkMLRuntimeException(
                                        String.format("Type of slot '%s' is not compatible with simple dict inlining",
                                                slot.getLinkMLName()));
                            }
                            ArrayList<Object> value = new ArrayList<>();

                            Map<String, Object> rawValue = toMap(entry.getValue());
                            for ( Map.Entry<String, Object> rawItem : rawValue.entrySet() ) {
                                // FIXME: For now we assume that the primary slot of a class inlined as a
                                // SimpleDict cannot be multi-valued and cannot be of a non-scalar type. Whether
                                // this is correct or not is unclear: the LinkML validator accepts a
                                // multi-valued primary slot and a non-scalar-typed primary slot, but the LinkML
                                // converter rejects both cases (https://github.com/linkml/linkml/issues/3112).
                                Object item = ctx.getObject(type, rawItem.getKey(), true);
                                primarySlot.setValue(item,
                                        convertScalar(primarySlot.getInnerType(), rawItem.getValue(), ctx));
                                value.add(item);
                            }
                            slot.setValue(dest, value);
                        }
                    }
                } else {
                    // Scalar multi-valued slot
                    if ( !(entry.getValue() instanceof List) ) {
                        throw new LinkMLRuntimeException("Invalid value type, list expected");
                    }
                    @SuppressWarnings("unchecked")
                    List<Object> rawValue = List.class.cast(entry.getValue());
                    IScalarConverter conv = ctx.getScalarConverter(type);
                    if ( conv == null ) {
                        throw new LinkMLRuntimeException(String.format("Unsupported scalar type: %s", type.getName()));
                    }
                    ArrayList<Object> value = new ArrayList<>();

                    for ( Object rawItem : rawValue ) {
                        value.add(conv.convert(rawItem));
                    }
                    slot.setValue(dest, value);

                }
            } else if ( ctx.isComplexType(type) ) {
                // FIXME: Non-covered cases:
                // - non-identifiable object
                // - identifiable object inlined
                if ( ctx.hasIdentifier(type) ) {
                    switch ( slot.getInliningMode() ) {
                    case NO_INLINING:
                        ctx.getObject(slot, (String) entry.getValue(), dest);
                        break;
                    case DICT:
                        break;
                    case LIST:
                        // Does not make sense for a single-valued slot?
                        break;
                    case SIMPLE_DICT:
                        break;
                    default:
                        // Should never happen
                        break;
                    }
                }
            } else {
                slot.setValue(dest, convertScalar(type, entry.getValue(), ctx));
            }
        }
    }

    protected Object convertScalar(Class<?> type, Object value, ConverterContext ctx) throws LinkMLRuntimeException {
        IScalarConverter conv = ctx.getScalarConverter(type);
        if ( conv == null ) {
            throw new LinkMLRuntimeException(String.format("Unsupported scalar type: %s", type.getName()));
        }
        return conv.convert(value);
    }

    /**
     * Checks that a raw object is a String-keyed map, and casts it as such.
     * 
     * @param value The raw object to cast.
     * @return The input object, cast into a String-keyed map; or <code>null</code>
     *         if the input object is not a map, or is a map with at least one key
     *         that is not a string.
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> toMap(Object value) {
        if ( Map.class.isInstance(value) ) {
            Map<Object, Object> map = Map.class.cast(value);
            for ( Object key : map.keySet() ) {
                if ( !String.class.isInstance(key) ) {
                    return null;
                }
            }

            return (Map<String, Object>) Map.class.cast(value);
        }
        return null;
    }
}
