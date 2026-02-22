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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.incenp.linkml.core.sample.SimpleClass;
import org.incenp.linkml.schema.model.SchemaDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class PrefixDeclarationExtractorTest {

    @Test
    void testGetPrefixDeclarationExtractor() {
        PrefixDeclarationExtractor ex = PrefixDeclarationExtractor.getExtractor(SchemaDefinition.class);
        Assertions.assertNotNull(ex);

        ex = PrefixDeclarationExtractor.getExtractor(SimpleClass.class);
        Assertions.assertNull(ex);
    }

    @Test
    void testExtractPrefixes() throws IOException, LinkMLRuntimeException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        FileInputStream stream = new FileInputStream(new File("src/test/resources/schemas/organism.yaml"));
        @SuppressWarnings("unchecked")
        Map<String, Object> raw = mapper.readValue(stream, Map.class);

        ConverterContext ctx = new ConverterContext();
        PrefixDeclarationExtractor ex = PrefixDeclarationExtractor.getExtractor(SchemaDefinition.class);
        SchemaDefinition sd = new SchemaDefinition();
        Map<String, String> prefixMap = ex.extractPrefixes(raw, sd, ctx);
        Assertions.assertTrue(prefixMap.containsKey("linkml"));
        Assertions.assertEquals("https://w3id.org/linkml/", prefixMap.get("linkml"));
    }
}
