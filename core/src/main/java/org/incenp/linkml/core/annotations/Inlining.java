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

package org.incenp.linkml.core.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.incenp.linkml.core.InliningMode;

/**
 * An annotation to indicate whether the value(s) of a field should be inlined
 * or not (in serialisations that do support inlining).
 * 
 * When that annotation is not present and the type (“range”, in LinkML
 * parlance) of a field is a class that defines an {@link Identifier} slot, then
 * only the identifier is serialised (it is assumed that the full value will be
 * serialised in inline form elsewhere).
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Inlining {
    InliningMode value();
}
