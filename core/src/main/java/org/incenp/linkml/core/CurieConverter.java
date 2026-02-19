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
