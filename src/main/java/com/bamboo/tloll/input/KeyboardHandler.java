package com.bamboo.tloll.input;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * https://tutorialedge.net/lwjgl-3-keyboard-input-beginners-tutorial
 *
 * Created by ablackbu on 3/18/17.
 */
public class KeyboardHandler extends GLFWKeyCallback {


    public static boolean[] keys = new boolean[65536];


    // The GLFWKeyCallback class is an abstract method that
    // can't be instantiated by itself and must instead be extended
    //
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
    }

    // boolean method that returns true if a given key
    // is pressed.
    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }


}
