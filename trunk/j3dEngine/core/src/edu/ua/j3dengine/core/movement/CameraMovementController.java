package edu.ua.j3dengine.core.movement;

import javax.vecmath.Tuple3f;


public interface CameraMovementController extends BasicMovementController{

    void setDirection(Tuple3f direction);

    Tuple3f getDirection();

}
