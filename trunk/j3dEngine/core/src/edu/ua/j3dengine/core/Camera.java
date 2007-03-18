package edu.ua.j3dengine.core;

import edu.ua.j3dengine.core.geometry.impl.CameraGeometry;
import org.xith3d.scenegraph.View;


public class Camera extends DynamicGameObject {

    private static final String DEFAULT_CAMERA_NAME = "DEFAULT_CAMERA";

    public Camera(String name, View view) {
        super(name != null ? name : DEFAULT_CAMERA_NAME);
        this.setGeometry(new CameraGeometry(view));
    }

    @Override
    public CameraGeometry getGeometry() {
        return (CameraGeometry)super.getGeometry();
    }
}
