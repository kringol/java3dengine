package edu.ua.j3dengine.processors.input;

import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.processors.execution.GameEnvironment;
import net.jtank.input.MouseAccumulator;
import net.jtank.input.MouseDevice;
import net.jtank.input.awt.AWTMouse;

public class MouseManager {

    private static MouseManager ourInstance = new MouseManager(true);
    private MouseDevice mouseDevice;
    private MouseAccumulator mouse;
    protected static final long MIN_DELTA_UPDATE_TIME_MILLIS = 10L;
    protected static final long MIN_DELTA_UPDATE_WHEEL_TIME_MILLIS = 200L;
    private static long elapsedTime = 0;
    volatile private int dX, dY;
    volatile private int wheelDelta;
    private static long elapsedTimeWheel = 0;

    private MouseManager(boolean exclusiveMode) {
        mouseDevice = new AWTMouse(GameEnvironment.getInstance().getComponent());
        mouse = new MyMouseListener();
        mouseDevice.registerListener(mouse);

        //if true hides and centers the cursor
        setExclusive(exclusiveMode);
    }

    public static void init(boolean exclusive) {
        ourInstance = new MouseManager(exclusive);
    }

    public static void update() {

        ourInstance.mouseDevice.update();

        elapsedTime += GameObjectManager.getInstance().getElapsedTime();
        elapsedTimeWheel += GameObjectManager.getInstance().getElapsedTime();


        if (elapsedTime >= MIN_DELTA_UPDATE_TIME_MILLIS) {

            updateDeltaMoveX();
            updateDeltaMoveY();

            elapsedTime = 0;
        }

        if (elapsedTimeWheel >= MIN_DELTA_UPDATE_WHEEL_TIME_MILLIS) {
            ourInstance.wheelDelta = 0;
            elapsedTimeWheel = 0;
        }
    }

    private static void updateDeltaMoveY() {
        ourInstance.dY = ourInstance.mouse.getYAccumulator();
        ourInstance.mouse.clearYAccumulator();
    }

    private static void updateDeltaMoveX() {
        ourInstance.dX = ourInstance.mouse.getXAccumulator();
        ourInstance.mouse.clearXAccumulator();
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
        return ourInstance.dX;
    }

    public static int getYDelta() {
        return ourInstance.dY;
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

    public static int getWheelDelta() {
        int result = ourInstance.wheelDelta;
        //ourInstance.wheelDelta = 0;
        return result;
    }

    private class MyMouseListener extends MouseAccumulator {

        public void onMouseWheelMoved(int i, int i1, int i2) {
            wheelDelta += i;
        }
    }
}
