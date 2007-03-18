package edu.ua.j3dengine.core.geometry;

public abstract class BaseGeometry implements Geometry{

    private String name;
    private static final String DEFAULT_GEOMETRY_NAME = "Unnamed";

    public BaseGeometry(String name) {
        this.name = name;
    }

    protected BaseGeometry() {
        this(DEFAULT_GEOMETRY_NAME);
    }


    public String getName() {
        return name;
    }
 
}
