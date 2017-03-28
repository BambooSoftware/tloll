package com.bamboo.tloll.input;

import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.physics.Vector3;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    // TODO(map) : We have a cheesed out diagonal.  Should do proper vector math instead.
    // TODO(map) : The player stands still on diagonal against a wall.  Should slide instead.

    /*
     * The slick callback wont work because its too laggy with the difference between key press and key repeat.
     * Functionally useless.
     */
    public void pollInput(long windowId, Unit player) {
        handleKeyPresses(windowId, player);
    }

    private void handleKeyPresses(long windowId, Unit player) {
        keyPressEscape(windowId);
        handleKeyW(player);
        handleKeyS(player);
        handleKeyA(player);
        handleKeyD(player);
        keyPressToggleWorld();
    }

    // TODO(map) : Can we find a way to make these methods a single method where we can pass a vector3 object that
    // is the sum of all the values in the different directions????
    private void handleKeyW(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
            player.moveUpStart(new Vector3(0.0f, 1.0f, 0.0f));
        } else {
            player.moveUpStop(new Vector3(0.0f, -1.0f, 0.0f));
        }
    }

    private void handleKeyS(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
            player.moveDownStart(new Vector3(0.0f, -1.0f, 0.0f));
        } else {
            player.moveDownStop(new Vector3(0.0f, 1.0f, 0.0f));
        }
    }

    private void handleKeyA(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
            player.moveLeftStart(new Vector3(-1.0f, 0.0f, 0.0f));
        } else {
            player.moveLeftStop(new Vector3(1.0f, 0.0f, 0.0f));
        }
    }

    private void handleKeyD(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
            player.moveRightStart(new Vector3(1.0f, 0.0f, 0.0f));
        } else {
            player.moveRightStop(new Vector3(-1.0f, 0.0f, 0.0f));
        }
    }

    private void keyPressEscape(long windowId) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(windowId, true);
        }
    }

    private void keyPressToggleWorld() {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_T)) {
            //TODO: Switch for toggling between world and combat mode testing
        }
    }

}
