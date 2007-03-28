package edu.ua.j3dengine.core.movement.impl;

import static edu.ua.j3dengine.utils.ValidationUtils.*;

import edu.ua.j3dengine.core.movement.CameraMovementController;
import edu.ua.j3dengine.core.movement.MovementController;
import edu.ua.j3dengine.core.movement.Rotation;
import edu.ua.j3dengine.core.Camera;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import org.xith3d.scenegraph.View;

//TODO (pablius) implementar!!!
public class CameraMovementControllerImpl extends MovementController implements CameraMovementController {

    private static final Vector3f VEC_UP = new Vector3f(0,1,0);

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

    public void setDirection(Tuple3f direction) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public Tuple3f getDirection() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void setSpeed(Vector3f direction, float speed) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public Vector3f getCurrentDirection() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void setSpeed(float speed) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void setDirection(Vector3f direction) {
        View view = getCamera().getGeometry().getView();
        view.lookAt(view.getPosition(), direction, VEC_UP);
    }

    public void setRotationSpeed(Vector3f rotationAxis, float rotationSpeed) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public Vector3f getCurrentRotationAxis() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void setRotationAxis(Vector3f rotationAxis) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void setRotationSpeed(float rotationSpeed) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }


    public void rotate(Rotation axis, float rotationAngle) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
