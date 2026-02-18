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

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import org.incenp.linkml.core.sample.ContainerOfIntegerValues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScalarConverterBaseTest {

    private ConverterContext ctx = new ConverterContext();

    @Test
    void testNonScalarObjects() {
        StringConverter c = new StringConverter();

        Object o = null;
        try {
            c.convert(o, ctx);
            Assertions.fail("Exception not thrown for a null value");
        } catch ( LinkMLRuntimeException e ) {
        }

        o = new ArrayList<String>();
        try {
            c.convert(o, ctx);
            Assertions.fail("Exception not thrown for a list value");
        } catch ( LinkMLRuntimeException e ) {
        }

        o = new HashMap<String, Object>();
        try {
            c.convert(o, ctx);
            Assertions.fail("Exception not thrown for a dictionary value");
        } catch ( LinkMLRuntimeException e ) {
        }
    }

    @Test
    void testStringConversion() throws LinkMLRuntimeException {
        StringConverter c = new StringConverter();
        Assertions.assertEquals("string value", c.convert("string value", ctx));
        Assertions.assertEquals("123", c.convert(123, ctx));
    }

    @Test
    void testBooleanConversion() throws LinkMLRuntimeException {
        BooleanConverter c = new BooleanConverter();
        Assertions.assertEquals(true, c.convert(true, ctx));
        Assertions.assertEquals(true, c.convert("true", ctx));
        Assertions.assertEquals(false, c.convert("False", ctx));
        try {
            c.convert("not a boolean", ctx);
            Assertions.fail("Exception not thrown for an invalid boolean value");
        } catch ( LinkMLRuntimeException e ) {
        }
    }

    @Test
    void testURIConversion() throws LinkMLRuntimeException {
        URIConverter c = new URIConverter();
        Object uri = c.convert("local_file.txt", ctx);
        Assertions.assertTrue(uri instanceof URI);
        Assertions.assertEquals("local_file.txt", ((URI) uri).getPath());

        try {
            c.convert("not a uri", ctx);
            Assertions.fail("Exception not thrown for an invalid URI value");
        } catch ( LinkMLRuntimeException e ) {
        }
    }

    @Test
    void testIntegerConversion() throws LinkMLRuntimeException {
        IntegerConverter c = new IntegerConverter();
        Object i = c.convert(123, ctx);
        Assertions.assertTrue(i instanceof Integer);
        Assertions.assertTrue((int) i == 123);

        i = c.convert("123", ctx);
        Assertions.assertTrue(i instanceof Integer);
        Assertions.assertTrue((int) i == 123);

        try {
            c.convert("not a number", ctx);
            Assertions.fail("Exception not thrown for an invalid integer value");
        } catch ( LinkMLRuntimeException e ) {
            Assertions.assertInstanceOf(NumberFormatException.class, e.getCause());
        }
    }

    @Test
    void testFloatConversion() throws LinkMLRuntimeException {
        FloatConverter c = new FloatConverter();
        Object f = c.convert(123.456f, ctx);
        Assertions.assertTrue(f instanceof Float);
        Assertions.assertTrue((float) f == 123.456f);

        f = c.convert("123.456", ctx);
        Assertions.assertTrue(f instanceof Float);
        Assertions.assertTrue((float) f == 123.456f);

        try {
            c.convert("not a number", ctx);
            Assertions.fail("Exception not thrown for an invalid float value");
        } catch ( LinkMLRuntimeException e ) {
            Assertions.assertInstanceOf(NumberFormatException.class, e.getCause());
        }
    }

    @Test
    void testDoubleConversion() throws LinkMLRuntimeException {
        DoubleConverter c = new DoubleConverter();
        Object d = c.convert(123.456, ctx);
        Assertions.assertTrue(d instanceof Double);
        Assertions.assertTrue((double) d == 123.456);

        d = c.convert("123.456", ctx);
        Assertions.assertTrue(d instanceof Double);
        Assertions.assertTrue((double) d == 123.456);

        try {
            c.convert("not a number", ctx);
            Assertions.fail("Exception not thrown for an invalid float value");
        } catch ( LinkMLRuntimeException e ) {
            Assertions.assertInstanceOf(NumberFormatException.class, e.getCause());
        }
    }

    @Test
    void testMultivaluedConversion() throws LinkMLRuntimeException {
        IntegerConverter c = new IntegerConverter();
        ArrayList<Object> raw = new ArrayList<>();
        raw.add(123);
        raw.add(456);

        ContainerOfIntegerValues coiv = new ContainerOfIntegerValues();
        Slot slot = Slot.getSlot(ContainerOfIntegerValues.class, "baz");
        c.convertForSlot(raw, coiv, slot, ctx);

        Assertions.assertEquals(coiv.getBaz().get(0), 123);
        Assertions.assertEquals(coiv.getBaz().get(1), 456);

        try {
            c.convertForSlot(789, coiv, slot, ctx);
            Assertions.fail("Exception not thrown for an invalid list value");
        } catch ( LinkMLRuntimeException e ) {
            Assertions.assertEquals("Invalid value type, list expected", e.getMessage());
        }
    }

    @Test
    void testSerialisationReturnsSameObject() throws LinkMLRuntimeException {
        IConverter c = new StringConverter();
        String s = "string value";

        Assertions.assertEquals(s, c.serialise(s, ctx));

        c = new IntegerConverter();
        Integer i = 123;

        Assertions.assertEquals(i, c.serialise(i, ctx));
    }

    @Test
    void testSerialisationOfInvalidValue() {
        StringConverter c = new StringConverter();
        try {
            c.serialise(123, ctx);
            Assertions.fail("Exception not thrown for attempting to serialise an invalid value type");
        } catch ( LinkMLRuntimeException e ) {
        }
    }

    @Test
    void testSerialisationOfBooleanValue() throws LinkMLRuntimeException {
        BooleanConverter c = new BooleanConverter();
        Boolean b1 = true;
        boolean b2 = true;

        Assertions.assertEquals(b1, c.serialise(b1, ctx));
        Assertions.assertEquals(b2, c.serialise(b2, ctx));

        try {
            c.serialise(123, ctx);
            Assertions.fail("Exception not thrown for attempting to serialise a non-boolean value");
        } catch ( LinkMLRuntimeException e ) {
        }
    }
}
