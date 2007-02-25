package edu.ua.j3dengine.core;

import static edu.ua.j3dengine.utils.AssertionUtils.assertNotEmpty;
import static edu.ua.j3dengine.utils.AssertionUtils.assertNotNull;

import java.util.*;

public abstract class State {

    private String name;
    private Map<String, StateAttribute> attributes;

    protected State(String name) {
        assertNotEmpty(name);
        this.name = name;
        this.attributes = new HashMap<String, StateAttribute>();
    }

    public String getName() {
        return name;
    }

    public Set<String> getAllAttributeNames(){
        return Collections.unmodifiableSet(attributes.keySet());
    }

    public Collection<StateAttribute> getAllAttributes(){
        return Collections.unmodifiableCollection(attributes.values());
    }

    public StateAttribute getAttribute(String attributeName){
        return attributes.get(attributeName);
    }

    public void putAttribute(StateAttribute attribute){
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

    public static class StateAttribute<T> {
        private String name;
        private T value;


        public StateAttribute(String name, T value) {
            assertNotEmpty(name);
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return "[StateAttribute]: '"+name+"'";
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

            StateAttribute that = (StateAttribute) o;

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
}
