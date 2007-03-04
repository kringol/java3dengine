package edu.ua.j3dengine.core.state;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class StaticObjectState extends State {


    public StaticObjectState(String name) {
        super(name);
    }
    

    private StaticObjectState(){}

    public void activate() {
        //do nothing
    }

    public void deactivate() {
        //do nothing
    }
}
