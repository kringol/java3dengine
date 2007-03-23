package edu.ua.j3dengine.processors.input;

import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.processors.execution.GameEnvironment;
import net.jtank.input.MouseAccumulator;
import net.jtank.input.MouseDevice;
import net.jtank.input.awt.AWTMouse;

public class MouseManager {

    private static MouseManager ourInstance = new MouseManager();
    private MouseDevice mouseDevice;
    private MouseAccumulator mouse;
    protected static final long MIN_DELTA_UPDATE_TIME_MILLIS = 10L;
    private static long elapsedTime = 0;
    private int x, y;

    private MouseManager() {
        mouseDevice = new AWTMouse(GameEnvironment.getInstance().getComponent());
        mouse = new MouseAccumulator();
        mouseDevice.registerListener(mouse);

        //hides and centers the cursor
        setExclusive(true);
    }

    public static void init() {
        ourInstance = new MouseManager();
    }

    public static void update() {

        ourInstance.mouseDevice.update();

        elapsedTime += GameObjectManager.getInstance().getElapsedTime();

        if (elapsedTime > MIN_DELTA_UPDATE_TIME_MILLIS) {
            ourInstance.mouse.setXAccumulator(getX() - ourInstance.x);
            ourInstance.mouse.setYAccumulator(getY() - ourInstance.y);
            ourInstance.x = getX();
            ourInstance.y = getY();
            elapsedTime = 0;
        }
    }


    public static boolean isExclusiveMode() {
        return ourInstance.mouseDevice.isExclusive();
    }

    public static void setExclusiveMode(boolean exclusiveMode) {
        ourInstance.setExclusive(exclusiveMode);
    }

    public void setExclusive(boolean exclusiveMode) {
        if (mouseDevice.isExclusiveSupported()) {
            mouseDevice.setExclusive(exclusiveMode);
            mouseDevice.setCursorVisible(!exclusiveMode);
        }
    }

    public static boolean isButtonPressed(int key) {
        return ourInstance.mouse.isPressed(key);
    }

    public static int getXDelta() {
        return ourInstance.mouse.getXAccumulator();
    }

    public static int getYDelta() {
        return ourInstance.mouse.getYAccumulator();
    }

    public static int getX() {
        return ourInstance.mouse.getX();
    }

    public static int getY() {
        return ourInstance.mouse.getY();
    }

    public static boolean isAt(int x, int y) {
        return (getX() == x && getY() == y);
    }
}
