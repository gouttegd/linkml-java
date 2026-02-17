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

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Converts raw objects into URI values.
 */
public class URIConverter extends ScalarConverterBase {

    @Override
    public Class<?> getType() {
        return URI.class;
    }

    @Override
    protected Object convertImpl(Object raw, ConverterContext ctx) throws LinkMLRuntimeException {
        try {
            return new URI(raw.toString());
        } catch ( URISyntaxException e ) {
            throw new LinkMLValueError(String.format("Invalid value, URI expected: %s", raw), e);
        }
    }

}
