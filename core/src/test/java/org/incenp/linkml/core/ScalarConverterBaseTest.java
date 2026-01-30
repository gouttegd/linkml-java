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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScalarConverterBaseTest {

    @Test
    void testNonScalarObjects() {
        StringConverter c = new StringConverter();

        Object o = null;
        try {
            c.convert(o);
            Assertions.fail("Exception not thrown for a null value");
        } catch ( LinkMLRuntimeException e ) {
        }

        o = new ArrayList<String>();
        try {
            c.convert(o);
            Assertions.fail("Exception not thrown for a list value");
        } catch ( LinkMLRuntimeException e ) {
        }

        o = new HashMap<String, Object>();
        try {
            c.convert(o);
            Assertions.fail("Exception not thrown for a dictionary value");
        } catch ( LinkMLRuntimeException e ) {
        }
    }

    @Test
    void testStringConversion() throws LinkMLRuntimeException {
        StringConverter c = new StringConverter();
        Assertions.assertEquals("string value", c.convert("string value"));
        Assertions.assertEquals("123", c.convert(123));
    }

    @Test
    void testBooleanConversion() throws LinkMLRuntimeException {
        BooleanConverter c = new BooleanConverter();
        Assertions.assertEquals(true, c.convert(true));
        Assertions.assertEquals(true, c.convert("true"));
        Assertions.assertEquals(false, c.convert("False"));
        try {
            c.convert("not a boolean");
            Assertions.fail("Exception not thrown for an invalid boolean value");
        } catch ( LinkMLRuntimeException e ) {
        }
    }

    @Test
    void testURIConversion() throws LinkMLRuntimeException {
        URIConverter c = new URIConverter();
        Object uri = c.convert("local_file.txt");
        Assertions.assertTrue(uri instanceof URI);
        Assertions.assertEquals("local_file.txt", ((URI) uri).getPath());

        try {
            c.convert("not a uri");
            Assertions.fail("Exception not thrown for an invalid URI value");
        } catch ( LinkMLRuntimeException e ) {
        }
    }
}
