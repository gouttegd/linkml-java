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

import org.incenp.linkml.core.sample.SampleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnumConverterTest {

    @Test
    void testInvalidEnumType() {
        try {
            new EnumConverter(EnumConverterTest.class);
            Assertions.fail("Exception not thrown for a non-enum type");
        } catch (LinkMLRuntimeException e) {
        }

        try {
            new EnumConverter(InliningMode.class);
            Assertions.fail("Exception not thrown for an unsupported enum type");
        } catch ( LinkMLRuntimeException e ) {
        }
    }

    @Test
    void testDeserialisation() {
        ConverterContext ctx = new ConverterContext();
        try {
            Assertions.assertEquals(SampleEnum.FOO, ctx.getConverter(SampleEnum.class).convert("foo", ctx));
        } catch ( LinkMLRuntimeException e ) {
            Assertions.fail("Unexpected exception", e);
        }

        try {
            ctx.getConverter(SampleEnum.class).convert(123, ctx);
            Assertions.fail("Exception not thrown for an invalid value");
        } catch ( LinkMLRuntimeException e ) {
            Assertions.assertInstanceOf(LinkMLValueError.class, e);
            Assertions.assertEquals("Invalid enum value '123'", e.getMessage());
        }
    }

    @Test
    void testSerialisation() {
        ConverterContext ctx = new ConverterContext();
        try {
            Assertions.assertEquals("foo", ctx.getConverter(SampleEnum.class).serialise(SampleEnum.FOO, ctx));
        } catch ( LinkMLRuntimeException e ) {
            Assertions.fail("Unexpected exception", e);
        }

        try {
            ctx.getConverter(SampleEnum.class).serialise(123, ctx);
            Assertions.fail("Exception not thrown for an invalid value");
        } catch ( LinkMLRuntimeException e ) {
            Assertions.assertInstanceOf(LinkMLInternalError.class, e);
            Assertions.assertEquals("Invalid enum value '123'", e.getMessage());
        }
    }
}
