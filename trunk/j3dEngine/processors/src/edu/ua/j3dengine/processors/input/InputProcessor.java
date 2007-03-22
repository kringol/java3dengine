package edu.ua.j3dengine.processors.input;

import edu.ua.j3dengine.processors.Processor;


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
    }

    public String getId() {
        return getClass().getCanonicalName();
    }

    public void performConcreteRelease() {
    }

}
