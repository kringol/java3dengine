package edu.ua.j3dengine.core;

import static edu.ua.j3dengine.utils.Utils.*;

import edu.ua.j3dengine.core.behavior.Behavior;
import edu.ua.j3dengine.core.geometry.Geometry;
import edu.ua.j3dengine.core.geometry.SpatialObject;
import edu.ua.j3dengine.core.state.DynamicObjectState;
import edu.ua.j3dengine.core.movement.MovementController;
import edu.ua.j3dengine.core.movement.impl.DefaultMovementController;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement
public class DynamicGameObject extends GameObject<DynamicObjectState> implements SpatialObject {

    @XmlElement
    private Geometry geometry;

    private MovementController movementController;

    public DynamicGameObject(String name) {
        super(name);
    }


    private DynamicGameObject() {
        this("Unnamed");
        this.movementController = new DefaultMovementController(this);
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

        getMovementController().update();
    }


    public MovementController getMovementController() {
        return movementController;
    }

    public Behavior getBehavior(){
        return getCurrentState().getNormalBehavior();
    }


    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        if (geometry != null){
            throw new IllegalStateException("Geometry has already been set.");
        }
        this.geometry = geometry;

        //todo (pablius) esto está muy bindeado a la implementación! --> armar un factory de dynamic objects para xith3d 
        if (!this.movementController.isInitialized()){
            this.movementController.initialize();
        }
    }

    public void initializeMovementController(){
        if (!this.movementController.isInitialized()){
            this.movementController.initialize();
        }else{
            logDebug("MovementController had already been initialized.");
        }
    }


    public GameObjectType getGameObjectType() {
        return GameObjectType.DYNAMIC;
    }


    @Override
    public Collection<DynamicObjectState> getAllStates() {
        return super.getAllStates();    
    }

}
