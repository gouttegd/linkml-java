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

import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The context in which a schema definition is being converted.
 * <p>
 * A context is specific to an instance of {@link SchemaDocument} and allows to
 * keep track of the various schemas that are being converted as the imports
 * closure is processed.
 */
public class SchemaProcessingContext {

    // For now, this class mostly encapsulates a LIFO queue storing the IDs of the
    // schemas that are being processed.
    private Deque<URI> importChain = new ArrayDeque<>();

    /**
     * Pushes a new schema ID into the import queue.
     * 
     * @param schema The ID to add to the queue.
     */
    public void pushImport(URI schema) {
        importChain.addLast(schema);
    }

    /**
     * Removes the last schema ID.
     */
    public void popImport() {
        importChain.removeLast();
    }

    /**
     * Gets the ID of the schema that is currently being converted.
     * 
     * @return The current schema ID.
     */
    public URI getCurrentSchema() {
        return importChain.getLast();
    }

    /**
     * Checks whether the given URI (assumed to be a schema ID) is present in the
     * current import chain.
     * 
     * @param schema The schema ID to check.
     * @return <code>true</code> if the given ID represents a schema that is part of
     *         the current import chain, otherwise <code>false</code>.
     */
    public boolean isInImportChain(URI schema) {
        return importChain.contains(schema);
    }
}
