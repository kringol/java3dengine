package edu.ua.j3dengine.processors.input;

import static edu.ua.j3dengine.utils.Utils.*;

import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.processors.execution.GameEnvironment;
import net.jtank.input.MouseAccumulator;
import net.jtank.input.MouseDevice;
import net.jtank.input.MouseAdapter;
import net.jtank.input.awt.AWTMouse;

public class MouseManager {

    private static MouseManager ourInstance;
    private MouseDevice mouseDevice;
    private MouseAccumulator mouse;
    protected static final long MIN_DELTA_UPDATE_TIME_MILLIS = 10L;
    private static long elapsedTime = 0;
    private int x, y;

    private MouseManager(boolean exclusiveMode) {
        mouseDevice = new AWTMouse(GameEnvironment.getInstance().getComponent());
        mouse = new MouseAccumulator();
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

        if (elapsedTime > MIN_DELTA_UPDATE_TIME_MILLIS) {
            if (!isExclusiveMode()){
                int pX = getX();
                int pY = getY();
                ourInstance.mouse.setXAccumulator(pX - ourInstance.x);
                ourInstance.mouse.setYAccumulator(pY - ourInstance.y);
                ourInstance.x = pX;
                ourInstance.y = pY;
            }else{
                ourInstance.mouse.clearXAccumulator();
                ourInstance.mouse.clearYAccumulator();
            }
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
