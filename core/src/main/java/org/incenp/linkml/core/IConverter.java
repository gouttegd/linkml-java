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

/**
 * An object that can convert a “raw object” (as obtained from a JSON/YAML
 * parser) into a LinkML object, and the other way round.
 */
public interface IConverter {

    /**
     * Gets the type of object that this converter can convert a raw object into.
     */
    public Class<?> getType();

    /**
     * Converts a raw object into a LinkML object.
     * 
     * @param raw The raw object to convert.
     * @param ctx The global converter context.
     * @return The converted object.
     * @throws LinkMLRuntimeException If the converter cannot convert the given
     *                                value.
     */
    public Object convert(Object raw, ConverterContext ctx) throws LinkMLRuntimeException;

    /**
     * Converts a raw object into a LinkML object and assigns the result to a slot
     * of another object.
     * 
     * @param raw  The raw object to convert.
     * @param dest The object that should received the converted value.
     * @param slot The slot of the <code>dest</code> object to which the converted
     *             value should be assigned.
     * @param ctx  The global converter context.
     * @throws LinkMLRuntimeException If the converter cannot convert the given
     *                                value, or cannot assign it to the target
     *                                object/slot.
     */
    public void convertForSlot(Object raw, Object dest, Slot slot, ConverterContext ctx) throws LinkMLRuntimeException;

    /**
     * Converts a LinkML object into a raw object.
     * 
     * @param object The LinkML object to convert.
     * @param ctx    The global converter context.
     * @return The raw object that represents the original LinkML object.
     * @throws LinkMLRuntimeException If the converter cannot convert the given
     *                                object.
     */
    public Object serialise(Object object, ConverterContext ctx) throws LinkMLRuntimeException;

    /**
     * Converts a LinkML object into a raw object, when the object is the value of a
     * specific LinkML slot.
     * <p>
     * We need such a method because the way to serialise a LinkML object into a raw
     * object will sometimes depend on the slot to which the object belongs (the
     * slot of which it is a value), especially with respect to inlining.
     * 
     * @param object The LinkML object to convert.
     * @param slot   The slot that the given object is a value of.
     * @param ctx    The global converter context.
     * @return The raw object that represents the original LinkML object.
     * @throws LinkMLRuntimeException If the converter cannot convert the given
     *                                object.
     */
    public Object serialiseForSlot(Object object, Slot slot, ConverterContext ctx) throws LinkMLRuntimeException;
}
