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
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.catalog.Catalog;
import javax.xml.catalog.CatalogException;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogFeatures.Feature;
import javax.xml.catalog.CatalogManager;

/**
 * A schema resolver that relies on a XML Catalog to resolve import names into
 * schema sources.
 */
public class XMLCatalogSchemaResolver implements ISchemaResolver {

    private final static String UNRESOLVABLE_SCHEMA = "Cannot resolve schema name '%s'";

    private URI catalogURI;
    private Catalog catalog;
    private boolean needsReset;

    public XMLCatalogSchemaResolver(File catalogFile) throws CatalogException {
        catalogURI = catalogFile.toURI();
        parseCatalog();

        /*
         * There is a frankly astonishing bug with the `matchURI` method in Java 11 to
         * 18, causing the method to always return the same value after the first time
         * it has found a match. The only workaround is to re-initialise (that is,
         * re-parse) the entire catalog after every successful lookup. :(
         * 
         * See <https://bugs.openjdk.org/browse/JDK-8253569>.
         */
        needsReset = Integer.valueOf(System.getProperty("java.specification.version")) < 19;
    }

    private void parseCatalog() throws CatalogException {
        CatalogFeatures features = CatalogFeatures.builder()
                .with(Feature.PREFER, "system")
                .with(Feature.DEFER, "false")
                .with(Feature.RESOLVE, "continue")
                .build();
        catalog = CatalogManager.catalog(features, catalogURI);
    }

    @Override
    public ISchemaSource resolve(String name, String base) throws InvalidSchemaException {
        if ( !name.contains(":") ) {
            // Local file, relative to the base directory
            File file = new File(base, name);
            if ( !file.exists() ) {
                throw new InvalidSchemaException(String.format(UNRESOLVABLE_SCHEMA, name));
            }
            return new FileSchemaSource(file);
        }

        String resolved = catalog.matchURI(name);
        if ( resolved != null ) {
            if ( needsReset ) {
                parseCatalog();
            }
            File file = new File(URI.create(resolved));
            if ( !file.exists() ) {
                throw new InvalidSchemaException(String.format(UNRESOLVABLE_SCHEMA, name));
            }
            return new FileSchemaSource(file);
        }

        try {
            return new URLSchemaSource(name);
        } catch ( URISyntaxException | MalformedURLException e ) {
            throw new InvalidSchemaException(String.format(UNRESOLVABLE_SCHEMA, name), e);
        }
    }


}
