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

package org.incenp.linkml.core.types;

import org.incenp.linkml.core.types.BinaryBlob.BinaryEncoding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BinaryBlobTest {

    @Test
    void testBase64Decoding() {
        byte[] decoded = BinaryBlob.decodeFromBase64("SGVsbG8sIFdvcmxkIQ==");
        Assertions.assertEquals("Hello, World!", new String(decoded));
    }

    @Test
    void testBase64Encoding() {
        String encoded = BinaryBlob.encodeToBase64("Hello, World!".getBytes());
        Assertions.assertEquals("SGVsbG8sIFdvcmxkIQ==", encoded);
    }

    @Test
    void testBase16Decoding() {
        byte[] decoded = BinaryBlob.decodeFromBase16("48656C6C6F2C20576F726C6421");
        Assertions.assertEquals("Hello, World!", new String(decoded));

        decoded = BinaryBlob.decodeFromBase16("48656c6c6f2c20576f726c6421");
        Assertions.assertEquals("Hello, World!", new String(decoded));

        decoded = BinaryBlob.decodeFromBase16("48 65 6C 6C 6F 2C 20 57 6F 72 6C 64 21");
        Assertions.assertEquals("Hello, World!", new String(decoded));
    }

    @Test
    void testBase16Encoding() {
        String encoded = BinaryBlob.encodeToBase16("Hello, World!".getBytes());
        Assertions.assertEquals("48656C6C6F2C20576F726C6421", encoded);
    }

    @Test
    void testConstructor() {
        BinaryBlob blob = new BinaryBlob("SGVsbG8sIFdvcmxkIQ==", BinaryEncoding.BASE64);
        Assertions.assertEquals("Hello, World!", new String(blob.getValue()));

        blob = new BinaryBlob("48656C6C6F2C20576F726C6421", BinaryEncoding.BASE16);
        Assertions.assertEquals("Hello, World!", new String(blob.getValue()));
    }

    @Test
    void testEmpty() {
        BinaryBlob blob = new BinaryBlob("", BinaryEncoding.BASE64);
        Assertions.assertTrue(blob.getValue().length == 0);

        blob = new BinaryBlob("", BinaryEncoding.BASE16);
        Assertions.assertTrue(blob.getValue().length == 0);
    }

    @Test
    void testSetter() {
        BinaryBlob blob = new BinaryBlob();
        blob.setValue("SGVsbG8=", BinaryEncoding.BASE64);
        Assertions.assertEquals("Hello", new String(blob.getValue()));

        blob.setValue("576F726C64", BinaryEncoding.BASE16);
        Assertions.assertEquals("World", new String(blob.getValue()));
    }

    @Test
    void testGetter() {
        BinaryBlob blob = new BinaryBlob("SGVsbG8sIFdvcmxkIQ==", BinaryEncoding.BASE64);
        Assertions.assertEquals("48656C6C6F2C20576F726C6421", blob.getValue(BinaryEncoding.BASE16));
    }

    @Test
    void testEqualsAndHashcode() {
        BinaryBlob b1 = new BinaryBlob("SGVsbG8sIFdvcmxkIQ==", BinaryEncoding.BASE64);
        BinaryBlob b2 = new BinaryBlob("48656C6C6F2C20576F726C6421", BinaryEncoding.BASE16);

        Assertions.assertFalse(b1 == b2);
        Assertions.assertTrue(b1.equals(b2));
        Assertions.assertEquals(b1, b2);
        Assertions.assertEquals(b1.hashCode(), b2.hashCode());
    }
}
