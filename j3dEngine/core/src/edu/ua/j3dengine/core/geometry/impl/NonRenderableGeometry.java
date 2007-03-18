package edu.ua.j3dengine.core.geometry.impl;

import edu.ua.j3dengine.core.geometry.BaseGeometry;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;


public class NonRenderableGeometry extends BaseGeometry {

    private Tuple3f location;

    public NonRenderableGeometry() {
        super("NonRenderableGeometry");
        this.location = new Vector3f(0,0,0);
    }

    public void setLocation(Tuple3f location){
        this.location = location;
    }

    public Tuple3f getLocation() {
        return location;
    }
}
