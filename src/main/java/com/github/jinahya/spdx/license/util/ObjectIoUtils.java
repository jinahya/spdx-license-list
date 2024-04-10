package com.github.jinahya.spdx.license.util;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class ObjectIoUtils {

    public static void write(final File file, final Object obj) throws IOException {
        try (var fos = new FileOutputStream(file);
             var gzipos = new GZIPOutputStream(fos);
             var oos = new ObjectOutputStream(gzipos)) {
            oos.writeObject(obj);
            oos.flush();
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T read(final File file) throws IOException, ClassNotFoundException {
        try (var fos = new FileInputStream(file);
             var gzipos = new GZIPInputStream(fos);
             var oos = new ObjectInputStream(gzipos)) {
            return (T) oos.readObject();
        }
    }

    private ObjectIoUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
