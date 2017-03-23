package com.bamboo.tloll.input;

import com.bamboo.tloll.graphics.Unit;

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

    private void handleKeyW(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
            player.moveUpStart();
        } else {
            player.moveUpStop();
        }
    }

    private void handleKeyS(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
            player.moveDownStart();
        } else {
            player.moveDownStop();
        }
    }

    private void handleKeyA(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
            player.moveLeftStart();
        } else {
            player.moveLeftStop();
        }
    }

    private void handleKeyD(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
            player.moveRightStart();
        } else {
            player.moveRightStop();
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
