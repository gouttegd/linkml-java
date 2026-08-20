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

import java.util.ArrayList;
import java.util.List;

/**
 * A helper object to (1) resolve type designator values into a LinkML class and
 * (2) obtain the type designator for a LinkML class.
 */
public class TypeDesignatorResolver implements ITypeDesignatorResolver {

    private ITypeDesignatorResolver innerResolver = new DefaultTypeDesignatorResolver();

    @Override
    public ClassInfo resolve(String designator, ClassInfo base) throws LinkMLRuntimeException {
        return innerResolver.resolve(designator, base);
    }

    /**
     * Resolves a multi-valued designator into a LinkML class.
     * <p>
     * This will resolve into the most specific class among the classes designated
     * by the provided value. The behaviour when the list contains several
     * designators for different classes at the same hierarchical level is
     * undefined.
     * 
     * @param designators The type designator values.
     * @param base        The base LinkML class. This would typically be the class
     *                    that defines the type designator slot, though it could
     *                    also be any class below it.
     * @return The {@link ClassInfo} object representing the most specific
     *         designated class, or <code>null</code> if none of the designator
     *         values could be resolved into a descendant of the <code>base</code>
     *         class (or the <code>base</code> class itself).
     * @exception LinkMLRuntimeException If any error occurs during the resolution
     *                                   attempt.
     */
    public ClassInfo resolve(List<String> designators, ClassInfo base) throws LinkMLRuntimeException {
        int length = -1;
        ClassInfo ci = null;
        for ( String designator : designators ) {
            ClassInfo candidate = resolve(designator, base);
            if ( candidate != null ) {
                int nParents = candidate.getParents().size();
                if ( nParents > length ) {
                    ci = candidate;
                    length = nParents;
                }
            }
        }

        return ci;
    }

    @Override
    public Object getDesignator(ClassInfo klass) throws LinkMLRuntimeException {
        return innerResolver.getDesignator(klass);
    }

    /**
     * Gets values that designate the provided class along with its ancestors.
     * 
     * @param klass The {@link ClassInfo} object representing the class to
     *              designate.
     * @return The list of designator values. The list is ordered from the highest
     *         ancestor (the class in which the type designator slot is defined)
     *         down to the provided class itself. The values in the list can be
     *         either class URIs, strings representing shortened class URIs, or
     *         class names, depending on the type designator slot.
     * @throws LinkMLRuntimeException If the given class has no type designator
     *                                slot, or if any error occurs when attempting
     *                                to find the correct designator values.
     */
    public List<Object> getDesignators(ClassInfo klass) throws LinkMLRuntimeException {
        List<Object> designators = new ArrayList<>();
        List<ClassInfo> parents = klass.getParents();
        for ( int i = parents.size() - 1; i >= 0; i-- ) {
            ClassInfo parent = parents.get(i);
            if ( parent.hasTypeDesignator() ) {
                designators.add(innerResolver.getDesignator(parent));
            }
        }
        designators.add(innerResolver.getDesignator(klass));

        return designators;
    }
}
