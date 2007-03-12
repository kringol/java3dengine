package edu.ua.j3dengine.core.behavior;

import static edu.ua.j3dengine.utils.Utils.logDebug;

public class InertBehavior extends Behavior {
    private static final String INERT_BEHAVIOR_NAME = "InertBehavior";


    public InertBehavior(String name) {
        super(name);
    }

    public InertBehavior() {
        super(INERT_BEHAVIOR_NAME);
    }



    public void execute() {
        //do nothing! (it's inert)
        logDebug("InertBehavior executed.");
    }
}
