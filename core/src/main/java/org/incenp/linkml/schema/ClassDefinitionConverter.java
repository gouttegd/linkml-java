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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.core.ObjectConverter;
import org.incenp.linkml.core.Slot;
import org.incenp.linkml.schema.model.ClassDefinition;
import org.incenp.linkml.schema.model.SlotDefinition;

/**
 * A converter object specifically intended to convert {@link ClassDefinition}
 * objects.
 * <p>
 * We need a subclass for {@link ClassDefinition} objects because class
 * definitions purposefully violate the unicity constraint of the
 * {@link SlotDefinition} class. A slot definition is supposed to be a unique
 * object (only one object with the same name can exist in the global context).
 * But several classes can have an attribute with the same name, that should not
 * imply that they share the same attribute! (In fact, one of the reasons for
 * the concept of attributes is precisely so that classes can have “local slots”
 * – slots whose definition has no impact outside of the declaring class.
 * Therefore, within a LinkML schema, there may very well be several
 * {@link SlotDefinition} objects with the same name: one global slot at most,
 * plus an arbitrary number of class-specific attributes.
 * <p>
 * In LinkML-Py, this violation of the unicity constraint is apparently managed
 * by prepending the name of the declaring class to the name of the attribute,
 * as in <code>class__slotname</code>, so that all slot definitions (including
 * definitions of class-specific attributes) are globally unique.
 * <p>
 * The approach adopted here is simply <em>not</em> to put the attribute
 * definitions in the global context at all, so that they are really specific to
 * the class in which they are defined. But this requires overriding the default
 * behaviour of the {@link ObjectConverter}, which puts all unique objects (that
 * is, all instances of classes that have an identifier slot) in the global
 * context.
 * <p>
 * Likewise for the {@link SlotDefinition} objects found in the
 * <code>slot_usage</code> section: they are really local to the class and
 * should <em>not</em> be treated as global objects.
 */
public class ClassDefinitionConverter extends ObjectConverter {

    public ClassDefinitionConverter() {
        super(ClassDefinition.class);
    }

    @Override
    public void convertTo(Map<String, Object> rawMap, Object dest, ConverterContext ctx) throws LinkMLRuntimeException {
        ClassDefinition def = (ClassDefinition) dest;
        Object attributes = rawMap.remove("attributes");
        if ( attributes != null ) {
            def.setAttributes(convertLocalSlotDefinitions(toMap(attributes), ctx));
        }

        Object slotUsages = rawMap.remove("slot_usage");
        if ( slotUsages != null ) {
            def.setSlotUsage(convertLocalSlotDefinitions(toMap(slotUsages), ctx));

            // Additionally inject on each slot usage a reference pointing to the global
            // slot that is being refined, for convenience.
            Slot globalSlot = Slot.getSlot(SlotDefinition.class, "globalSlot");
            for ( SlotDefinition sd : def.getSlotUsage() ) {
                ctx.getObject(globalSlot, sd.getName(), sd);
            }
        }

        super.convertTo(rawMap, dest, ctx);
    }

    private List<SlotDefinition> convertLocalSlotDefinitions(Map<String, Object> rawMap, ConverterContext ctx)
            throws LinkMLRuntimeException {
        List<SlotDefinition> value = new ArrayList<>();
        ObjectConverter converter = (ObjectConverter) ctx.getConverter(SlotDefinition.class);
        for ( Map.Entry<String, Object> rawItem : rawMap.entrySet() ) {
            // We need a purely local object, notwithstanding the unicity constraint on
            // SlotDefinition; so we do not look up in the global context, and we do not
            // store the newly created object there.
            SlotDefinition def = new SlotDefinition();
            def.setName(rawItem.getKey());
            if ( rawItem.getValue() != null ) {
                converter.convertTo(toMap(rawItem.getValue()), def, ctx);
            }
            value.add(def);
        }

        return value;
    }
}
