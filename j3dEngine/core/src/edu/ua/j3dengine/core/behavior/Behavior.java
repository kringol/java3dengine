package edu.ua.j3dengine.core.behavior;


public abstract class Behavior {

    private String name;

    public Behavior(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public abstract void execute();

}
