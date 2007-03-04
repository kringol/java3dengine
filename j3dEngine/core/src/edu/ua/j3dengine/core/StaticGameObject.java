package edu.ua.j3dengine.core;

import edu.ua.j3dengine.core.state.StaticObjectState;
import edu.ua.j3dengine.core.geometry.SpatialObject;
import edu.ua.j3dengine.core.geometry.Geometry;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



public class StaticGameObject extends GameObject<StaticObjectState>  implements SpatialObject {

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
}
