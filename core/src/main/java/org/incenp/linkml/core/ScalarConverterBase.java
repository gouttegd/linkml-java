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
