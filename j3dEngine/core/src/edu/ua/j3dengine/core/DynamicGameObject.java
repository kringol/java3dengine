package edu.ua.j3dengine.core;

import static edu.ua.j3dengine.utils.Utils.*;
import static edu.ua.j3dengine.utils.ValidationUtils.*;

import edu.ua.j3dengine.core.behavior.Behavior;
import edu.ua.j3dengine.core.geometry.BaseGeometry;
import edu.ua.j3dengine.core.geometry.SpatialObject;
import edu.ua.j3dengine.core.geometry.Geometry;
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
        this.movementController = new DefaultMovementController(this);
    }


    private DynamicGameObject() {
        this("Unnamed");
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
        if (this.geometry != null){
            throw new IllegalStateException("Geometry has already been set.");
        }
        this.geometry = geometry;

    }

    protected void changeMovementController(MovementController controller){
        validateNotNull(controller);
        this.movementController = controller;
        initializeMovementController();
    }

    public void initializeMovementController(){
        if (getGeometry() == null){
            logDebug("["+this.toString()+"] MovementController initialization failed. Object does not have a Geometry, so it cannot be moved. .");
        }else{
            if (!this.movementController.isInitialized()){
                this.movementController.initialize();
            }else{
                logDebug("["+this.toString()+"] MovementController '"+this.movementController.getClass().getName()+"' had already been initialized.");
            }
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
