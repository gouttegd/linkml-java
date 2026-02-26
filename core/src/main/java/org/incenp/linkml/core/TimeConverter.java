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

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * A converter for slots typed as <code>xsd:time</code> (represented as
 * {@link LocalTime}).
 */
public class TimeConverter extends ScalarConverterBase {

    @Override
    public Class<?> getType() {
        return LocalTime.class;
    }

    @Override
    protected Object convertImpl(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( raw instanceof LocalTime ) {
            return raw;
        } else {
            try {
                return LocalTime.parse(raw.toString());
            } catch ( DateTimeParseException e ) {
                throw new LinkMLValueError(String.format("Invalid value, time expected: %s", raw), e);
            }
        }
    }

    @Override
    public Object serialise(Object object, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( object instanceof LocalTime ) {
            return object.toString();
        } else {
            throw new LinkMLInternalError("invalid value, time expected");
        }
    }
}
