/*
 * Copyright (c) 2013, 2019, Oracle and/or its affiliates. All rights reserved.
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


















package jdk.internal.vm.compiler.nativeimage.c.type;

import jdk.internal.vm.compiler.nativeimage.c.struct.CPointerTo;
import jdk.internal.vm.compiler.word.PointerBase;
import jdk.internal.vm.compiler.word.SignedWord;

/**
 * A pointer to a pointer to a 8-bit C primitive value.
 *
 * @since 19.0
 */
@CPointerTo(CCharPointer.class)
public interface CCharPointerPointer extends PointerBase {

    /**
     * Reads the value at the pointer address.
     *
     * @since 19.0
     */
    CCharPointer read();

    /**
     * Reads the value of the array element with the specified index, treating the pointer as an
     * array of the C type.
     *
     * @since 19.0
     */
    CCharPointer read(int index);

    /**
     * Reads the value of the array element with the specified index, treating the pointer as an
     * array of the C type.
     *
     * @since 19.0
     */
    CCharPointer read(SignedWord index);

    /**
     * Writes the value at the pointer address.
     *
     * @since 19.0
     */
    void write(CCharPointer value);

    /**
     * Writes the value of the array element with the specified index, treating the pointer as an
     * array of the C type.
     *
     * @since 19.0
     */
    void write(int index, CCharPointer value);

    /**
     * Writes the value of the array element with the specified index, treating the pointer as an
     * array of the C type.
     *
     * @since 19.0
     */
    void write(SignedWord index, CCharPointer value);

    /**
     * Computes the address of the array element with the specified index, treating the pointer as
     * an array of the C type.
     *
     * @since 19.0
     */
    CCharPointerPointer addressOf(int index);

    /**
     * Computes the address of the array element with the specified index, treating the pointer as
     * an array of the C type.
     *
     * @since 19.0
     */
    CCharPointerPointer addressOf(SignedWord index);
}
