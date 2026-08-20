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

package org.incenp.linkml.schema;

import java.util.Map;

import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.core.ObjectConverter;
import org.incenp.linkml.schema.model.Element;

/**
 * A converter object specifically intended to convert schema elements (i.e.
 * {@link Element} objects).
 * <p>
 * We need a special converter because we must take into account that schema
 * elements (class, slot, enumeration, and type definitions) in a given schema
 * can be overridden by another schema depending on the position of each schema
 * in the import chains.
 * <p>
 * The LinkML specification does <em>not</em> say how overriding should work
 * – in fact, the specification explicitly says that multiple definitions of a
 * single element across several schemas in the imports closure is an
 * <em>error</em> and should be treated as such, so the question of which
 * definition overrides another should be moot.
 * <p>
 * However LinkML-Py does <em>not</em> treat multiple definitions as errors, and
 * instead has an explicit overriding logic to deal with them (logic that is
 * only “documented” in a comment in the SchemaView code…). Presumably that
 * logic is the expected behaviour.
 * <p>
 * The sole purpose of this converter is to check prior to convert an element
 * object if the object is overridden from another schema, and if so skip
 * conversion altogether.
 */
public class ElementConverter extends ObjectConverter {

    private SchemaDocument doc;

    public ElementConverter(Class<? extends Element> klass, SchemaDocument doc) {
        super(klass);
        this.doc = doc;
    }

    @Override
    public void convertTo(Map<String, Object> rawMap, Object dest, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( doc.isElementOverriden((Element) dest) ) {
            return;
        }

        super.convertTo(rawMap, dest, ctx);
    }
}
