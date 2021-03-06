/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
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


















package jdk.internal.vm.compiler.nativeimage.c.struct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jdk.internal.vm.compiler.word.WordBase;

/**
 * Denotes a method as a field address computation of a {@link RawStructure}.
 * <p>
 * Calls to the method are replaced with address arithmetic. The possible signatures are
 * {@code IntType addressOfFieldName([IntType index]);}
 * <p>
 * The return type must be a primitive integer type or a {@link WordBase word type}. The receiver is
 * the pointer to the {@link RawStructure} that is accessed.
 * <p>
 * The field offset, i.e., the value that is added to the receiver, is a compile time constant.
 * <p>
 * The optional parameter {@code index} denotes an index, i.e., the receiver is treated as an array
 * of the {@link RawStructure}. The type must be a primitive integer type or a {@link WordBase word
 * type}. Address arithmetic is used to scale the index with the size of the struct.
 *
 * @since 19.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RawFieldAddress {
}
