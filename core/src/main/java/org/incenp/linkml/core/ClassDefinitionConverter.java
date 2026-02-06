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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.model.ClassDefinition;
import org.incenp.linkml.model.SlotDefinition;

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
        Object attributes = rawMap.remove("attributes");
        if ( attributes != null ) {
            ((ClassDefinition) dest).setAttributes(convertLocalSlotDefinitions(toMap(attributes), ctx));
        }

        Object slotUsages = rawMap.remove("slot_usage");
        if ( slotUsages != null ) {
            ((ClassDefinition) dest).setSlotUsage(convertLocalSlotDefinitions(toMap(slotUsages), ctx));
        }

        super.convertTo(rawMap, dest, ctx);
    }

    private List<SlotDefinition> convertLocalSlotDefinitions(Map<String, Object> rawMap, ConverterContext ctx)
            throws LinkMLRuntimeException {
        List<SlotDefinition> value = new ArrayList<>();
        for ( Map.Entry<String, Object> rawItem : rawMap.entrySet() ) {
            // We need a purely local object, notwithstanding the unicity constraint on
            // SlotDefinition; so we do not look up in the global context, and we do not
            // store the newly created object there.
            SlotDefinition def = new SlotDefinition();
            def.setName(rawItem.getKey());
            if ( rawItem.getValue() != null ) {
                ctx.getConverter(SlotDefinition.class).convertTo(toMap(rawItem.getValue()), def, ctx);
            }
            value.add(def);
        }

        return value;
    }
}
