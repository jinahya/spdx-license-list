package com.github.jinahya.spdx.license.util;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class IoUtils {

    public static void write(final File file, final Object obj) throws IOException {
        try (var fos = new FileOutputStream(file);
             var gzos = new GZIPOutputStream(fos);
             var oos = new ObjectOutputStream(gzos)) {
            oos.writeObject(obj);
            oos.flush();
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T read(final File file) throws IOException, ClassNotFoundException {
        try (var fos = new FileInputStream(file);
             var gzos = new GZIPInputStream(fos);
             var oos = new ObjectInputStream(gzos)) {
            return (T) oos.readObject();
        }
    }

    private IoUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
