package edu.ua.j3dengine.core.geometry;

import javax.vecmath.Vector3f;
import javax.vecmath.Tuple3f;
import java.util.Collection;


public abstract class Geometry {

    private String name;
    private static final String DEFAULT_GEOMETRY_NAME = "Unnamed";

    public Geometry(String name) {
        this.name = name;
    }

    protected Geometry() {
        this(DEFAULT_GEOMETRY_NAME);
    }


    public String getName() {
        return name;
    }

    public abstract Tuple3f getLocation();

    
    
}
