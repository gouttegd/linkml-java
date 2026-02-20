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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An object to convert simple LinkML enums (excluding so-called “dynamic
 * enums”, which are not yet supported).
 * <p>
 * Similarly to the <code>ObjectConverter</code>, there should be one instance
 * of this class for every type of enum that needs to be converted.
 * <p>
 * This converter relies on the enum type providing (1) a
 * <code>fromString</code> method that can be used to deserialise an enum value,
 * and (2) a <code>toString()</code> method that can be used to serialise the
 * value, in such a way that, if <code>v</code> is a value from the enum
 * <code>Enum</code>, then <code>Enum.fromString(v.toString()) == v</code> is
 * true.
 * <p>
 * The Java code generator in LinkML-Py automatically produces such enum types,
 * if the <code>--true-enums</code> option is used.
 */
public class EnumConverter extends ScalarConverterBase {

    private final static String NO_ENUM = "Target type is not a supported LinkML enum";
    private final static String INVOCATION_ERROR = "Cannot invoke the enum constructor";
    private final static String INVALID_VALUE = "Invalid enum value '%s'";

    private Class<?> targetEnum;
    private Method fromString;

    /**
     * Creates a new converter for the specified type of enum.
     * 
     * @param targetEnum The enum into which to convert raw objects.
     * @throws LinkMLRuntimeException If the target type is not a valid enum type
     *                                (either because it is not a Java enum to begin
     *                                with, or because it does not provide the
     *                                expected <code>fromString</code> method.
     */
    public EnumConverter(Class<?> targetEnum) throws LinkMLRuntimeException {
        if ( !targetEnum.isEnum() ) {
            throw new LinkMLInternalError(NO_ENUM);
        }

        this.targetEnum = targetEnum;
        try {
            fromString = targetEnum.getMethod("fromString", new Class<?>[] { String.class });
        } catch ( NoSuchMethodException | SecurityException e ) {
            throw new LinkMLInternalError(NO_ENUM, e);
        }
    }

    @Override
    public Class<?> getType() {
        return targetEnum;
    }

    @Override
    protected Object convertImpl(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        String rawString = raw instanceof String ? (String) raw : raw.toString();
        Object o = null;
        try {
            o = fromString.invoke(null, new Object[] { rawString });
        } catch ( IllegalAccessException | IllegalArgumentException e ) {
            throw new LinkMLInternalError(INVOCATION_ERROR, e);
        } catch ( InvocationTargetException e ) {
            throw new LinkMLValueError(String.format(INVALID_VALUE, raw), e);
        }

        if ( o == null ) {
            throw new LinkMLValueError(String.format(INVALID_VALUE, raw));
        }

        return o;
    }

    @Override
    public Object serialise(Object object, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( targetEnum.isInstance(object) ) {
            return object.toString();
        } else {
            throw new LinkMLInternalError(String.format(INVALID_VALUE, object));
        }
    }
}
