package edu.ua.j3dengine.core.behavior;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;


@XmlRootElement
public abstract class Behavior {

    @XmlElement
    private String name;

    private static final String DEFAULT_BEHAVIOR_NAME = "Behavior:Unnamed";

    public Behavior(String name) {
        this.name = name;
    }

    protected Behavior() {
        this(DEFAULT_BEHAVIOR_NAME);
    }


    public String getName() {
        return name;
    }

    public abstract void execute();

}
