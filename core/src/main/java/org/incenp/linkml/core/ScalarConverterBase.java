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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Base class for most scalar converters.
 * <p>
 * The role of this class is to have in a single place all the basic tests on
 * the raw value that most converters will need to perform.
 */
public abstract class ScalarConverterBase implements IConverter {

    @Override
    public Object convert(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( raw == null ) {
            throw new LinkMLValueError("Invalid null value, scalar type expected");
        } else if ( raw instanceof List || raw instanceof Map ) {
            throw new LinkMLValueError("Invalid complex value, scalar type expected");
        }
        return convertImpl(raw, ctx);
    }

    @Override
    public void convertForSlot(Object raw, Object dest, Slot slot, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( slot.isMultivalued() ) {
            ArrayList<Object> list = new ArrayList<>();
            for ( Object item : toList(raw) ) {
                list.add(convert(item, ctx));
            }
            slot.setValue(dest, list);
        } else {
            slot.setValue(dest, convert(raw, ctx));
        }
    }

    /**
     * Performs the actual type conversion.
     * 
     * @param raw The raw object to convert. This is guaranteed to be a
     *            non-<code>null</code>, non-list, non-dictionary object.
     * @return The converted value.
     * @throws LinkMLRuntimeException If the converter cannot convert the given
     *                                value.
     */
    protected abstract Object convertImpl(Object raw, ConverterContext ctx) throws LinkMLRuntimeException;

    @Override
    public Object serialise(Object object, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( getType().isInstance(object) ) {
            return object;
        } else {
            throw new LinkMLInternalError("Invalid value");
        }
    }

    @Override
    public Object serialiseForSlot(Object object, Slot slot, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( slot.isMultivalued() ) {
            ArrayList<Object> list = new ArrayList<>();
            for ( Object item : toList(object) ) {
                list.add(serialise(item, ctx));
            }
            return list;
        } else {
            return serialise(object, ctx);
        }
    }

    /**
     * Checks that a raw object is a list, and casts it as such.
     * 
     * @param raw The raw object to cast.
     * @return The input object, cast into a list.
     * @throws LinkMLRuntimeException If the raw object is not in fact a list.
     */
    @SuppressWarnings("unchecked")
    protected List<Object> toList(Object raw) throws LinkMLRuntimeException {
        if ( !(raw instanceof List) ) {
            throw new LinkMLValueError("Invalid value type, list expected");
        }
        return (List<Object>) raw;
    }
}
