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
import java.util.HashMap;
import java.util.Map;

/**
 * A type designator resolver that uses pre-registered “aliases” to resolve
 * designator values.
 * <p>
 * If a type designator cannot be resolved from the aliases, then this resolver
 * will fallback to using informations from annotated LinkML classes, as
 * {@link DefaultTypeDesignatorResolver} does.
 */
public class AliasTypeDesignatorResolver extends DefaultTypeDesignatorResolver {

    private Map<ClassInfo, Map<String, ClassInfo>> aliasMap = new HashMap<>();
    private Map<ClassInfo, String> reverseMap = new HashMap<>();

    /**
     * Registers an alias for a given class.
     * <p>
     * The alias must be unique for all classes that share a given designator slot
     * – that is, for all classes that are descendants of the class that defines the
     * slot (including that class).
     * <p>
     * Of note, a class may have more than one alias, in which
     * {@link #getDesignator(ClassInfo)} will return the last registered one.
     * 
     * @param klass The class for which to register an alias.
     * @param alias The alias to register.
     */
    public void registerAlias(ClassInfo klass, String alias) {
        ClassInfo root = klass.getDesignatorBase();
        if ( root == null ) {
            // Don't think it is worth it to throw an exception... just ignore.
            return;
        }

        Map<String, ClassInfo> classAliases = aliasMap.get(root);
        if ( classAliases == null ) {
            classAliases = new HashMap<>();
            aliasMap.put(root, classAliases);
        }
        classAliases.put(alias, klass);
        reverseMap.put(klass, alias);
    }

    @Override
    public ClassInfo resolve(String designator, ClassInfo base) throws LinkMLRuntimeException {
        ClassInfo root = base.getDesignatorBase();
        ClassInfo resolved = null;
        if ( root == null ) {
            throw new LinkMLRuntimeException(String.format(NO_DESIGNATOR, base.getName()));
        }

        Map<String, ClassInfo> classAliases = aliasMap.get(root);
        if ( classAliases != null ) {
            resolved = classAliases.get(designator);
            if ( resolved != null && (resolved != base && !resolved.getParents().contains(base)) ) {
                // We got something, but not what we expected; do not fallback.
                return null;
            }
        }

        if ( resolved == null ) {
            resolved = super.resolve(designator, base);
        }

        return resolved;
    }

    @Override
    public Object getDesignator(ClassInfo klass) throws LinkMLRuntimeException {
        Slot designatorSlot = klass.getTypeDesignatorSlot();
        if ( designatorSlot == null ) {
            throw new LinkMLInternalError(String.format(NO_DESIGNATOR, klass.getName()));
        }

        String alias = reverseMap.get(klass);
        if ( alias == null ) {
            return super.getDesignator(klass);
        }

        if ( designatorSlot.getInnerType().equals(URI.class) ) {
            try {
                return new URI(alias);
            } catch ( URISyntaxException e ) {
                throw new LinkMLInternalError(String.format(INVALID_CLASS_URI, klass.getName()));
            }
        } else {
            return alias;
        }
    }
}
