package com.bamboo.tloll.input;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.graphics.Direction;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.physics.PhysicsEngine;

import java.util.List;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    // TODO(map) : We have a cheesed out diagonal.  Should do proper vector math instead.
    // TODO(map) : The player stands still on diagonal against a wall.  Should slide instead.
    //TODO: Also direct movement may be better served inside the player. tbd.

    /*
     * The slick callback wont wory because its too laggy with the difference between key press and key repeat.
     * Functionally useless.
     */
    List<Character> inputList = new ArrayList<>();

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
        if(KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
	    if (inputList.indexOf('W') == -1)
		inputList.add('W');
	    keyPressW(player);
        } else {
	    keyReleaseW(player);
	    if (inputList.indexOf('W') != -1)
		inputList.remove(inputList.indexOf('W'));
        }
    }

    private void handleKeyS(Unit player) {
        if(KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
	    if (inputList.indexOf('S') == -1)
		inputList.add('S');
            keyPressS(player);
        } else {
            keyReleaseS(player);
	    if (inputList.indexOf('S') != -1)
		inputList.remove(inputList.indexOf('S'));
        }
    }

    private void handleKeyA(Unit player) {
        if(KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
	    if (inputList.indexOf('A') == -1)
		inputList.add('A');
	    keyPressA(player);
        } else {
            keyReleaseA(player);
	    if (inputList.indexOf('A') != -1)
		inputList.remove(inputList.indexOf('A'));
        }
    }

    private void handleKeyD(Unit player) {
        if(KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
	    if (inputList.indexOf('D') == -1)
		inputList.add('D');
            keyPressD(player);
        } else {
            keyReleaseD(player);
	    if (inputList.indexOf('D') != -1)
		inputList.remove(inputList.indexOf('D'));
        }
    }


    private void keyPressW(Unit player) {
	System.out.println("Speed Y : " + (Constants.MAX_SPEED_Y * (1.0F / inputList.size())));
        player.setSpeedY(player.getSpeedY() + player.getAcceleration());
        if (player.getSpeedY() > (Constants.MAX_SPEED_Y * (1.0F / inputList.size()))) {
	    player.setSpeedY(Constants.MAX_SPEED_Y * (1.0F / inputList.size()));
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

        player.setSpeedY(player.getSpeedY() - player.getAcceleration());
        if (player.getSpeedY() < (Constants.MIN_SPEED_Y * (1.0F / inputList.size()))) {
            player.setSpeedY(Constants.MIN_SPEED_Y * (1.0F / inputList.size()));
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
        player.setSpeedX(player.getSpeedX() - player.getAcceleration());
        if (player.getSpeedX() < (Constants.MIN_SPEED_X * (1.0F / inputList.size()))) {
            player.setSpeedX(Constants.MIN_SPEED_X * (1.0F / inputList.size()));
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
        player.setSpeedX(player.getSpeedX() + player.getAcceleration());
        if (player.getSpeedX() > (Constants.MAX_SPEED_X * (1.0F / inputList.size()))) {
            player.setSpeedX(Constants.MAX_SPEED_X * (1.0F / inputList.size()));
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
        if(KeyboardHandler.isKeyDown(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(windowId, true);
        }
    }

}
