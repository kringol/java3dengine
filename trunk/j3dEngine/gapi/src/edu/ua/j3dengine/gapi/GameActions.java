package edu.ua.j3dengine.gapi;

import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.GameObject;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.movement.AnimatedMovementController;
import edu.ua.j3dengine.core.movement.BasicMovementController;
import edu.ua.j3dengine.processors.input.KeyboardManager;
import edu.ua.j3dengine.processors.input.MouseManager;

import javax.vecmath.Vector3f;


public class GameActions {


    private GameActions() {
    }

    public static void setSpeed(String objectName, float x, float y, float z, float speed) {
        setSpeed(objectName, new Vector3f(x,y,z), speed);
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

    public static void changeAnimation(String objectName, String animationName, boolean loopAnimation) {
        DynamicGameObject targetObject = getDynamicGameObject(objectName);
        ((AnimatedMovementController) targetObject.getMovementController()).changeAnimation(animationName, loopAnimation);
    }


    public static void startAnimation(String objectName) {
        DynamicGameObject targetObject = getDynamicGameObject(objectName);
        ((AnimatedMovementController) targetObject.getMovementController()).startAnimation();
    }

    public static void stopAnimation(String objectName) {
        DynamicGameObject targetObject = getDynamicGameObject(objectName);
        ((AnimatedMovementController) targetObject.getMovementController()).stopAnimation();
    }


    public static boolean isKeyPressed(int key) {
        return KeyboardManager.isKeyPressed(key);
    }


    private static DynamicGameObject getDynamicGameObject(String objectName) {
        GameObject object = GameObjectManager.getInstance().lookupGameObject(objectName);
        if (!object.isDynamic()) {
            throw new IllegalArgumentException("Object must be dynamic");
        }
        return (DynamicGameObject) object;
    }

    public void setMouseExclusiveMode(boolean isExclusive){
        MouseManager.setExclusiveMode(isExclusive);
    }

    public static void setViewLocation(float x, float y, float z){
        setViewLocation(new Vector3f(x,y,z));
    }
    public static void setViewLocation(Vector3f newLocation){
        ((BasicMovementController)GameObjectManager.getInstance().getDefaultCamera().getMovementController()).setLocation(newLocation);
    }

}
