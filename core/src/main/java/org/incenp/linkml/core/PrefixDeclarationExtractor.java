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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A helper class to extract prefix declarations from a raw object.
 * <p>
 * This class is intended to support the mechanism tentatively outlined in
 * <a href="https://github.com/linkml/linkml/issues/1114">LinkML ticket
 * #1114</a>, where instance data can declare their own prefixes (supplementing
 * or overriding the prefixes emitted by the schema itself) using an ad-hoc data
 * structure that can be recognized by the fact that it contains two slots with
 * the <code>shacl:prefix</code> and <code>shacl:namespace</code> URIs.
 * <p>
 * The LinkML meta model is already an example of that mechanism being used: a
 * schema definition can (and most often, will indeed) have a
 * <code>prefixes</code> section holding a list of <code>Prefix</code>
 * instances.
 */
public class PrefixDeclarationExtractor {

    private final static String SHACL_PREFIX = "http://www.w3.org/ns/shacl#prefix";
    private final static String SHACL_NS = "http://www.w3.org/ns/shacl#namespace";

    private Slot containerSlot;
    private Slot nameSlot;
    private Slot prefixSlot;

    /**
     * Gets a prefix declaration extractor (if possible) for the specified LinkML
     * class.
     * <p>
     * This method will examine the class to check whether it contains a slot
     * intended to hold prefix declarations.
     * 
     * @param klass The object representing the LinkML class to check.
     * @return An extractor that can extract prefix declarations from an instance of
     *         the given class, or <code>null</code> if the class has no slot for
     *         prefix declarations.
     */
    public static PrefixDeclarationExtractor getExtractor(ClassInfo klass) {
        for ( Slot slot : klass.getSlots() ) {
            // We assume a slot used for declaring prefix expansions must be (1)
            // multi-valued and (2) inlined, because such a slot would not make much sense
            // otherwise.
            if ( !slot.isMultivalued() || slot.getInliningMode() == InliningMode.NO_INLINING ) {
                continue;
            }

            ClassInfo ci = ClassInfo.get(slot.getInnerType());
            if ( ci == null ) {
                // Not a class, so no chance it can interest us
                continue;
            }

            // Not a mistake. What SHACL calls the prefix is what we call the prefix NAME.
            Slot nameSlot = ci.getSlotByURI(SHACL_PREFIX);
            Slot prefixSlot = ci.getSlotByURI(SHACL_NS);

            if ( nameSlot != null && prefixSlot != null ) {
                PrefixDeclarationExtractor extractor = new PrefixDeclarationExtractor();
                extractor.containerSlot = slot;
                extractor.nameSlot = nameSlot;
                extractor.prefixSlot = prefixSlot;
                return extractor;
            }
        }

        // No suitable slot found
        return null;
    }

    /**
     * Extracts prefix declarations from the raw representation of a LinkML object.
     * 
     * @param rawMap The raw map representing the LinkML object from which to
     *               extract prefix declarations. If it does contain the key
     *               expected to hold prefix declarations, it will be removed from
     *               the map.
     * @param dest   The instance of the LinkML object that will hold the extracted
     *               prefix declarations.
     * @param ctx    The global converter context.
     * @return A map with the extracted prefix (empty if there are no prefix
     *         declarations in the raw map).
     * @throws LinkMLRuntimeException If any error occurs when attempting to convert
     *                                the prefix declarations.
     */
    public Map<String, String> extractPrefixes(Map<String, Object> rawMap, Object dest, ConverterContext ctx)
            throws LinkMLRuntimeException {
        Map<String, String> prefixMap = new HashMap<>();
        Object rawPrefixes = rawMap.remove(containerSlot.getLinkMLName());
        if ( rawPrefixes != null ) {
            ctx.getConverter(containerSlot).convertForSlot(rawPrefixes, dest, containerSlot, ctx);

            Object convertedPrefixes = containerSlot.getValue(dest);
            for ( Object convertedPrefix : toList(convertedPrefixes) ) {
                String name = nameSlot.getValue(convertedPrefix).toString();
                String prefix = prefixSlot.getValue(convertedPrefix).toString();
                prefixMap.put(name, prefix);
            }
        }

        return prefixMap;
    }

    // FIXME: We should have a centralised location for this kind of helper methods.
    @SuppressWarnings("unchecked")
    private List<Object> toList(Object value) throws LinkMLRuntimeException {
        if ( !(value instanceof List) ) {
            throw new LinkMLValueError("Invalid value type, list expected");
        }
        return (List<Object>) value;
    }
}
