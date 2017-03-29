package com.bamboo.tloll.input;

import com.bamboo.tloll.Config;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private static Input _instance;

    public static Input getInstance() {
        if(_instance == null) {
            _instance = new Input();
        }
        return _instance;
    }

    private Input() {
    }

    public void poll() {
        keyPressEscape();
        keyPressToggleWorld();
    }

    private void keyPressEscape() {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(Config.getInstance().getWindowId(), true);
        }
    }

    private void keyPressToggleWorld() {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_T)) {
            //TODO: Switch for toggling between world and combat mode testing
        }
    }

}