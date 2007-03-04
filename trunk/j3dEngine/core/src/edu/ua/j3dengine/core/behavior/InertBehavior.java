package edu.ua.j3dengine.core.behavior;

import static edu.ua.j3dengine.utils.Utils.*;

public class InertBehavior extends Behavior {


    public InertBehavior(String name) {
        super(name);
    }

    private InertBehavior(){}


    public void execute() {
        //do nothing! (it's inert)
        logDebug("InertBehavior executed.");
    }
}
