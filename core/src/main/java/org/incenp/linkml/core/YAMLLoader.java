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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Helper class to load or dump LinkML data instances from or to YAML files.
 * <p>
 * For now this is merely a thin wrapper around both Jackson’s ObjectMapper and
 * our own {@link ConverterContext}.
 */
public class YAMLLoader {

    private ConverterContext ctx = new ConverterContext();
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    /**
     * Gets the LinkML context used by this loader.
     * <p>
     * Client code might require access to the context (1) to customise it before
     * loading anything (e.g. to add externally defined prefixes or set up custom
     * converters), or (2) after loading something, to get all the prefixes that may
     * have been found in instance data.
     * 
     * @return The underlying LinkML context.
     */
    public ConverterContext getContext() {
        return ctx;
    }

    /**
     * Loads an instance of the specified type from a YAML file.
     * 
     * @param <T>  The type of objects to load.
     * @param file The file to load the object from.
     * @param type The type of objects to load.
     * @return The object that was loaded.
     * @throws IOException            If any I/O error occurs while attempting to
     *                                read from the file.
     * @throws LinkMLRuntimeException If the contents of the file do not match what
     *                                is expected for an instance of the specified
     *                                type.
     */
    public <T> T loadObject(File file, Class<T> type) throws IOException, LinkMLRuntimeException {
        Object raw = mapper.readValue(file, Map.class);

        Object cooked = ctx.getConverter(type).convert(raw, ctx);
        ctx.finalizeAssignments();

        return type.cast(cooked);
    }

    /**
     * Loads a list of instances of the specified type from a YAML file.
     * 
     * @param <T>  The type of objects to load.
     * @param file The file to load the objects from.
     * @param type The type of objects to load.
     * @return The objects that were loaded.
     * @throws IOException            If any I/O error occurs when attempting to
     *                                read from the file.
     * @throws LinkMLRuntimeException If the contents of the file do not match what
     *                                is expected for instances of the specified
     *                                type.
     */
    public <T> List<T> loadObjects(File file, Class<T> type) throws IOException, LinkMLRuntimeException {
        List<?> raw = mapper.readValue(file, List.class);

        List<T> cooked = new ArrayList<>();
        for ( Object rawItem : raw ) {
            cooked.add(type.cast(ctx.getConverter(type).convert(rawItem, ctx)));
        }
        ctx.finalizeAssignments();

        return cooked;
    }

    /**
     * Dumps a LinkML object into a YAML file.
     * 
     * @param <T>    The type of the object to dump.
     * @param file   The file where to dump the object.
     * @param object The object to dump.
     * @throws IOException            If any I/O error occurs when attempting to
     *                                write to the file.
     * @throws LinkMLRuntimeException If any error occurs when serialising the
     *                                object to a raw YAML tree (this should not
     *                                happen if the object is a valid LinkML object
     *                                in the first place).
     */
    public <T> void dumpObject(File file, T object) throws IOException, LinkMLRuntimeException {
        Object raw = ctx.getConverter(object.getClass()).serialise(object, ctx);
        mapper.writeValue(file, raw);
    }

    /**
     * Dumps a list of LinkML objects into a YAML file.
     * 
     * @param <T>     The type of the objects to dump. Of note, each object may be
     *                of a different subtype of that type.
     * @param file    The file where to dump the object.
     * @param objects The objects to dump.
     * @throws IOException            If any I/O error occurs when attempting to
     *                                write to the file.
     * @throws LinkMLRuntimeException If any error occurs when serialising the
     *                                objects to a raw YAML tree (this should not
     *                                happen if the object is a valid LinkML object
     *                                in the first place).
     */
    public <T> void dumpObjects(File file, List<T> objects) throws IOException, LinkMLRuntimeException {
        List<Object> raw = new ArrayList<>();
        for ( T object : objects ) {
            raw.add(ctx.getConverter(object.getClass()).serialise(object, ctx));
        }
        mapper.writeValue(file, raw);
    }
}
