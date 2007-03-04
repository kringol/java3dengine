package edu.ua.j3dengine.core.state;

import static edu.ua.j3dengine.utils.ValidationUtils.validateNotEmpty;
import static edu.ua.j3dengine.utils.ValidationUtils.validateNotNull;
import edu.ua.j3dengine.core.Attribute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.*;

@XmlRootElement
public abstract class State {

    @XmlElement
    private String name;

    @XmlElementWrapper
    private Map<String, Attribute> attributes;

    private static final String DEFAULT_STATE_NAME = "State:Unnamed";
    
    protected State(String name) {
        validateNotEmpty(name);
        this.name = name;
        this.attributes = new HashMap<String, Attribute>();
    }

    protected State() {
        this(DEFAULT_STATE_NAME);
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

    public void addAttribute(Attribute attribute){
        validateNotNull(attribute);
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
