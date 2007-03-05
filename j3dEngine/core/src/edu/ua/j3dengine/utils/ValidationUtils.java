package edu.ua.j3dengine.utils;


public class ValidationUtils {

    public static void validateNotNull(Object object, String msg){
        if (object == null){
            throw new IllegalArgumentException(msg != null ? msg : "Argument cannot be null!");
        }

    }
    public static void validateNotNull(Object object){
        validateNotNull(object, null);
    }

    public static void validateNotEmpty(String string){
        if (string == null || string.trim().length() == 0){
            throw new IllegalArgumentException("String argument cannot be null or empty!");
        }
    }

    public static void validateSameObject(Object o1, Object o2, String msg){
        validateNotNull(o1);
        validateNotNull(o2);
        if (o1 != o2){
            throw new IllegalArgumentException(msg != null ? msg : "Objects must be the same!");
        }
    }

    public static void validateSameObject(Object o1, Object o2){
        validateSameObject(o1, o2, null);
    }

    public static void validateEquals(Object o1, Object o2){
        validateEquals(o1, o2, null);
    }
    public static void validateEquals(Object o1, Object o2, String msg){
        validateNotNull(o1);
        validateNotNull(o2);
        if (!(o1.equals(o2) && o2.equals(o1))){
            throw new IllegalArgumentException(msg != null ? msg : "Objects must be equal!");
        }
    }


    public static <T extends Comparable> void validateInsideRange(T value, T minValue, T maxValue){
        validateInsideRange(value, minValue, maxValue, null);
    }

    public static <T extends Comparable> void validateInsideRange(T value, T minValue, T maxValue, String msg){
        validateNotNull(value);
        validateNotNull(minValue);
        validateNotNull(maxValue);

        if (value.compareTo(minValue) < 0 || value.compareTo(maxValue) > 0){
            throw new IllegalArgumentException(msg != null ? msg : "Value must be inside range. Value: '"+value+"', MinValue: '"+minValue+"', MaxValue: '"+maxValue+"'.");
        }
    }

}
