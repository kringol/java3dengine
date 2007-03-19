package edu.ua.j3dengine.processors;

import static edu.ua.j3dengine.utils.Utils.*;

import edu.ua.j3dengine.processors.input.KeyboardManager;
import edu.ua.j3dengine.processors.input.MouseManager;


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
        logDebug("Executing Input Processor");
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
