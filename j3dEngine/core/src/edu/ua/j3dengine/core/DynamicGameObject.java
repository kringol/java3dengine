package edu.ua.j3dengine.core;

import edu.ua.j3dengine.core.behavior.Behavior;
import edu.ua.j3dengine.core.geometry.Geometry;
import edu.ua.j3dengine.core.geometry.SpatialObject;
import edu.ua.j3dengine.core.state.DynamicObjectState;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement
public class DynamicGameObject extends GameObject<DynamicObjectState> implements SpatialObject {

    @XmlElement
    private Geometry geometry;

    public DynamicGameObject(String name) {
        super(name);
    }


    private DynamicGameObject() {
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public void update(){
        if (getCurrentState() == null){
            throw new IllegalStateException("DynamicGameObject has not been correctly initialized. No initial state has been defined.");
        }
        assert getBehavior() != null;
        
        getBehavior().execute();
    }

    public Behavior getBehavior(){
        return getCurrentState().getNormalBehavior();
    }


    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }


    public GameObjectType getGameObjectType() {
        return GameObjectType.DYNAMIC;
    }


    @Override
    public Collection<DynamicObjectState> getAllStates() {
        return super.getAllStates();    
    }
}
