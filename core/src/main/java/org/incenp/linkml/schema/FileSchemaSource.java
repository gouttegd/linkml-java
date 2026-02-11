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

package org.incenp.linkml.schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a schema location pointing to a local file.
 */
public class FileSchemaSource implements ISchemaSource {

    private File file;
    private InputStream stream;

    public FileSchemaSource(File file) {
        this.file = file;
    }

    public FileSchemaSource(String filename) {
        this.file = new File(filename);
    }

    @Override
    public String getBase() {
        return file.getParent();
    }

    @Override
    public InputStream getStream() throws IOException {
        if ( stream == null ) {
            stream = new FileInputStream(file);
        }
        return stream;
    }

    @Override
    public int hashCode() {
        return file.getAbsolutePath().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if ( object instanceof FileSchemaSource ) {
            return ((FileSchemaSource) object).file.getAbsolutePath().equals(file.getAbsolutePath());
        }
        return false;
    }
}
