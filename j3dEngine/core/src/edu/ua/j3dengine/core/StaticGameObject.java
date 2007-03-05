package edu.ua.j3dengine.core;

import edu.ua.j3dengine.core.geometry.Geometry;
import edu.ua.j3dengine.core.geometry.SpatialObject;
import edu.ua.j3dengine.core.state.StaticObjectState;

import javax.xml.bind.annotation.XmlElement;
import java.util.Collection;


public class StaticGameObject extends GameObject<StaticObjectState>  implements SpatialObject {

    @XmlElement
    private Geometry geometry;

    public StaticGameObject(String name) {
        super(name);
    }


    private StaticGameObject() {
    }

    public boolean isDynamic() {
        return false;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }


    public GameObjectType getGameObjectType() {
        return GameObjectType.STATIC;
    }


    @Override
    public Collection<StaticObjectState> getAllStates() {
        return super.getAllStates();    
    }
}
