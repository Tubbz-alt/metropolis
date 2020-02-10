/*
 * Copyright (c) 2018, 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */


















package jdk.internal.vm.compiler.nativeimage;

import java.util.Objects;

import jdk.internal.vm.compiler.nativeimage.impl.IsolateSupport;
import jdk.internal.vm.compiler.word.UnsignedWord;

/**
 * Support for the creation, access to, and tear-down of isolates.
 *
 * @since 19.0
 */
public final class Isolates {
    private Isolates() {
    }

    /**
     * An exception thrown in the context of managing isolates.
     *
     * @since 19.0
     */
    public static final class IsolateException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        /**
         * Constructs a new exception with the specified detail message.
         *
         * @since 19.0
         */
        public IsolateException(String message) {
            super(message);
        }
    }

    /**
     * Parameters for the creation of an isolate.
     *
     * @see CreateIsolateParameters.Builder
     *
     * @since 19.0
     */
    public static final class CreateIsolateParameters {

        /**
         * Builder for a {@link CreateIsolateParameters} instance.
         *
         * @since 19.0
         */
        public static final class Builder {
            private UnsignedWord reservedAddressSpaceSize;

            /**
             * Creates a new builder with default values.
             *
             * @since 19.0
             */
            public Builder() {
            }

            /**
             * Sets the size in bytes for the reserved virtual address space of the new isolate.
             *
             * @since 19.0
             */
            public Builder reservedAddressSpaceSize(UnsignedWord size) {
                this.reservedAddressSpaceSize = size;
                return this;
            }

            /**
             * Produces the final {@link CreateIsolateParameters} with the values set previously by
             * the builder methods.
             *
             * @since 19.0
             */
            public CreateIsolateParameters build() {
                return new CreateIsolateParameters(reservedAddressSpaceSize);
            }
        }

        private static final CreateIsolateParameters DEFAULT = new Builder().build();

        /**
         * Returns a {@link CreateIsolateParameters} with all default values.
         *
         * @since 19.0
         */
        public static CreateIsolateParameters getDefault() {
            return DEFAULT;
        }

        private final UnsignedWord reservedAddressSpaceSize;

        private CreateIsolateParameters(UnsignedWord reservedAddressSpaceSize) {
            this.reservedAddressSpaceSize = reservedAddressSpaceSize;
        }

        /**
         * Returns the size in bytes for the reserved virtual address space of the new isolate.
         * Returns a {@link CreateIsolateParameters} with all default values.
         *
         * @since 19.0
         */
        public UnsignedWord getReservedAddressSpaceSize() {
            return reservedAddressSpaceSize;
        }
    }

    /**
     * Creates a new isolate with the passed {@linkplain CreateIsolateParameters parameters}. On
     * success, the current thread is attached to the created isolate, and a pointer to its
     * associated {@link IsolateThread} structure is returned.
     *
     * @param parameters Parameters for the creation of the isolate.
     * @return A pointer to the structure that represents the current thread in the new isolate.
     * @throws IsolateException on error.
     *
     * @since 19.0
     */
    public static IsolateThread createIsolate(CreateIsolateParameters parameters) throws IsolateException {
        Objects.requireNonNull(parameters);
        return ImageSingletons.lookup(IsolateSupport.class).createIsolate(parameters);
    }

    /**
     * Attaches the current thread to the passed isolate. If the thread has already been attached,
     * the call provides the thread's existing isolate thread structure.
     *
     * @param isolate The isolate to which to attach the current thread.
     * @return A pointer to the structure representing the newly attached isolate thread.
     * @throws IsolateException on error.
     *
     * @since 19.0
     */
    public static IsolateThread attachCurrentThread(Isolate isolate) throws IsolateException {
        return ImageSingletons.lookup(IsolateSupport.class).attachCurrentThread(isolate);
    }

    /**
     * Given an isolate to which the current thread is attached, returns the address of the thread's
     * associated isolate thread structure. If the current thread is not attached to the passed
     * isolate, returns {@code null}.
     *
     * @param isolate The isolate for which to retrieve the current thread's corresponding structure
     * @return A pointer to the current thread's structure in the specified isolate or {@code null}
     *         if the thread is not attached to that isolate.
     * @throws IsolateException on error.
     *
     * @since 19.0
     */
    public static IsolateThread getCurrentThread(Isolate isolate) throws IsolateException {
        return ImageSingletons.lookup(IsolateSupport.class).getCurrentThread(isolate);
    }

    /**
     * Given an isolate thread structure, determines to which isolate it belongs and returns the
     * address of the isolate structure. May return {@code null} if the specified isolate thread
     * structure is no longer valid.
     *
     * @param thread The isolate thread for which to retrieve the isolate.
     * @return A pointer to the isolate, or {@code null}.
     * @throws IsolateException on error.
     *
     * @since 19.0
     */
    public static Isolate getIsolate(IsolateThread thread) throws IsolateException {
        return ImageSingletons.lookup(IsolateSupport.class).getIsolate(thread);
    }

    /**
     * Detaches the passed isolate thread from its isolate and discards any state or context that is
     * associated with it. At the time of the call, no code may still be executing in the isolate
     * thread's context. The passed pointer is no longer valid after the method returns.
     *
     * @param thread The isolate thread to detach from its isolate.
     * @throws IsolateException on error.
     *
     * @since 19.0
     */
    public static void detachThread(IsolateThread thread) throws IsolateException {
        ImageSingletons.lookup(IsolateSupport.class).detachThread(thread);
    }

    /**
     * Tears down an isolate. Given an {@link IsolateThread} for the current thread which must be
     * attached to the isolate to be torn down, waits for any other attached threads to detach from
     * the isolate, then discards the isolate's objects, threads, and any other state or context
     * that is associated with it. The passed pointer is no longer valid after the method returns.
     *
     * @param thread {@link IsolateThread} of the current thread that is attached to the isolate
     *            which is to be torn down.
     * @throws IsolateException on error.
     *
     * @since 19.0
     */
    public static void tearDownIsolate(IsolateThread thread) throws IsolateException {
        ImageSingletons.lookup(IsolateSupport.class).tearDownIsolate(thread);
    }
}
