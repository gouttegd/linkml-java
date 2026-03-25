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
import org.incenp.linkml.schema.model.SchemaDefinition;

/**
 * A converter object specifically intended to convert {@link SchemaDefinition}
 * objects.
 * <p>
 * A special converter is needed because LinkML’s own meta-schema violates the
 * unicity constraint of the <code>Element</code> class with its schema names.
 * <p>
 * The <code>SchemaDefinition</code> class, like all “schema elements” (e.g.
 * <code>SlotDefinition</code>, <code>ClassDefinition</code>, etc.), is a
 * subclass of the <code>Element</code> class, which is the class that carries,
 * among other slots, the <em>identifier slot</em> (<code>name</code>). This
 * means that schema elements share the <em>same identifier namespace</em>.
 * <p>
 * This sharing of a single identifier namespace is needed, because that’s what
 * ensures that we cannot have, for example, a <code>ClassDefinition</code> with
 * the same name than a <code>SlotDefinition</code> (if this could happen, then
 * we would have no way of knowing whether the range of a slot refers to the
 * class definition or to the type definition).
 * <p>
 * But LinkML’s own meta-schema violates the unicity constraint in several
 * places, seemingly behaving as if the name of a schema was either not an
 * identifier, or at least an identifier that does not live in the same
 * namespace than all the other schema elements. This results in several
 * conflicts where the name of a schema (that is imported by LinkML’s root
 * schema) happens to be the same as the name of another element. For example,
 * the name of the <code>types</code> schema conflicts with the name of the
 * <code>types</code> slot, and the name of the <code>mappings</code> schema
 * conflicts with the name of the <code>mappings</code> slots.
 * <p>
 * So here, we work around that issue by basically <em>not</em> treating
 * <code>SchemaDefinition</code> objects as global objects. There should be no
 * cases where we might need to query a schema by its name in the cache, so we
 * can behave as if a schema definition was a non-identifiable object (which
 * seems to be what LinkML-Py is doing).
 */
public class SchemaDefinitionConverter extends ObjectConverter {

    public SchemaDefinitionConverter() {
        super(SchemaDefinition.class);
    }

    @Override
    public Object convert(Map<String, Object> raw, String id, ConverterContext ctx) throws LinkMLRuntimeException {
        // Create the object here and give it its ID. Do NOT register it into the global
        // cache, to avoid conflicts with other schema elements.
        SchemaDefinition def = new SchemaDefinition();
        def.setName(id);
        convertTo(raw, def, ctx);
        return def;
    }
}
