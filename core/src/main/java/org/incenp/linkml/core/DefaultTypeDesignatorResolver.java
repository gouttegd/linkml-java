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

import java.net.URI;
import java.net.URISyntaxException;

/**
 * The “default” type designator resolver.
 * <p>
 * This class implements the base logic, using only the informations available
 * from the annotated LinkML classes, to handle type designators.
 */
public class DefaultTypeDesignatorResolver extends TypeDesignatorResolverBase {

    protected static final String NO_DESIGNATOR = "Type '%s' has no designator slot";
    protected final static String INVALID_CLASS_URI = "Missing or invalid class URI for type '%s'";

    @Override
    public ClassInfo resolve(String designator, ClassInfo base) throws LinkMLRuntimeException {
        // Lookup by URI
        ClassInfo ci = ClassInfo.get(designator);
        if ( ci == null ) {
            // Look up by class name; we assume that all derived classes will live in the
            // same Java package as the base class.
            String pkgName = base.getType().getPackage().getName();
            try {
                ci = ClassInfo.get(Class.forName(pkgName + "." + designator));
            } catch ( ClassNotFoundException e ) {
            }
        }

        if ( ci != null && (ci == base || ci.getParents().contains(base)) ) {
            return ci;
        }

        return null;
    }

    @Override
    public Object getDesignator(ClassInfo klass) throws LinkMLRuntimeException {
        Slot designatorSlot = klass.getTypeDesignatorSlot();
        if ( designatorSlot == null ) {
            throw new LinkMLInternalError(String.format(NO_DESIGNATOR, klass.getName()));
        }

        if ( designatorSlot.getInnerType().equals(URI.class) ) {
            try {
                return new URI(klass.getURI());
            } catch ( NullPointerException | URISyntaxException e ) {
                throw new LinkMLInternalError(String.format(INVALID_CLASS_URI, klass.getName()));
            }
        } else if ( designatorSlot.isCurieTyped() ) {
            String uri = klass.getURI();
            if ( uri == null ) {
                throw new LinkMLInternalError(String.format(INVALID_CLASS_URI, klass.getName()));
            }
            return uri;
        } else {
            return klass.getName();
        }
    }
}
