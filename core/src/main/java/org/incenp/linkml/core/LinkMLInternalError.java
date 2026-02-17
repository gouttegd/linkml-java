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
 * An exception caused by an internal error within the LinkML runtime, which
 * reveals either (1) a bug in the runtime or (2) an issue with the generated
 * code that is supposed to be used with the runtime.
 * <p>
 * Either way, this is an error that should not happen and that is independent
 * of the user's data.
 */
public class LinkMLInternalError extends LinkMLRuntimeException {

    private static final long serialVersionUID = 4460399470375040486L;

    public LinkMLInternalError(String msg) {
        super(msg);
    }

    public LinkMLInternalError(String msg, Throwable inner ) {
        super(msg, inner);
    }
}
