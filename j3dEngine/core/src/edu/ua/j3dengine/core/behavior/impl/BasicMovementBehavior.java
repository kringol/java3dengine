package edu.ua.j3dengine.core.behavior.impl;

import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.behavior.Behavior;
import edu.ua.j3dengine.core.movement.CompositeMovementController;
import edu.ua.j3dengine.core.movement.impl.RotationMovementController;
import edu.ua.j3dengine.core.movement.impl.VelocityMovementController;
import static edu.ua.j3dengine.utils.ValidationUtils.validateNotNull;


public class BasicMovementBehavior extends Behavior {

    private DynamicGameObject targetObject;

    private CompositeMovementController controller;

    private boolean initialized;

    private BasicMovementBehavior(){
        super("BasicMovementBehavior");
    }

    public BasicMovementBehavior(DynamicGameObject targetObject){
        this();
        setTargetObject(targetObject);
    }

    public void initialize(){
        validateNotNull(getTargetObject());
        if (isInitialized()){
            throw new IllegalStateException(getName() + " can only be initialized once.");
        }
        controller = new CompositeMovementController(getTargetObject());
        VelocityMovementController vController = new VelocityMovementController(getTargetObject());
        RotationMovementController rController = new RotationMovementController(getTargetObject());
        controller.addControllers(vController, rController);

        initialized = true;
    }

    public DynamicGameObject getTargetObject() {
        return targetObject;
    }


    public void setTargetObject(DynamicGameObject targetObject) {
        if (this.targetObject != null){
            throw new IllegalStateException(getName() + " can only be initialized for one target dynamic object.");
        }else{
            this.targetObject = targetObject;
            initialize();
        }
    }


    public boolean isInitialized() {
        return initialized;
    }

    public void execute() {
        if (!initialized){
            initialize();
        }

        controller.update();   
    }
}
