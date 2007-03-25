package edu.ua.j3dengine.core.movement.impl;

import edu.ua.j3dengine.core.DynamicGameObject;
import org.xith3d.scenegraph.Transform3D;
import org.xith3d.scenegraph.Transform;

import javax.vecmath.Vector3f;
import javax.vecmath.Tuple3f;


public class VelocityMovementControllerXithImpl extends BaseMovementControllerXithImpl {

    private VelocityMovementDelegate velocityDelegate;


    public VelocityMovementControllerXithImpl(DynamicGameObject targetObject) {
        super(targetObject);
        velocityDelegate = new VelocityMovementDelegate();
    }


    public void initialize() {
        //do nothing
    }

    public boolean isInitialized() {
        return true;
    }

    protected final Transform3D updateTransform(Transform3D transform, long elapsedMillis) {

        if (getDirection() != null && getSpeed() > 0) {
            /*Vector3f translationVector = (Vector3f) transform.getTranslation();

            double deltaSecTime = (elapsedMillis / 1000f);

            double delta = (speed * deltaSecTime);

            translationVector.x += direction.x * delta;
            translationVector.y += direction.y * delta;
            translationVector.z += direction.z * delta;*/
            Tuple3f translationVector = velocityDelegate.calculateTranslationVector(elapsedMillis);
            transform.getTranslation().add(translationVector);

            //transform.setTranslation(translationVector);
        }

        return transform;
    }

    public float getSpeed() {
        return velocityDelegate.getSpeed();
    }

    public Vector3f getDirection() {
        return velocityDelegate.getDirection();
    }

    //todo (pablius) method to be tested
    public Vector3f getLocation(){
        return (Vector3f)getTargetGeometry().getLocation();
    }

    //todo (pablius) method to be tested
    public void setLocation(Tuple3f newLocation){
        getTargetTransformGroup().setTransform(new Transform3D(newLocation));
    }


    /**
     * Set speed for controller.
     *
     * @param speed Speed measured in pixels/second.
     */
    public void setSpeed(float speed) {
        velocityDelegate.setSpeed(speed);
    }

    public void setDirection(Vector3f direction) {
        velocityDelegate.setDirection(direction);
    }
}
