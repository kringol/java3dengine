package edu.ua.j3dengine.core;

import static edu.ua.j3dengine.utils.AssertionUtils.*;

import java.util.*;

public abstract class GameObject {
    private String name;
    private State currentState;
    private Map<String, State> gameObjectStates;
    private Map<String, Attribute> attributes;


    protected GameObject(String name) {
        assertNotEmpty(name);
        this.gameObjectStates = new HashMap<String, State>();
        this.attributes = new HashMap<String, Attribute>();
        this.name = name;
    }


    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State newState) {
        State storedState = gameObjectStates.get(newState.getName());
        assertNotNull(storedState, "State is invalid.");
        assertSameObject(storedState, newState, "State is invalid.");

        //if the state has changed, activate the new state
        if (currentState != newState){
            currentState.deactivate();
            newState.activate();
            currentState = newState;
        }
    }

    public Set<String> getAllStateNames(){
        return Collections.unmodifiableSet(gameObjectStates.keySet());
    }

    public Collection<State> getAllStates(){
        return Collections.unmodifiableCollection(gameObjectStates.values());
    }

    public State getState(String stateName){
        return gameObjectStates.get(stateName);
    }

    public void putState(State state){
        assertNotNull(state);
        gameObjectStates.put(state.getName(), state);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String toString() {
        return "[GameObject]: '"+name+"'";
    }
}
