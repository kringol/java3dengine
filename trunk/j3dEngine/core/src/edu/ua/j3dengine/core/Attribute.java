package edu.ua.j3dengine.core;

import edu.ua.j3dengine.utils.AssertionUtils;


public class Attribute<T> {
    private String name;
    private T value;


    public Attribute(String name, T value) {
        AssertionUtils.assertNotEmpty(name);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return "[Attribute]: '"+name+"'";
    }


    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute that = (Attribute) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    public int hashCode() {
        return name.hashCode();
    }


    public String toString() {
        return name;
    }
}
