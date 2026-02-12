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

/**
 * An annotation to indicate that a field is intended to hold “extensions”, that
 * is, any unknown property that are associated with the object the field
 * belongs to.
 * <p>
 * Only a field typed as a <code>Map&lt;String, Object&gt;</code> should be
 * annotated with this annotation.
 */
// FIXME: Figure out if there is a way to enforce the type restriction
// ("this annotation can only be used on a Map<String, Object> field")
// at compile-time.
@Retention(RUNTIME)
@Target(FIELD)
public @interface ExtensionHolder {

}
