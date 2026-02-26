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
 *   (3)The name of the author may not be used to endorse or promote
 *   products derived from this software without specific prior written
 *   permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.incenp.linkml.core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatetimeConversionTest {

    private ConverterContext ctx = new ConverterContext();
    private ZoneId utc = ZoneId.of("Z");
    private ZoneId of1 = ZoneId.ofOffset("", ZoneOffset.ofHours(1));

    @Test
    void testConvertingDateTime() throws LinkMLRuntimeException {
        DatetimeConverter conv = new DatetimeConverter();

        ZonedDateTime zdt = (ZonedDateTime) conv.convert("2026-01-01T12:12:12+01:00", ctx);
        Assertions.assertEquals(ZonedDateTime.of(2026, 1, 1, 12, 12, 12, 0, of1), zdt);

        zdt = (ZonedDateTime) conv.convert("2026-01-01 12:12:12+00:00", ctx);
        Assertions.assertEquals(ZonedDateTime.of(2026, 1, 1, 12, 12, 12, 0, utc), zdt);

        zdt = (ZonedDateTime) conv.convert("2026-01-01 12:12:12Z", ctx);
        Assertions.assertEquals(ZonedDateTime.of(2026, 1, 1, 12, 12, 12, 0, utc), zdt);

        zdt = (ZonedDateTime) conv.convert("2026-01-01", ctx);
        Assertions.assertEquals(ZonedDateTime.of(2026, 1, 1, 0, 0, 0, 0, utc), zdt);
    }

    @Test
    void testWritingDateTime() throws LinkMLRuntimeException {
        DatetimeConverter conv = new DatetimeConverter();

        Assertions.assertEquals("2026-01-01T12:12:12+01:00",
                conv.serialise(ZonedDateTime.of(2026, 1, 1, 12, 12, 12, 0, of1), ctx));
        Assertions.assertEquals("2026-01-01T12:12:12Z",
                conv.serialise(ZonedDateTime.of(2026, 1, 1, 12, 12, 12, 0, utc), ctx));
    }

    @Test
    void testConvertingDate() throws LinkMLRuntimeException {
        DateConverter conv = new DateConverter();

        LocalDate d = (LocalDate) conv.convert("2026-01-01", ctx);
        Assertions.assertEquals(LocalDate.of(2026, 1, 1), d);
    }

    @Test
    void testWritingDate() throws LinkMLRuntimeException {
        DateConverter conv = new DateConverter();

        Assertions.assertEquals("2026-01-01", conv.serialise(LocalDate.of(2026, 1, 1), ctx));
    }

    @Test
    void testConvertingTime() throws LinkMLRuntimeException {
        TimeConverter conv = new TimeConverter();

        LocalTime t = (LocalTime) conv.convert("12:12:12", ctx);
        Assertions.assertEquals(LocalTime.of(12, 12, 12), t);
    }

    @Test
    void testWritingTime() throws LinkMLRuntimeException {
        TimeConverter conv = new TimeConverter();

        Assertions.assertEquals("12:12:12", conv.serialise(LocalTime.of(12, 12, 12), ctx));
    }
}
