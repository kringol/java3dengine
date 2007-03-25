package edu.ua.j3dengine.core;

import edu.ua.j3dengine.core.state.State;
import static edu.ua.j3dengine.utils.ValidationUtils.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.*;


public abstract class GameObject<S extends State> {

    @XmlElement
    private String name;

    //@XmlElement(name = "currentState")
    private S currentState;

    @XmlElement
    private String initialState;

    @XmlElementWrapper
    private Map<String, S> gameObjectStates;
    
    @XmlElementWrapper
    private Map<String, Attribute> attributes;

    private static final String DEFAULT_GAME_OBJECT_NAME = "Unnamed";
   
    protected GameObject(String name) {
        validateNotEmpty(name);
        this.gameObjectStates = new HashMap<String, S>();
        this.attributes = new HashMap<String, Attribute>();
        this.name = name;
    }

    protected GameObject() {
        this(DEFAULT_GAME_OBJECT_NAME);    
    }

    public abstract boolean isDynamic();



    public String getInitialState(){
        return initialState;
        //return getCurrentState() != null ? getCurrentState().getName() : null; //TODO (pablius) erase later
    }

    public void setInitialState(String stateName){
        validateNotEmpty(stateName);
        this.initialState = stateName;
//        S state = getState(stateName); //TODO (pablius) erase later
//        assert state != null;
//
//        setCurrentState(state);
    }

    public S getCurrentState() {
        if (currentState == null){
            validateNotEmpty(getInitialState());
            setCurrentState(getState(getInitialState()));
        }
        return currentState;
    }

    public void setCurrentState(S newState) {
        State storedState = gameObjectStates.get(newState.getName());
        validateNotNull(storedState, "State is invalid.");
        validateEquals(storedState, newState, "State is invalid.");
        //validateSameObject(storedState, newState, "State is invalid.");

        //if the state has changed, activate the new state
        if (currentState == null || currentState != newState){
            if (currentState != null){
                currentState.deactivate();
            }
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

    public S getState(String stateName){
        return gameObjectStates.get(stateName);
    }

    public void addState(S state){
        validateNotNull(state);
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
        validateNotNull(attribute);
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
        STATIC, DYNAMIC
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameObject that = (GameObject) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    public int hashCode() {
        return name.hashCode();
    }
}
