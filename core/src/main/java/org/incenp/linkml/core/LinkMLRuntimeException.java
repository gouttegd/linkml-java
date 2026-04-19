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

/**
 * Base class for all exceptions caused by an error within the LinkML runtime.
 * <p>
 * In many cases, methods within the LinkML-Java runtime will actually only
 * throw a more specific exception type (such as {@link LinkMLInternalError} or
 * {@link LinkMLValueError}), but will declare throwing this more abstract type
 * instead.
 * <p>
 * This is because the distinction between an “internal error” and a “value
 * error” is sometimes fuzzy, and (at least for now), this runtime does not
 * commit to enforcing a strict separation between the two.
 */
public class LinkMLRuntimeException extends Exception {

    private static final long serialVersionUID = 8282311125749458714L;

    /**
     * Creates a new instance.
     * 
     * @param msg A human-readable error message.
     */
    public LinkMLRuntimeException(String msg) {
        super(msg);
    }

    /**
     * Creates a new instance, for an error that has another exception as the
     * underlying cause.
     * 
     * @param msg   A human-readable error message.
     * @param inner The original exception that caused the present exception to be
     *              thrown.
     */
    public LinkMLRuntimeException(String msg, Throwable inner) {
        super(msg, inner);
    }
}
