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

package org.incenp.linkml.schema;

import java.io.File;
import java.net.MalformedURLException;

/**
 * The schema resolver that implements the default behaviour for resolving
 * <code>imports</code> declarations within a LinkML schema.
 * <p>
 * If the name to resolve contains a colon, this resolver assumes it is a
 * relative file name, and attempts to find it on the local file system.
 * Otherwise, it assumes the name is a URI pointing to a remote resource.
 * <p>
 * Of note, this resolver automatically and silently redirects the
 * <code>https://w3id.org/linkml/types.yaml</code> schema name to a version that
 * is embedded with the LinkML-Java runtime. That schema is expected to be
 * imported in virtually all LinkML schemas, so we don’t want to have to always
 * fetch it from a remote server.
 */
public class DefaultSchemaResolver implements ISchemaResolver {

    private final static String TYPES_SCHEMA = "https://w3id.org/linkml/types.yaml";
    private final static String UNRESOLVABLE_SCHEMA = "Cannot resolve schema name '%s'";

    @Override
    public ISchemaSource resolve(String name, String base) throws InvalidSchemaException {
        if (!name.contains(":")) {
            // Local file, relative to the base directory
            File file = new File(base, name);
            if ( !file.exists() ) {
                throw new InvalidSchemaException(String.format(UNRESOLVABLE_SCHEMA, name));
            }
            return new FileSchemaSource(file);
        }

        if ( name.equals(TYPES_SCHEMA) ) {
            // Redirect the standard linkml:types schema to the embedded version.
            return new EmbeddedSchemaSource("types.yaml");
        }
        try {
            return new URLSchemaSource(name);
        } catch ( MalformedURLException e ) {
            throw new InvalidSchemaException(String.format(UNRESOLVABLE_SCHEMA, name), e);
        }
    }

}
