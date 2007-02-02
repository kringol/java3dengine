package org.test.xith3d;

public class LibPathDetector {
    private static final String JAVA_LIBRARY_PATH = "java.library.path";

    public static void main(String[] args) {
        String libPath = "../lib;"+System.getProperties().get(JAVA_LIBRARY_PATH);
        System.getProperties().put(JAVA_LIBRARY_PATH, libPath);
        System.out.println("System.getProperties().get(\"java.library.path\") = " + System.getProperties().get(JAVA_LIBRARY_PATH));
        System.out.println("System.getProperties().get(\"java.ext.dirs\") = " + System.getProperties().get("java.ext.dirs"));

    }
}
