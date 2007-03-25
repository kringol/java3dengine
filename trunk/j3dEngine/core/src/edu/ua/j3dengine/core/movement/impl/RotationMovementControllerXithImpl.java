package edu.ua.j3dengine.core.movement.impl;

import org.xith3d.scenegraph.Group;
import org.xith3d.scenegraph.Transform3D;

import javax.vecmath.Vector3f;

import edu.ua.j3dengine.core.DynamicGameObject;


public class RotationMovementControllerXithImpl extends BaseMovementControllerXithImpl {


    private Vector3f rotationAxis;
    private float rotationAngle;
    private float rotationSpeed;


    public RotationMovementControllerXithImpl(DynamicGameObject targetObject) {
        super(targetObject);
    }


    public void initialize() {
        //do nothing
    }

    public boolean isInitialized() {
        return true;
    }
    

    
    public float getRotationSpeed() {
        return rotationSpeed;
    }


    protected final Transform3D updateTransform(Transform3D transform, long elapsedMillis) {
        if (rotationAxis != null && rotationSpeed != 0){
            float deltaDegrees = rotationSpeed * (elapsedMillis / 1000f);
            rotationAngle = (rotationAngle + deltaDegrees) % 360;
            transform.rotAxis(rotationAxis, (float) Math.toRadians(rotationAngle));
        }
        return transform;
    }


    /**
     * Sets the rotation speed, measured in degrees/secs.
     * @param rotationSpeed
     */
    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public Vector3f getRotationAxis() {
        return rotationAxis;
    }

    public void setRotationAxis(Vector3f rotationAxis) {
        this.rotationAxis = rotationAxis;
    }
}
