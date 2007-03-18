package edu.ua.j3dengine.core.movement;

import javax.vecmath.Tuple3f;


public interface CameraMovementController {

    void setLocation(Tuple3f location);

    Tuple3f getLocation();

}
