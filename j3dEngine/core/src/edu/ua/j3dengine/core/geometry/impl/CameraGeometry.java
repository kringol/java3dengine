package edu.ua.j3dengine.core.geometry.impl;

import edu.ua.j3dengine.core.geometry.Geometry;

import javax.vecmath.Tuple3f;

import org.xith3d.scenegraph.View;

public class CameraGeometry implements Geometry {

    private View view;


    public CameraGeometry(View view) {
        this.view = view;
    }

    public Tuple3f getLocation() {
        return view.getPosition();
    }


    public View getView() {
        return view;
    }
}
