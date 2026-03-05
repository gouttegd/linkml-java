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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * Represents the type of a LinkML object.
 */
public enum ObjectType {
    /**
     * A LinkML class.
     */
    CLASS,

    /**
     * A LinkML enumeration.
     * <p>
     * This does not cover <em>dynamic</em> enumerations, which are currently
     * completely unsupported.
     */
    ENUM,

    /**
     * A scalar type (excluding enumerations).
     */
    TYPE,

    /**
     * Not a LinkML object.
     */
    NONE;

    /**
     * Gets the type of LinkML object that is represented by a Java type.
     * 
     * @param type The Java type to query.
     * @return The corresponding LinkML type.
     */
    public static ObjectType get(Class<?> type) {
        // FIXME: We might have to deal with custom types as well at some point.
        if ( type.isPrimitive() || type == String.class || type == Boolean.class || type == Integer.class
                || type == Float.class || type == Double.class || type == ZonedDateTime.class || type == LocalDate.class
                || type == LocalTime.class || type == URI.class ) {
            return TYPE;
        } else if ( type.isEnum() ) {
            // FIXME: Not necessarily a *LinkML* enum
            return ENUM;
        } else if ( type.getSuperclass() == null ) {
            // Could be Object, an interface, or void -- in any case, cannot be a LinkML
            // object at all
            return NONE;
        } else {
            // FIXME: Not necessarily a *LinkML* class
            return CLASS;
        }
    }
}
