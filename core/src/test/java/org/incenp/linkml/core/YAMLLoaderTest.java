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

package org.incenp.linkml.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.incenp.linkml.core.sample.SampleEnum;
import org.incenp.linkml.core.sample.SimpleClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class YAMLLoaderTest {

    private YAMLLoader loader = new YAMLLoader();

    @Test
    void testReadingSimpleClass() throws IOException, LinkMLRuntimeException {
        SimpleClass sc = loader.loadObject(new File("src/test/resources/core/samples/simple-class.yaml"),
                SimpleClass.class);

        Assertions.assertEquals("a string", sc.getFoo());
        Assertions.assertEquals("https://example.org/a/URI", sc.getBar().toString());
        Assertions.assertTrue(sc.getBaz());
        Assertions.assertEquals("a string in a list", sc.getFoos().get(0));
        Assertions.assertEquals(SampleEnum.FOO, sc.getType());
    }

    @Test
    void testReadingListOfSimpleClass() throws IOException, LinkMLRuntimeException {
        List<SimpleClass> scs = loader.loadObjects(new File("src/test/resources/core/samples/simple-class-list.yaml"),
                SimpleClass.class);

        Assertions.assertEquals("a string", scs.get(0).getFoo());
        Assertions.assertEquals("another string", scs.get(1).getFoo());
    }
}
