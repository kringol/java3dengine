package edu.ua.j3dengine.core.movement.impl;

import static edu.ua.j3dengine.utils.ValidationUtils.*;

import edu.ua.j3dengine.core.movement.CameraMovementController;
import edu.ua.j3dengine.core.movement.MovementController;
import edu.ua.j3dengine.core.Camera;

import javax.vecmath.Tuple3f;

public class CameraMovementControllerImpl extends MovementController implements CameraMovementController {

    public CameraMovementControllerImpl(Camera targetCamera) {
        super(targetCamera);
        validateNotNull(targetCamera);

    }


    public Camera getCamera() {
        return (Camera)getTargetObject();
    }


    public void initialize() {
        //do nothing
    }

    public boolean isInitialized() {
        return true;
    }

    protected void performConcreteUpdate(long elapsedTime) {
        //do nothing
    }

    public void setLocation(Tuple3f location) {
        getCamera().getGeometry().getView().setPosition(location);
    }

    public Tuple3f getLocation() {
        return getCamera().getGeometry().getLocation();
    }
}
