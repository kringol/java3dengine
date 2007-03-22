package edu.ua.j3dengine.processors;

import edu.ua.j3dengine.processors.input.KeyboardManager;
import edu.ua.j3dengine.processors.input.MouseManager;


public class InputProcessor extends Processor {

    public InputProcessor() {
        super("InputProcessor");
    }

    public void performConcreteInitialize() {
        KeyboardManager.init();
        MouseManager.init();
    }

    public void performConcreteExecute() {
        KeyboardManager.update();
        MouseManager.update();
        //System.out.println("Mouse coords: " + MouseManager.getX() + "," + MouseManager.getY());
        System.out.println("Mouse deltas: X= " + MouseManager.getXDelta() + " Y=" + MouseManager.getYDelta());
    }

    public String getType() {
        return TYPE;
    }

    public void performConcreteRelease() {
    }

    public static final String TYPE = "processor.input";
}
