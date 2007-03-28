package edu.ua.j3dengine.processors.input;

import edu.ua.j3dengine.processors.Processor;
import static edu.ua.j3dengine.utils.Utils.logDebug;


public class InputProcessor extends Processor {

    public InputProcessor() {
        super("InputProcessor");
    }

    public void performConcreteInitialize() {
        KeyboardManager.init();
        MouseManager.init(true);
    }

    public void performConcreteExecute() {
        KeyboardManager.update();
        MouseManager.update();
        int x = MouseManager.getXDelta();
        int y = MouseManager.getYDelta();
        int w = MouseManager.getWheelDelta();
        if (x != 0 || y != 0) {
            logDebug("MouseDelta: " + x + "," + y);
        }
        if (w != 0) {
            logDebug("MouseWheelDelta: " + w);
        }
    }

    public String getId() {
        return getClass().getCanonicalName();
    }

    public void performConcreteRelease() {
    }

}
