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

/**
 * A converter for String-typed fields whose value is expected to be a CURIE.
 * <p>
 * This is especially intended for slots of type <code>uriOrCurie</code>. The
 * converter will automatically expand the CURIE upon deserialising the data, so
 * that after parsing we no longer need to worry about the presence of CURIEs
 * anywhere – there will only be full-length IRIs. Conversely, when serialising
 * the data the converter will take care of compacting the IRIs back into CURIE
 * form.
 * <p>
 * This is basically a generalisation of the approach previously used
 * specifically for SSSOM-Java: expand CURIEs as soon as possible upon receiving
 * data, contract them as late as possible upon writing data. In between, you
 * only need to deal with full-length IRIs. This is the only sane way of working
 * with CURIEs.
 */
public class CurieConverter extends StringConverter {

    @Override
    protected Object convertImpl(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( raw instanceof String ) {
            return ctx.resolve((String) raw);
        } else {
            return ctx.resolve(raw.toString());
        }
    }

    @Override
    public Object serialise(Object object, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( getType().isInstance(object) ) {
            return ctx.compact((String) object);
        } else {
            throw new LinkMLInternalError("Invalid value");
        }
    }
}
