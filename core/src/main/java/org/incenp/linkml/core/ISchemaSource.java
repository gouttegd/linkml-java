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
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.incenp.linkml.core;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents the source location of a schema.
 */
public interface ISchemaSource {

    /**
     * Gets the base location of this source.
     * <p>
     * The base location is the location from which relative import references are
     * resolved.
     * 
     * @return The base location. May be <code>null</code>.
     */
    public String getBase();

    /**
     * Opens the location into a readable stream.
     * 
     * @throws IOException If the location cannot be opened for any reason.
     */
    public InputStream getStream() throws IOException;
}
