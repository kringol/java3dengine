package edu.ua.j3dengine.core.geometry;


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

    
}
