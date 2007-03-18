package edu.ua.j3dengine.processors;

import edu.ua.j3dengine.core.input.KeyboardManager;
import edu.ua.j3dengine.core.input.MouseManager;


public class InputProcessor extends Processor {

    private static InputProcessor instance;

    private InputProcessor() {
        super("InputProcessor");
    }

    public synchronized static InputProcessor getInstance() {
        if (instance == null) {
            instance = new InputProcessor();
        }
        return instance;
    }

    public void performConcreteInitialize() {
        KeyboardManager.init();
        MouseManager.init();
    }

    public void performConcreteExecute() {
        KeyboardManager.update();
        MouseManager.update();
    }

    public String getType() {
        return TYPE;
    }

    public void performConcreteRelease() {
    }

    public static final String TYPE = "processor.input";
}
