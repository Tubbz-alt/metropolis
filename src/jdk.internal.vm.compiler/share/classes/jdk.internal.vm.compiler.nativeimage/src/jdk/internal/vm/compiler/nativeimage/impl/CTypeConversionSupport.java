/*
 * Copyright (c) 2017, 2018, Oracle and/or its affiliates. All rights reserved.
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


















package jdk.internal.vm.compiler.nativeimage.impl;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import jdk.internal.vm.compiler.nativeimage.c.type.CCharPointer;
import jdk.internal.vm.compiler.nativeimage.c.type.CTypeConversion.CCharPointerHolder;
import jdk.internal.vm.compiler.word.PointerBase;
import jdk.internal.vm.compiler.word.UnsignedWord;

public interface CTypeConversionSupport {

    UnsignedWord toCString(CharSequence javaString, Charset charset, CCharPointer buffer, UnsignedWord bufferSize);

    UnsignedWord toCString(CharSequence javaString, CCharPointer buffer, UnsignedWord bufferSize);

    CCharPointerHolder toCString(CharSequence javaString);

    String toJavaString(CCharPointer cString);

    String toJavaString(CCharPointer cString, UnsignedWord length);

    String toJavaString(CCharPointer cString, UnsignedWord length, Charset charset);

    ByteBuffer asByteBuffer(PointerBase address, int size);
}
