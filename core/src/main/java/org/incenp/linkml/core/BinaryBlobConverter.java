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

import java.util.Base64;

/**
 * A converter for slots typed as <code>xsd:base64Binary</code> (represented as
 * an array of <code>byte</code>).
 * <p>
 * Of note, such a type currently does not exist in LinkML, though it can easily
 * be added in a custom type declaration. The default Javagen templates won’t
 * render it as a <code>byte[]</code>, though.
 */
public class BinaryBlobConverter extends ScalarConverterBase {

    // Not sure of a better way to get the Class<?> representing a byte[] array than
    // having a dummy on which we can call getClass()... The correct way would be
    // Byte.TYPE.arrayType(), but that method is only available from Java 12+.
    private static byte[] array = new byte[1];

    @Override
    public Class<?> getType() {
        return array.getClass();
    }

    @Override
    protected Object convertImpl(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        try {
            return Base64.getDecoder().decode(raw.toString());
        } catch ( IllegalArgumentException e ) {
            throw new LinkMLValueError("Invalid value, Base64-encoded binary blob expected", e);
        }
    }

    public Object serialise(Object object, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( getType().isInstance(object) ) {
            return Base64.getEncoder().encodeToString((byte[]) object);
        } else {
            throw new LinkMLInternalError("Invalid value, array of bytes expected");
        }
    }
}
