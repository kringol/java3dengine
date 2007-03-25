package edu.ua.j3dengine.core.movement.impl;

import static edu.ua.j3dengine.utils.ValidationUtils.*;

import edu.ua.j3dengine.core.movement.CameraMovementController;
import edu.ua.j3dengine.core.movement.MovementController;
import edu.ua.j3dengine.core.Camera;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

//TODO (pablius) implementar!!!
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

    public void setDirection(Tuple3f direction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Tuple3f getDirection() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setSpeed(Vector3f direction, float speed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Vector3f getCurrentDirection() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setSpeed(float speed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDirection(Vector3f direction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRotationSpeed(Vector3f rotationAxis, float rotationSpeed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Vector3f getCurrentRotationAxis() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRotationAxis(Vector3f rotationAxis) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRotationSpeed(float rotationSpeed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public void rotate(float rotationAngle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void rotate(Vector3f rotationAxis, float rotationAngle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
