package org.test.xith3d.movement;

import org.xith3d.scenegraph.Group;
import org.xith3d.scenegraph.Transform3D;

import javax.vecmath.Vector3f;


public class VelocityMovementController extends MovementController {

    /**
     * Speed measured in pixels/sec.
     */
    private float speed;
    private Vector3f direction;
    


    private VelocityMovementController(Group group, String objectName) {
        super(group, objectName);
    }

    public static VelocityMovementController create(Group targetGroup, String objectName) {
        return new VelocityMovementController(targetGroup, objectName);
    }


    protected Transform3D updateTransform(Transform3D transform, long elapsedMillis) {

        if (direction != null && speed != 0) {
            Vector3f translationVector = (Vector3f) transform.getTranslation();

            double deltaSecTime = (elapsedMillis / 1000f);

            double delta = (speed * deltaSecTime);

            translationVector.x += direction.x * delta;
            translationVector.y += direction.y * delta;
            translationVector.z += direction.z * delta;

            transform.setTranslation(translationVector);
        }

        return transform;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector3f getDirection() {
        return direction;
    }

    /**
     * Set speed for controller.
     *
     * @param speed Speed measured in pixels/second.
     */
    public void setSpeed(float speed) {
        if (speed < 0){
            throw new IllegalArgumentException("Speed cannot be negative!");
        }
        this.speed = speed;
    }

    public void setDirection(Vector3f direction) {
        direction.normalize();
        this.direction = direction;
    }


}

