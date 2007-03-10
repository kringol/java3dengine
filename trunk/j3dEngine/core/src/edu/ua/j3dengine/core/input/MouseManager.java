package edu.ua.j3dengine.core.input;

import net.jtank.input.MouseAccumulator;
import net.jtank.input.MouseDevice;
import net.jtank.input.awt.AWTMouse;

import java.awt.*;

public class MouseManager {

    private static MouseManager ourInstance = new MouseManager();
    private MouseDevice mouseDevice;
    private MouseAccumulator mouse;

    private MouseManager() {
        mouseDevice = new AWTMouse(new Frame()); //todo DUMMY FRAME, FIX!!

        //todo what should we do with this?
//        if (mouseDevice.isExclusiveSupported()) {
//            mouseDevice.setExclusive(true);
//            mouseDevice.setCursorVisible(false);
//        }

        mouse = new MouseAccumulator();
        mouseDevice.registerListener(mouse);
    }

    public static void init() {
        ourInstance = new MouseManager();
    }

    public static void update() {
        ourInstance.mouseDevice.update();
        ourInstance.mouse.clearXAccumulator();
        ourInstance.mouse.clearYAccumulator();
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
