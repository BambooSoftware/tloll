package com.bamboo.tloll.input;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.graphics.Direction;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.physics.PhysicsEngine;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    // TODO(map) : We have a cheesed out diagonal.  Should do proper vector math instead.
    // TODO(map) : The player stands still on diagonal against a wall.  Should slide instead.
    //TODO: Also direct movement may be better served inside the player. tbd.

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
    }

    private void handleKeyW(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
            keyPressW(player);
        } else {
            keyReleaseW(player);
        }
    }

    private void handleKeyS(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
            keyPressS(player);
        } else {
            keyReleaseS(player);
        }
    }

    private void handleKeyA(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
            keyPressA(player);
        } else {
            keyReleaseA(player);
        }
    }

    private void handleKeyD(Unit player) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
            keyPressD(player);
        } else {
            keyReleaseD(player);
        }
    }

    private void keyPressW(Unit player) {

        int numberOfSInputs  = getNumberOfSimultaneousInputs();

        System.out.println("Speed Y : " + (Constants.MAX_SPEED_Y * (1.0F / numberOfSInputs)));
        player.setSpeedY(player.getSpeedY() + player.getAcceleration());
        if (player.getSpeedY() > (Constants.MAX_SPEED_Y * (1.0F / numberOfSInputs))) {
            player.setSpeedY(Constants.MAX_SPEED_Y * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());
        player.setDirection(Direction.UP);
        if (player.getFrameSkip() < 0) {
            player.setColNumber(player.getColNumber() + 1);
            if (player.getColNumber() > 4) {
                player.setColNumber(1);
            }
        } else {
            player.setFrameSkip(player.getFrameSkip() - 1);
        }

    }

    private void keyReleaseW(Unit player) {
        if (player.getSpeedY() > 0.0f) {
            float playerReducedSpeed = player.getSpeedX() - player.getAcceleration();
            player.setSpeedY((playerReducedSpeed < 0) ? 0.0f : playerReducedSpeed);
        }
    }

    private void keyPressS(Unit player) {

        int numberOfSInputs  = getNumberOfSimultaneousInputs();


        player.setSpeedY(player.getSpeedY() - player.getAcceleration());
        if (player.getSpeedY() < (Constants.MIN_SPEED_Y * (1.0F / numberOfSInputs))) {
            player.setSpeedY(Constants.MIN_SPEED_Y * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());
        player.setDirection(Direction.DOWN);
        if (player.getFrameSkip() < 0) {
            player.setColNumber(player.getColNumber() + 1);
            if (player.getColNumber() > 4) {
                player.setColNumber(1);
            }
        } else {
            player.setFrameSkip(player.getFrameSkip() - 1);
        }

    }

    private void keyReleaseS(Unit player) {
        if (player.getSpeedY() < 0.0f) {
            //reduce the speed to 0;
            float playerReducedSpeed = player.getSpeedX() + player.getAcceleration();
            player.setSpeedY((playerReducedSpeed > 0) ? 0.0f : playerReducedSpeed);
        }
    }

    private void keyPressA(Unit player) {

        int numberOfSInputs  = getNumberOfSimultaneousInputs();


        player.setSpeedX(player.getSpeedX() - player.getAcceleration());
        if (player.getSpeedX() < (Constants.MIN_SPEED_X * (1.0F / numberOfSInputs))) {
            player.setSpeedX(Constants.MIN_SPEED_X * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());
        player.setDirection(Direction.LEFT);
        if (player.getFrameSkip() < 0) {
            player.setColNumber(player.getColNumber() + 1);
            if (player.getColNumber() > 4) {
                player.setColNumber(1);
            }
        } else {
            player.setFrameSkip(player.getFrameSkip() - 1);
        }

    }

    private void keyReleaseA(Unit player) {
        if (player.getSpeedX() < 0.0f) {
            //reduce the speed to 0;
            float playerReducedSpeed = player.getSpeedX() + player.getAcceleration();
            player.setSpeedX((playerReducedSpeed > 0) ? 0.0f : playerReducedSpeed);
        }
    }


    private void keyPressD(Unit player) {

        int numberOfSInputs  = getNumberOfSimultaneousInputs();


        player.setSpeedX(player.getSpeedX() + player.getAcceleration());
        if (player.getSpeedX() > (Constants.MAX_SPEED_X * (1.0F / numberOfSInputs))) {
            player.setSpeedX(Constants.MAX_SPEED_X * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());
        player.setDirection(Direction.RIGHT);
        if (player.getFrameSkip() < 0) {
            player.setColNumber(player.getColNumber() + 1);
            if (player.getColNumber() > 4) {
                player.setColNumber(1);
            }
        } else {
            player.setFrameSkip(player.getFrameSkip() - 1);
        }

    }

    private void keyReleaseD(Unit player) {
        if (player.getSpeedX() > 0.0f) {
            //reduce the speed to 0;
            float playerReducedSpeed = player.getSpeedX() - player.getAcceleration();
            player.setSpeedX((playerReducedSpeed < 0) ? 0.0f : playerReducedSpeed);
        }
    }


    private void keyPressEscape(long windowId) {
        if (KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(windowId, true);
        }
    }

    //TODO: when the movement is refactored out perhaps we can have the players "input" choices be on a list.
    //TODO: that would help facilitate this to be more configurable in the future.
    private int getNumberOfSimultaneousInputs() {
        return  asInt(KeyboardHandler.isKeyDown(GLFW_KEY_W)) + asInt(KeyboardHandler.isKeyDown(GLFW_KEY_S)) +
                asInt(KeyboardHandler.isKeyDown(GLFW_KEY_A)) + asInt(KeyboardHandler.isKeyDown(GLFW_KEY_D));
    }

    private int asInt(boolean b) {
        return b ? 1 : 0;
    }

}
