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

import java.util.List;
import java.util.Map;

/**
 * Base class for most scalar converters.
 * <p>
 * The role of this class is to have in a single place all the basic tests on
 * the raw value that most converters will need to perform.
 */
public abstract class ScalarConverterBase implements IScalarConverter {

    @Override
    public Object convert(Object value) throws LinkMLRuntimeException {
        if ( value == null ) {
            throw new LinkMLRuntimeException("Invalid null value, scalar type expected");
        } else if ( value instanceof List || value instanceof Map ) {
            throw new LinkMLRuntimeException("Invalid complex value, scalar type expected");
        }
        return convertImpl(value);
    }

    /**
     * Performs the actual type conversion.
     * 
     * @param value The raw object to convert. This is guaranteed to be a
     *              non-<code>null</code>, non-list, non-dictionary object.
     * @return The converted value.
     * @throws LinkMLRuntimeException If the converter cannot convert the given
     *                                value.
     */
    protected abstract Object convertImpl(Object value) throws LinkMLRuntimeException;

}
