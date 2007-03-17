package edu.ua.j3dengine.core.movement;

import edu.ua.j3dengine.core.DynamicGameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class CompositeMovementController extends MovementController {

    private List<MovementController> controllers;
    private boolean initialized = false;

    public CompositeMovementController(DynamicGameObject targetObject) {
        super(targetObject);
        this.controllers = new ArrayList<MovementController>();
    }


    public void initialize() {
        for (MovementController controller : controllers) {
            controller.initialize();
        }
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    protected void performConcreteUpdate(long elapsedTime) {
        for (MovementController controller : controllers) {
            controller.performConcreteUpdate(elapsedTime);
        }
    }

    public void addController(MovementController controller){
        this.controllers.add(controller);
    }

    public void addControllers(MovementController... controllers){
        for (MovementController controller : controllers) {
            addController(controller);
        }
    }

    public List<MovementController> getControllers() {
        return Collections.unmodifiableList(controllers);
    }

}
