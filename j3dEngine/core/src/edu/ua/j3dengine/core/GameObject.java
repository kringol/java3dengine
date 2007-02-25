package edu.ua.j3dengine.core;

import static edu.ua.j3dengine.utils.AssertionUtils.*;

import java.util.*;

public abstract class GameObject {
    private String name;
    private State currentState;
    private Map<String, State> gameObjectStates;


    protected GameObject(String name) {
        assertNotEmpty(name);
        this.gameObjectStates = new HashMap<String, State>();
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
