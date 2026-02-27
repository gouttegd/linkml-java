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
