/**
 * Created by IntelliJ IDEA.
 * User: Ignacio
 * Date: 10/03/2007
 * Time: 17:11:17
 * To change this template use File | Settings | File Templates.
 */
package edu.ua.j3dengine.core.input;

import net.jtank.input.KeyArrayListener;
import net.jtank.input.KeyboardDevice;
import net.jtank.input.awt.AWTKeyboard;

import java.awt.*;

//todo see InputManagerImpl

public class KeyboardManager {

    private static KeyboardManager ourInstance = new KeyboardManager();
    private KeyboardDevice keyboardDevice;
    private KeyArrayListener keyboard;

    private KeyboardManager() {
        this.keyboardDevice = new AWTKeyboard(new Frame()); //todo DUMMY FRAME, FIX!!
        this.keyboard = new KeyArrayListener();
        this.keyboardDevice.registerListener(keyboard);
    }

    public static void init() {
        ourInstance = new KeyboardManager();
    }

    public static void update() {
        ourInstance.keyboardDevice.update();
    }

    public static boolean isKeyPressed(int key) {
        return ourInstance.keyboard.isPressed(key);
    }
}