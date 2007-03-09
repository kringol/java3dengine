package edu.ua.j3dengine.core.movement;

import static edu.ua.j3dengine.utils.ValidationUtils.*;

import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.geometry.Geometry;


public abstract class MovementController {

    protected static final long MIN_ACTIVATION_TIME_MILLIS = 10L;
    private long elapsedTime = 0;

    private DynamicGameObject targetObject;


    protected MovementController(DynamicGameObject targetObject) {
        validateNotNull(targetObject);
        this.targetObject = targetObject;
    }


    public DynamicGameObject getTargetObject() {
        return targetObject;
    }

    public Geometry getTargetGeometry(){
        assert targetObject != null;
        return targetObject.getGeometry();
    }


    public final void update(){
        elapsedTime += GameObjectManager.getInstance().getElapsedTime();

        if (elapsedTime > MIN_ACTIVATION_TIME_MILLIS) {
            performConcreteUpdate(elapsedTime);
            elapsedTime = 0;
        }
    }

    public abstract void performConcreteUpdate(long elapsedTime);

}
