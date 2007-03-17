package edu.ua.j3dengine.core.movement;

import javax.vecmath.Vector3f;


public interface BasicMovementController {

    void setSpeed(Vector3f direction, float speed);

    Vector3f getCurrentDirection();

    void setSpeed(float speed);

    void setDirection(Vector3f direction);

    void setRotationSpeed(Vector3f rotationAxis, float rotationSpeed);

    Vector3f getCurrentRotationAxis();

    void setRotationAxis(Vector3f rotationAxis);

    void setRotationSpeed(float rotationSpeed);

}
