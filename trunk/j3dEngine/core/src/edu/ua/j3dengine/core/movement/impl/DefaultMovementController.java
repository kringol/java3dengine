package edu.ua.j3dengine.core.movement.impl;

import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.movement.MovementController;
import edu.ua.j3dengine.core.movement.BasicMovementController;

import javax.vecmath.Vector3f;


public class DefaultMovementController extends MovementController implements BasicMovementController {

    private VelocityMovementController velocityController;
    private RotationMovementController rotationController;


    public DefaultMovementController(DynamicGameObject targetObject) {
        super(targetObject);
    }


    protected void performConcreteUpdate(long elapsedTime) {
        getVelocityController().update();
        getRotationController().update();
    }

    public void initialize(){
        velocityController = new VelocityMovementController(getTargetObject());
        rotationController = new RotationMovementController(getTargetObject());
    }


    public boolean isInitialized() {
        return velocityController != null && rotationController != null;
    }


    public VelocityMovementController getVelocityController() {
         if (!isInitialized()){
            throw new IllegalStateException("DefaultMovementController has not been initialized.");
        }
        return velocityController;
    }

    public RotationMovementController getRotationController() {
         if (!isInitialized()){
            throw new IllegalStateException("DefaultMovementController has not been initialized.");
        }
        return rotationController;
    }

    public void setSpeed(Vector3f direction, float speed) {
        getVelocityController().setDirection(direction);
        getVelocityController().setSpeed(speed);
    }

    public Vector3f getCurrentDirection(){
        return getVelocityController().getDirection();
    }

    public void setSpeed(float speed){
        getVelocityController().setSpeed(speed);
    }


    public void setDirection(Vector3f direction) {
        getVelocityController().setDirection(direction);
    }

    public void setRotationAxis(Vector3f rotationAxis) {
        getRotationController().setRotationAxis(rotationAxis);
    }

    public void setRotationSpeed(Vector3f rotationAxis, float rotationSpeed) {
        getRotationController().setRotationAxis(rotationAxis);
        getRotationController().setRotationSpeed(rotationSpeed);
    }

    public Vector3f getCurrentRotationAxis() {
        return getRotationController().getRotationAxis();
    }

    public void setRotationSpeed(float rotationSpeed){
        getRotationController().setRotationSpeed(rotationSpeed);
    }


}