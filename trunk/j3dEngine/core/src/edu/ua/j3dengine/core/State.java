package edu.ua.j3dengine.core;

import static edu.ua.j3dengine.utils.AssertionUtils.assertNotEmpty;
import static edu.ua.j3dengine.utils.AssertionUtils.assertNotNull;

import java.util.*;

public abstract class State {

    private String name;
    private Map<String, Attribute> attributes;

    protected State(String name) {
        assertNotEmpty(name);
        this.name = name;
        this.attributes = new HashMap<String, Attribute>();
    }

    public String getName() {
        return name;
    }

    public Set<String> getAllAttributeNames(){
        return Collections.unmodifiableSet(attributes.keySet());
    }

    public Collection<Attribute> getAllAttributes(){
        return Collections.unmodifiableCollection(attributes.values());
    }

    public Attribute getAttribute(String attributeName){
        return attributes.get(attributeName);
    }

    public void putAttribute(Attribute attribute){
        assertNotNull(attribute);
        attributes.put(attribute.getName(), attribute);
    }


    public String toString() {
        return "[State]: '"+name+"'";
    }

    public abstract void activate();

    public abstract void deactivate();


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (!name.equals(state.name)) return false;

        return true;
    }

    public int hashCode() {
        return name.hashCode();
    }

}
