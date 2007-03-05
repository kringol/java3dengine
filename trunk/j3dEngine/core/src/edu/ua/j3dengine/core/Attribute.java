package edu.ua.j3dengine.core;

import edu.ua.j3dengine.utils.ValidationUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Attribute<T> {

    @XmlElement
    private String name;

    @XmlElement
    private T value;


    public Attribute(String name, T value) {
        ValidationUtils.validateNotEmpty(name);
        this.name = name;
        this.value = value;
    }

    private Attribute(){
    }


    public String getName() {
        return name;
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
        return "[Attribute]: '"+name+"'";
    }
}
