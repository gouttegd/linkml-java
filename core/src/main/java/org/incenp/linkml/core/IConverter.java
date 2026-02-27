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
