package edu.ua.j3dengine.utils;


public class AssertionUtils {

    public static void assertNotNull(Object object, String msg){
        if (object == null){
            throw new IllegalArgumentException(msg != null ? msg : "Argument cannot be null!");
        }

    }
    public static void assertNotNull(Object object){
        assertNotNull(object, null);
    }

    public static void assertNotEmpty(String string){
        if (string == null || string.trim().length() == 0){
            throw new IllegalArgumentException("String argument cannot be null or empty!");
        }
    }

    public static void assertSameObject(Object o1, Object o2, String msg){
        assertNotNull(o1);
        assertNotNull(o2);
        if (o1 != o2){
            throw new IllegalArgumentException(msg != null ? msg : "Objects must be the same!");
        }
    }

    public static void assertSameObject(Object o1, Object o2){
        assertSameObject(o1, o2, null);
    }

    public static void assertEquals(Object o1, Object o2){
        assertEquals(o1, o2, null);
    }
    public static void assertEquals(Object o1, Object o2, String msg){
        assertNotNull(o1);
        assertNotNull(o2);
        if (!(o1.equals(o2) && o2.equals(o1))){
            throw new IllegalArgumentException(msg != null ? msg : "Objects must be equal!");
        }
    }

}
