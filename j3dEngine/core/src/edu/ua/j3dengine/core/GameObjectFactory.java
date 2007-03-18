package edu.ua.j3dengine.core;

import org.xith3d.scenegraph.View;
import edu.ua.j3dengine.core.state.DynamicObjectState;
import edu.ua.j3dengine.core.behavior.Behavior;
import edu.ua.j3dengine.core.behavior.InertBehavior;
import edu.ua.j3dengine.core.movement.MovementController;
import edu.ua.j3dengine.core.movement.CameraMovementController;
import edu.ua.j3dengine.core.movement.impl.CameraMovementControllerImpl;


public class GameObjectFactory {

    private static GameObjectFactory instance;


    private GameObjectFactory() {
    }

    public static synchronized GameObjectFactory getInstance(){
        if (instance == null){
            instance = new GameObjectFactory();
        }
        return instance;
    }

    public Camera createCamera(String cameraName, View view){
        Camera camera = new Camera(cameraName, view);
        Behavior behavior = new InertBehavior();
        DynamicObjectState normalCameraState = new DynamicObjectState("NormalCameraState", null, null, behavior);
        camera.addState(normalCameraState);
        camera.setInitialState("NormalCameraState");
        MovementController controller = new CameraMovementControllerImpl(camera);
        camera.changeMovementController(controller);

        return camera;
    }
}
