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

import java.net.URI;
import java.util.Map;

import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.core.ObjectConverter;
import org.incenp.linkml.core.Slot;
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

    private SchemaProcessingContext context;
    private boolean checkOverriding;

    /**
     * Creates a new converter for the specified type of element.
     * 
     * @param klass   The type of element to convert.
     * @param context The context for which elements are to be converted.
     */
    public ElementConverter(Class<? extends Element> klass, SchemaProcessingContext context) {
        super(klass);
        this.context = context;
        this.checkOverriding = true;
    }

    /**
     * Creates a new converter for the specified type of element.
     * 
     * @param klass           The type of element to convert.
     * @param context         The context for which elements are to be converted.
     * @param checkOverriding If <code>true</code>, this class will not check
     *                        whether a given element is overridden. This is
     *                        intended to allow subclasses to perform this check
     *                        themselves.
     */
    public ElementConverter(Class<? extends Element> klass, SchemaProcessingContext context, boolean checkOverriding) {
        super(klass);
        this.context = context;
        this.checkOverriding = checkOverriding;
    }

    @Override
    public void convertTo(Map<String, Object> rawMap, Object dest, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( !checkOverriding || !isOverridden((Element) dest) ) {
            super.convertTo(rawMap, dest, ctx);
        }
    }

    /**
     * Checks whether the schema schema is overridden by another one about the given
     * element.
     * <p>
     * If the current schema is found to override a prior definition (instead of
     * being overridden itself), then this method ensures that the current schema
     * takes precedence.
     * 
     * @param element The element to check.
     * @return <code>true</code> if the element is overridden, and therefore does
     *         not need to be processed any further.
     * @throws LinkMLRuntimeException If an error occurs when deleting the prior
     *                                definition, if any (this should never happen).
     */
    protected boolean isOverridden(Element element) throws LinkMLRuntimeException {
        URI fromSchema = element.getFromSchema();
        if ( fromSchema != null ) {
            if ( context.isInImportChain(fromSchema) ) {
                // "Pre-overriding": The element was defined in a schema that came earlier in
                // the same import chain as the current schema, so the earlier definition takes
                // precedence.
                return true;
            } else {
                // "Post-overriding": The element was defined in a schema that came from an
                // earlier import chain, so the current schema takes precedence. As per
                // LinkML-Py's observed behaviour, the prior definition should be completely
                // ignored, so here we need to "erase" it.
                for ( Slot slot : klass.getSlots() ) {
                    slot.setValue(element, null);
                }
            }
        } else {
            // The element has not been defined before.
        }

        element.setFromSchema(context.getCurrentSchema());
        return false;
    }
}
