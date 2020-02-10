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

/**
 * Utility class to retrieve information about the context in which code gets executed. The provided
 * string constants are part of the API and guaranteed to remain unchanged in future versions. This
 * allows to use {@link System#getProperty(String)} directly with the string literals defined here
 * thus eliminating the need to depend on this class.
 *
 * @since 19.0
 */
public final class ImageInfo {

    private ImageInfo() {
    }

    /**
     * Holds the string that is the name of the system property providing information about the
     * context in which code is currently executing. If the property returns the string given by
     * {@link #PROPERTY_IMAGE_CODE_VALUE_BUILDTIME} the code is executing in the context of image
     * building (e.g. in a static initializer of a class that will be contained in the image). If
     * the property returns the string given by {@link #PROPERTY_IMAGE_CODE_VALUE_RUNTIME} the code
     * is executing at image runtime. Otherwise the property is not set.
     *
     * @since 19.0
     */
    public static final String PROPERTY_IMAGE_CODE_KEY = "jdk.internal.vm.compiler.nativeimage.imagecode";

    /**
     * Holds the string that will be returned by the system property for
     * {@link ImageInfo#PROPERTY_IMAGE_CODE_KEY} if code is executing in the context of image
     * building (e.g. in a static initializer of class that will be contained in the image).
     *
     * @since 19.0
     */
    public static final String PROPERTY_IMAGE_CODE_VALUE_BUILDTIME = "buildtime";

    /**
     * Holds the string that will be returned by the system property for
     * {@link ImageInfo#PROPERTY_IMAGE_CODE_KEY} if code is executing at image runtime.
     *
     * @since 19.0
     */
    public static final String PROPERTY_IMAGE_CODE_VALUE_RUNTIME = "runtime";

    /**
     * Name of the system property that holds if this image is built as a shared library or an
     * executable. If the property is {@link #PROPERTY_IMAGE_KIND_VALUE_EXECUTABLE} the image is
     * built as an executable. If the property is {@link #PROPERTY_IMAGE_KIND_VALUE_SHARED_LIBRARY}
     * the image is built as a shared library.
     *
     * @since 19.0
     */
    public static final String PROPERTY_IMAGE_KIND_KEY = "jdk.internal.vm.compiler.nativeimage.kind";

    /**
     * Holds the string that will be returned by the system property for
     * {@link ImageInfo#PROPERTY_IMAGE_KIND_KEY} if image is a shared library.
     *
     * @since 19.0
     */
    public static final String PROPERTY_IMAGE_KIND_VALUE_SHARED_LIBRARY = "shared";

    /**
     * Holds the string that will be returned by the system property for
     * {@link ImageInfo#PROPERTY_IMAGE_KIND_KEY} if image is an executable.
     *
     * @since 19.0
     */
    public static final String PROPERTY_IMAGE_KIND_VALUE_EXECUTABLE = "executable";

    /**
     * Returns true if (at the time of the call) code is executing in the context of image building
     * or during image runtime, else false. This method will be const-folded so that it can be used
     * to hide parts of an application that only work when running on the JVM. For example:
     * {@code if (!ImageInfo.inImageCode()) { ... JVM specific code here ... }}
     *
     * @since 19.0
     */
    public static boolean inImageCode() {
        return System.getProperty(PROPERTY_IMAGE_CODE_KEY) != null;
    }

    /**
     * Returns true if (at the time of the call) code is executing at image runtime. This method
     * will be const-folded. It can be used to hide parts of an application that only work when
     * running as native image.
     *
     * @since 19.0
     */
    public static boolean inImageRuntimeCode() {
        return PROPERTY_IMAGE_CODE_VALUE_RUNTIME.equals(System.getProperty(PROPERTY_IMAGE_CODE_KEY));
    }

    /**
     * Returns true if (at the time of the call) code is executing in the context of image building
     * (e.g. in a static initializer of class that will be contained in the image).
     *
     * @since 19.0
     */
    public static boolean inImageBuildtimeCode() {
        return PROPERTY_IMAGE_CODE_VALUE_BUILDTIME.equals(System.getProperty(PROPERTY_IMAGE_CODE_KEY));
    }

    /**
     * Returns true if the image is build as an executable.
     *
     * @since 19.0
     */
    public static boolean isExecutable() {
        ensureKindAvailable();
        return PROPERTY_IMAGE_KIND_VALUE_EXECUTABLE.equals(System.getProperty(PROPERTY_IMAGE_KIND_KEY));
    }

    /**
     * Returns true if the image is build as a shared library.
     *
     * @since 19.0
     */
    public static boolean isSharedLibrary() {
        ensureKindAvailable();
        return PROPERTY_IMAGE_KIND_VALUE_SHARED_LIBRARY.equals(System.getProperty(PROPERTY_IMAGE_KIND_KEY));
    }

    private static void ensureKindAvailable() {
        if (inImageCode() && System.getProperty(PROPERTY_IMAGE_KIND_KEY) == null) {
            throw new UnsupportedOperationException(
                            "The kind of image that is built (executable or shared library) is not available yet because the relevant command line option has not been parsed yet.");
        }
    }
}
