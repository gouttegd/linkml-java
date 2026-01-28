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

/**
 * This exception is thrown when processing a LinkML schema that does not
 * conform to the LinkML specification.
 */
public class InvalidSchemaException extends Exception {

    private static final long serialVersionUID = -9079693184858658675L;

    public InvalidSchemaException(String msg) {
        super(msg);
    }

    public InvalidSchemaException(String msg, Throwable inner) {
        super(msg, inner);
    }
}
