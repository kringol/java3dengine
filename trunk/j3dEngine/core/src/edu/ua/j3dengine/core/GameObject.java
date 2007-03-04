package edu.ua.j3dengine.core;

import edu.ua.j3dengine.core.state.State;
import static edu.ua.j3dengine.utils.AssertionUtils.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.*;


public abstract class GameObject<S extends State> {

    @XmlElement
    private String name;

    //@XmlElement(name = "currentState")
    private S currentState;

    @XmlElementWrapper
    private Map<String, S> gameObjectStates;
    
    @XmlElementWrapper
    private Map<String, Attribute> attributes;

    private static final String DEFAULT_GAME_OBJECT_NAME = "Unnamed";
   
    protected GameObject(String name) {
        assertNotEmpty(name);
        this.gameObjectStates = new HashMap<String, S>();
        this.attributes = new HashMap<String, Attribute>();
        this.name = name;
    }

    protected GameObject() {
        this(DEFAULT_GAME_OBJECT_NAME);    
    }

    public abstract boolean isDynamic();

    public S getCurrentState() {
        return currentState;
    }

    public void setCurrentState(S newState) {
        State storedState = gameObjectStates.get(newState.getName());
        assertNotNull(storedState, "State is invalid.");
        assertEquals(storedState, newState, "State is invalid.");
        //assertSameObject(storedState, newState, "State is invalid.");

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

    public Collection<S> getAllStates(){
        return Collections.unmodifiableCollection(gameObjectStates.values());
    }

    public State getState(String stateName){
        return gameObjectStates.get(stateName);
    }

    public void addState(S state){
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

    public void addAttribute(Attribute attribute){
        assertNotNull(attribute);
        attributes.put(attribute.getName(), attribute);
    }

    public String getName() {
        return name;
    }

    public abstract GameObjectType getGameObjectType();

    public String toString() {
        return "[GameObject]: '"+name+"'";
    }

    public enum GameObjectType {
        STATIC, DYNAMIC;
    }
}
