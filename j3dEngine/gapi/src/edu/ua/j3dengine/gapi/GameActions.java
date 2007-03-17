package edu.ua.j3dengine.gapi;

import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.GameObject;
import edu.ua.j3dengine.core.input.KeyboardManager;
import edu.ua.j3dengine.core.movement.BasicMovementController;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;

import javax.vecmath.Vector3f;


public class GameActions {


    private GameActions() {
    }

    public static void setSpeed(String objectName, Vector3f direction, float speed) {
        DynamicGameObject targetObject = getDynamicGameObject(objectName);
        ((BasicMovementController) targetObject.getMovementController()).setSpeed(direction, speed);
    }

    public static void setSpeed(String objectName, float speed) {
        DynamicGameObject targetObject = getDynamicGameObject(objectName);
        ((BasicMovementController) targetObject.getMovementController()).setSpeed(speed);
    }

    public static void setRotationSpeed(String objectName, Vector3f rotationAxis, float speed) {
        DynamicGameObject targetObject = getDynamicGameObject(objectName);
        ((BasicMovementController) targetObject.getMovementController()).setRotationSpeed(rotationAxis, speed);
    }

    public static void setRotationSpeed(String objectName, float speed) {
        DynamicGameObject targetObject = getDynamicGameObject(objectName);
        ((BasicMovementController) targetObject.getMovementController()).setRotationSpeed(speed);
    }


    public static boolean isKeyPressed(int key){
        return KeyboardManager.isKeyPressed(key);
    }

    

    

    private static DynamicGameObject getDynamicGameObject(String objectName) {
        GameObject object = GameObjectManager.getInstance().lookupGameObject(objectName);
        if (!object.isDynamic()) {
            throw new IllegalArgumentException("Object must be dynamic");
        }
        return (DynamicGameObject) object;
    }


}
