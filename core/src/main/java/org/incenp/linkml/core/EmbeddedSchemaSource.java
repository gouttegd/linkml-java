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
 * Represents a schema location pointing to an embedded Java resource.
 */
public class EmbeddedSchemaSource implements ISchemaSource {

    private String name;
    private InputStream stream;

    public EmbeddedSchemaSource(String name) {
        this.name = name;
    }

    @Override
    public String getBase() {
        return null;
    }

    @Override
    public InputStream getStream() throws IOException {
        if ( stream == null ) {
            stream = EmbeddedSchemaSource.class.getClassLoader().getResourceAsStream(name);
        }
        return stream;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if ( object instanceof EmbeddedSchemaSource ) {
            return ((EmbeddedSchemaSource) object).name.equals(name);
        }
        return false;
    }
}
