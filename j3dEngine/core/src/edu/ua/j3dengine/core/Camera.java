package edu.ua.j3dengine.core;


public class Camera extends DynamicGameObject {


    protected Camera(String name) {
        super("CAMERA" + (name != null ? ":" + name : ""));
    }

    
}
