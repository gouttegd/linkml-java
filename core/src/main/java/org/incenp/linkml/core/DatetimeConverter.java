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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

/**
 * A converter for slots typed as <code>xsd:datetime</code> (represented as
 * {@link ZonedDateTime}).
 */
public class DatetimeConverter extends ScalarConverterBase {

    private DateTimeFormatter writeFormat;
    private DateTimeFormatter parseFormat;

    public DatetimeConverter() {
        // The LinkML "specification" says nothing about how a datetime object is
        // supposed to be serialised, but the Python implementation expects a
        // ISO8601-like string, where the separator between the date and time parts can
        // be either 'T' or ' '. Also, it accepts a string containing the date part
        // only. The following parse format _should_ cover all those cases.

        // @formatter:off
        DateTimeFormatter timeFmt1 = new DateTimeFormatterBuilder()
                .appendLiteral('T')
                .appendPattern("HH:mm[:ss][XXX]")
                .toFormatter();
        DateTimeFormatter timeFmt2 = new DateTimeFormatterBuilder()
                .appendLiteral(' ')
                .appendPattern("HH:mm[:ss][XXX]")
                .toFormatter();
        parseFormat = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-DD")
                .appendOptional(timeFmt1)
                .appendOptional(timeFmt2)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
                .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
                .toFormatter();

        writeFormat = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-DD")
                .appendLiteral('T')
                .appendPattern("HH:mm:ssXXX")
                .toFormatter();
        // @formatter:on
    }

    @Override
    public Class<?> getType() {
        return ZonedDateTime.class;
    }

    @Override
    protected Object convertImpl(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        if (raw instanceof ZonedDateTime) {
            return raw;
        } else {
            try {
                return ZonedDateTime.parse(raw.toString(), parseFormat);
            } catch ( DateTimeParseException e ) {
                throw new LinkMLValueError(
                        String.format("Invalid value, datetime expected: %s", raw), e);
            }
        }
    }

    @Override
    public Object serialise(Object object, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( object instanceof ZonedDateTime ) {
            return ((ZonedDateTime) object).format(writeFormat);
        } else {
            throw new LinkMLInternalError("Invalid value, datetime expected");
        }
    }
}
