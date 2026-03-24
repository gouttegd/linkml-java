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

package org.incenp.linkml.ext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.schema.model.ClassDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectLoaderTest {

    private ObjectLoader loader = new ObjectLoader();

    @Test
    void testReadingSimpleClass() throws IOException, LinkMLRuntimeException {
        ClassDefinition cd = loader.loadObject(new File("src/test/resources/ext/samples/class-definition.yaml"),
                ClassDefinition.class);

        Assertions.assertEquals("a string", cd.getAlias());
        Assertions.assertEquals("https://example.org/a/URI", cd.getClassUri());
        Assertions.assertTrue(cd.getTreeRoot());
    }

    @Test
    void testReadingListOfSimpleClass() throws IOException, LinkMLRuntimeException {
        List<ClassDefinition> cds = loader
                .loadObjects(new File("src/test/resources/ext/samples/class-definition-list.yaml"),
                        ClassDefinition.class);

        Assertions.assertEquals("a string", cds.get(0).getAlias());
        Assertions.assertEquals("another string", cds.get(1).getAlias());
    }

    @Test
    void testWritingSimpleClass() throws IOException, LinkMLRuntimeException {
        ClassDefinition cd = new ClassDefinition();
        cd.setName("SimpleClass");
        cd.setAlias("a string");
        cd.setClassUri("https://example.org/a/URI");
        cd.setTreeRoot(false);

        File tmp = new File("src/test/resources/ext/samples/class-definition.yaml.out");
        loader.dumpObject(tmp, cd);

        ClassDefinition cd2 = loader.loadObject(tmp, ClassDefinition.class);
        Assertions.assertFalse(cd == cd2);
        Assertions.assertTrue(cd.equals(cd2));

        tmp.delete();
    }

    @Test
    void testWritingListOfSimpleClass() throws IOException, LinkMLRuntimeException {
        List<ClassDefinition> list = new ArrayList<>();
        ClassDefinition cd = new ClassDefinition();
        cd.setName("SimpleClass");
        cd.setAlias("a string");
        list.add(cd);
        cd = new ClassDefinition();
        cd.setName("AnotherClass");
        cd.setClassUri("https://example.org/a/URI");
        list.add(cd);

        File tmp = new File("src/test/resources/ext/samples/class-definition-list.yaml.out");
        loader.dumpObjects(tmp, list);

        List<ClassDefinition> list2 = loader.loadObjects(tmp, ClassDefinition.class);
        Assertions.assertTrue(list.equals(list2));

        tmp.delete();
    }

    @Test
    void testWritingSimpleClassAsJSON() throws IOException, LinkMLRuntimeException {
        ClassDefinition cd = new ClassDefinition();
        cd.setName("SimpleClass");
        cd.setAlias("a string");
        cd.setClassUri("https://example.org/a/URI");
        cd.setTreeRoot(false);

        File tmp = new File("src/test/resources/ext/samples/class-definition.json.out");
        loader.dumpObject(tmp, cd, DataFormat.JSON);

        ClassDefinition cd2 = loader.loadObject(tmp, ClassDefinition.class, DataFormat.JSON);
        Assertions.assertFalse(cd == cd2);
        Assertions.assertTrue(cd.equals(cd2));

        tmp.delete();
    }
}
