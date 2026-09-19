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

import java.util.Arrays;
import java.util.Base64;

import org.incenp.linkml.core.annotations.TypeURI;

/**
 * This class represents a type intended to store arbitrary blobs of binary
 * data.
 * <p>
 * This is merely a wrapper around an array of bytes (<code>byte[]</code>). The
 * wrapper makes it easier to provide the appropriate <code>equals()</code> and
 * <code>hashCode()</code> behaviours.
 */
@TypeURI("http://www.w3.org/2001/XMLSchema#base64Binary")
public class BinaryBlob {

    private byte[] value;

    /**
     * Creates a new empty value.
     */
    public BinaryBlob() {
        value = new byte[] {};
    }

    /**
     * Creates a new value with the specified array of bytes.
     * 
     * @param value The initial content of the binary blob.
     */
    public BinaryBlob(byte[] value) {
        this.value = value;
    }

    /**
     * Creates a new value from an encoded string.
     * 
     * @param value    The initial content of the binary blob, encoded into a
     *                 string.
     * @param encoding How the initial value is encoded.
     * @throws IllegalArgumentException If the string does not contain a valid value
     *                                  for the specified encoding.
     */
    public BinaryBlob(String value, BinaryEncoding encoding) throws IllegalArgumentException {
        switch ( encoding ) {
        case BASE16:
            this.value = BinaryBlob.decodeFromBase16(value);
            break;

        case BASE64:
        default:
            this.value = BinaryBlob.decodeFromBase64(value);
            break;
        }
    }

    /**
     * Gets the value of this binary blob.
     * 
     * @return The array of bytes representing the binary blob.
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Gets the value of this binary blob as an encoded string.
     * 
     * @param encoding How to encode the value into a string.
     * @return A string representation of the binary blob.
     */
    public String getValue(BinaryEncoding encoding) {
        switch ( encoding ) {
        case BASE16:
            return BinaryBlob.encodeToBase16(value);

        case BASE64:
        default:
            return BinaryBlob.encodeToBase64(value);
        }
    }

    /**
     * Sets the value of this binary blob.
     * 
     * @param value The array of bytes that is to be the new value of this blob.
     */
    public void setValue(byte[] value) {
        this.value = value;
    }

    /**
     * Sets the value of this binary blob from an encoded string.
     * 
     * @param value    The new value of the binary blob, encoded into a string.
     * @param encoding How the value is encoded.
     * @throws IllegalArgumentException If the string does not contain a valid value
     *                                  for the specified encoding
     */
    public void setValue(String value, BinaryEncoding encoding) throws IllegalArgumentException {
        switch ( encoding ) {
        case BASE16:
            this.value = BinaryBlob.decodeFromBase16(value);
            break;

        case BASE64:
        default:
            this.value = BinaryBlob.decodeFromBase64(value);
            break;
        }
    }

    @Override
    public boolean equals(Object object) {
        if ( object == null || !(object instanceof BinaryBlob) ) {
            return false;
        }
        return Arrays.equals(value, ((BinaryBlob) object).value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    /**
     * Helper method to decode a Base64-encoded string into an array of bytes.
     * 
     * @param value The string to decode.
     * @return The decoded array of bytes.
     * @throws IllegalArgumentException If the string does not contain valid Base64.
     */
    public static byte[] decodeFromBase64(String value) throws IllegalArgumentException {
        return Base64.getDecoder().decode(value);
    }

    /**
     * Helper method to encode an array of bytes into a Base64 string.
     * 
     * @param value The array to encode.
     * @return The Base64 representation of the array.
     */
    public static String encodeToBase64(byte[] value) {
        return Base64.getEncoder().encodeToString(value);
    }

    /**
     * Helper method to decode a Base16- (i.e., hexadecimal-) encoded string into an
     * array of bytes.
     * 
     * @param value The string to decode.
     * @return The decoded array of bytes.
     */
    public static byte[] decodeFromBase16(String value) {
        int len = value.length();
        int dlen = 0;
        byte[] tmp = new byte[len / 2];
        boolean high = true;
        int val;

        for ( int i = 0; i < len; i++ ) {
            char c = value.charAt(i);

            if ( c >= '0' && c <= '9' ) {
                val = (c - '0');
            } else if ( c >= 'A' && c <= 'F' ) {
                val = (c - 'A' + 10);
            } else if ( c >= 'a' && c <= 'f' ) {
                val = (c - 'a' + 10);
            } else {
                continue;
            }

            if ( high ) {
                tmp[dlen] = (byte) (val << 4);
                high = false;
            } else {
                tmp[dlen++] |= (byte) (val & 0x0F);
                high = true;
            }
        }

        return Arrays.copyOf(tmp, dlen);
    }

    /**
     * Helper method to encode an array of bytes into a Base16 (i.e., hexadecimal)
     * string.
     * 
     * @param value The array to encode.
     * @return The Base16 representation of the array.
     */
    public static String encodeToBase16(byte[] value) {
        StringBuilder sb = new StringBuilder();

        for ( int i = 0; i < value.length; i++ ) {
            byte hi, lo;

            hi = (byte) ((value[i] & 0xF0) >> 4);
            lo = (byte) (value[i] & 0x0F);

            sb.appendCodePoint(hi >= 10 ? hi - 10 + 'A' : hi + '0');
            sb.appendCodePoint(lo >= 10 ? lo - 10 + 'A' : lo + '0');
        }

        return sb.toString();
    }

    /**
     * Supported string encodings for binary data.
     */
    public enum BinaryEncoding {
        /**
         * Base64 encoding, as per
         * <a href="https://tools.ietf.org/html/rfc4648#section-4">RFC-4648 §4</a>.
         */
        BASE64,

        /**
         * Base16 (or hexadecimal) encoding, as per
         * <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-8">RFC-4648
         * §8</a>.
         */
        BASE16
    }
}
