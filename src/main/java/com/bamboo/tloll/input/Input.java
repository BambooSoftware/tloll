package com.bamboo.tloll.input;

import com.bamboo.tloll.Constants;
import com.bamboo.tloll.graphics.Direction;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Scene;
import com.bamboo.tloll.physics.PhysicsEngine;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    /*
     * The slick callback wont wory because its too laggy with the difference between key press and key repeat.
     * Functionally useless.
     */
    public void pollInput(long windowId, Unit player, Scene scene) {
        keyPressEscape(windowId);
        handleKeyW(player, scene);
        handleKeyS(player, scene);
        handleKeyA(player, scene);
        handleKeyD(player, scene);
    }

    private void handleKeyW(Unit player, Scene scene) {
        if(KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
            keyPressW(player, scene);
        } else {
            keyReleaseW(player, scene);
        }
    }

    private void handleKeyS(Unit player, Scene scene) {
        if(KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
            keyPressS(player, scene);
        } else {
            keyReleaseS(player, scene);
        }
    }

    private void handleKeyA(Unit player, Scene scene) {
        if(KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
            keyPressA(player, scene);
        } else {
            keyReleaseA(player, scene);
        }
    }

    private void handleKeyD(Unit player, Scene scene) {
        if(KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
            keyPressD(player, scene);
        } else {
            keyReleaseD(player, scene);
        }
    }


    private void keyPressW(Unit player, Scene scene) {

        player.setSpeedY(player.getSpeedY() + player.getAcceleration());
        if (player.getSpeedY() > Constants.MAX_SPEED_Y) {
            player.setSpeedY(Constants.MAX_SPEED_Y);
        }
        PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), scene);
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

    private void keyPressS(Unit player, Scene scene) {

        player.setSpeedY(player.getSpeedY() - player.getAcceleration());
        if (player.getSpeedY() < Constants.MIN_SPEED_Y) {
            player.setSpeedY(Constants.MIN_SPEED_Y);
        }
        PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), scene);
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

    private void keyPressA(Unit player, Scene scene) {


        player.setSpeedX(player.getSpeedX() - player.getAcceleration());
        System.out.println("Player's acceleration: " + (player.getAcceleration() * -1));
        System.out.println("Players speed: " + player.getSpeedX());
        if (player.getSpeedX() < Constants.MIN_SPEED_X) {
            player.setSpeedX(Constants.MIN_SPEED_X);
        }
        PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), scene);
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

    private void keyPressD(Unit player, Scene scene) {


        player.setSpeedX(player.getSpeedX() + player.getAcceleration());
        if (player.getSpeedX() > Constants.MAX_SPEED_X) {
            player.setSpeedX(Constants.MAX_SPEED_X);
        }
        PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), scene);
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

    private void keyReleaseW(Unit player, Scene scene) {
        player.setSpeedY(0.0f);
        resetNonMovingPlayer(player);
    }

    private void keyReleaseS(Unit player, Scene scene) {
        player.setSpeedY(0.0f);
        resetNonMovingPlayer(player);
    }

    private void keyReleaseA(Unit player, Scene scene) {
        player.setSpeedX(0.0f);
        resetNonMovingPlayer(player);
    }

    private void keyReleaseD(Unit player, Scene scene) {
        player.setSpeedX(0.0f);
        resetNonMovingPlayer(player);
    }

    private void resetNonMovingPlayer(Unit player) {
        //resets the player to the standing position when not moving
        if(player.getSpeedY() == 0.0f && player.getSpeedX() == 0.0f) {
          player.setColNumber(1);
        }
    }

    private void keyPressEscape(long windowId) {
        if(KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(windowId, true);
        }
    }





}
