package edu.ua.j3dengine.processors;

import edu.ua.j3dengine.core.input.KeyboardManager;
import edu.ua.j3dengine.core.input.MouseManager;


public class InputProcessor extends Processor {

    protected InputProcessor(String name) {
        super(name);
    }

    public void performConcreteInitialize() {
        KeyboardManager.init();
        MouseManager.init();
    }

    public void performConcreteExecute() {
        KeyboardManager.update();
        MouseManager.update();
    }

    public void performConcreteRelease() {
    }
}
