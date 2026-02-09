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

import java.util.Map;

import org.incenp.linkml.model.Prefix;
import org.incenp.linkml.model.SchemaDefinition;

/**
 * A converter object specifically intended to convert {@link SchemaDefinition}
 * objects.
 * <p>
 * We need a subclass for {@link SchemaDefinition} objects because we want to
 * ensure that prefix declarations are processed early on when converting the
 * schema object, so that they can be made available to the converter context as
 * soon as possible.
 * <p>
 * We cannot rely on the fact that the <code>prefixes</code> block is typically
 * somewhere near the beginning of a LinkML schema, because (1) there is no
 * guarantee of that (the LinkML spec does not mandate an order for any of the
 * keys that make up the SchemaDefinition class), and (2) in any case since the
 * YAML object has been transformed into a Map, we have no guarantee that we
 * iterate over the keys in the same order than they appear in the source file.
 */
public class SchemaDefinitionConverter extends ObjectConverter {

    private final static String PREFIXES_KEY = "prefixes";

    public SchemaDefinitionConverter() {
        super(SchemaDefinition.class);
    }

    @Override
    public void convertTo(Map<String, Object> rawMap, Object dest, ConverterContext ctx) throws LinkMLRuntimeException {
        // Pre-process the prefixes slot before everything else
        Object prefixes = rawMap.remove(PREFIXES_KEY);
        if ( prefixes != null ) {
            Slot slot = getSlot(PREFIXES_KEY);
            ctx.getConverter(Prefix.class).convertForSlot(prefixes, dest, slot, ctx);
            ctx.addPrefixes(((SchemaDefinition) dest).getPrefixes());
        }

        super.convertTo(rawMap, dest, ctx);
    }
}
