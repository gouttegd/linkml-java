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

import org.incenp.linkml.core.annotations.Identifier;

/**
 * Represents the various modes of “inlining” instances of a class, in
 * serialisations that do support inlining (e.g. JSON, YAML).
 */
public enum InliningMode {
    /**
     * Instances are not inlined.
     */
    NO_INLINING,

    /**
     * Instances are inlined as a list.
     */
    LIST,

    /**
     * Instances are inlined as a dictionary. This is only possible if their class
     * defines an {@link Identifier} field.
     */
    DICT,

    /**
     * Instances are inlined as a “simple” dictionary. This is only possible if (1)
     * their class defines an {@link Identifier} field and (2) their class has only
     * one other field beyond the identifier field.
     */
    SIMPLE_DICT
}
