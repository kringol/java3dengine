package edu.ua.j3dengine.core.movement.impl;

import javax.vecmath.Vector3f;
import javax.vecmath.Tuple3f;


public class VelocityMovementDelegate {

    /**
     * Speed measured in pixels/sec.
     */
    private float speed;
    private Vector3f direction;


    public Tuple3f calculateTranslationVector(long elapsedTime) {
        Vector3f translationVector = new Vector3f(0,0,0);

        if (direction != null && speed != 0) {
            translationVector = direction;

            double deltaSecTime = (elapsedTime / 1000f);

            double delta = (speed * deltaSecTime);

            translationVector.x *= delta;
            translationVector.y *= delta;
            translationVector.z *= delta;
        }
        return translationVector;
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
        if (speed < 0) {
            throw new IllegalArgumentException("Speed cannot be negative!");
        }
        this.speed = speed;
    }

    public void setDirection(Vector3f direction) {
        direction.normalize();
        this.direction = direction;
    }
}
