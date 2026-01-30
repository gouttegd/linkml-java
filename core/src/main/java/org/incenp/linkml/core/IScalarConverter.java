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
 * parser) into a LinkML scalar value.
 * 
 * FIXME: We should probably try to unify that with the ObjectConverter (which
 * is intended to convert non-scalar values) so that we have a single interface
 * for both scalar and non-scalar values.
 */
public interface IScalarConverter {

    /**
     * Indicates whether this object can convert a raw object into a value of the
     * specified type.
     * 
     * @param type The type to query.
     * @return <code>true</code> if the converter can handle conversion to
     *         <code>type</code>, otherwise <code>false</code>.
     */
    public boolean canHandle(Class<?> type);

    /**
     * Converts a raw object into a LinkML scalar value.
     * 
     * @param value The raw object to convert.
     * @return The converted value.
     * @throws LinkMLRuntimeException If the converter cannot convert the given
     *                                value. This includes the case where the value
     *                                is <code>null</code>.
     */
    public Object convert(Object value) throws LinkMLRuntimeException;
}
