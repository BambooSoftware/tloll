package com.bamboo.tloll.input;

import com.bamboo.tloll.graphics.Direction;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.structure.Scene;
import com.bamboo.tloll.physics.PhysicsEngine;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    // NOTE(map) : These speeds are based on average human running speed in m/s.  This also assumes
    // that 1 pixel is equivalent to 1 meter game space.
    float maxSpeedX = 7.0f;
    float minSpeedX = -7.0f;
    float maxSpeedY = 7.0f;
    float minSpeedY = -7.0f;

    Map<Integer, Integer> keyStates;

    public Input() {
        keyStates = new HashMap<>();
        keyStates.put(GLFW_KEY_W, GLFW_RELEASE);
        keyStates.put(GLFW_KEY_S, GLFW_RELEASE);
        keyStates.put(GLFW_KEY_A, GLFW_RELEASE);
        keyStates.put(GLFW_KEY_D, GLFW_RELEASE);
    }


    /*
     * The slick callback wont wory because its too laggy with the difference between key press and key repeat.
     * Functionally useless.
     */
    public void pollInput(long windowId, Unit player, Scene scene) {




        setKeyStates(windowId);




    }

    private void setKeyStates(long windowId) {
        keyStates.put(GLFW_KEY_W, glfwGetKey(windowId, GLFW_KEY_W));
        keyStates.put(GLFW_KEY_S, glfwGetKey(windowId, GLFW_KEY_S));
        keyStates.put(GLFW_KEY_A, glfwGetKey(windowId, GLFW_KEY_A));
        keyStates.put(GLFW_KEY_D, glfwGetKey(windowId, GLFW_KEY_D));
    }


    //We can't use the repeate because the deplay in the OS between registering a repeat and a press is too slow
    //it appears to be laggy
    public void setupKeyListener(long windowId, Unit player, Scene scene) {
        glfwSetKeyCallback(windowId, (window, key, scancode, action, mods) -> {
            switch (action) {
                case GLFW_RELEASE: setupKeyRelease(key, player, scene); break;
                case GLFW_PRESS: setupKeyPress(key, player, scene, windowId); break;
                default: System.out.println("Action not programmed id: " + action); break;
            }
        });
    }

    private void setupKeyPress(int key, Unit player, Scene scene, long windowId) {
        switch (key) {
            case GLFW_KEY_ESCAPE: keyPressEscape(windowId); break;
            case GLFW_KEY_W: keyPressW(player, scene); break;
            case GLFW_KEY_S: keyPressS(player, scene); break;
            case GLFW_KEY_A: keyPressA(player, scene); break;
            case GLFW_KEY_D: keyPressD(player, scene); break;
            default: System.out.println("Key press not programmed id: " + key); break;
        }
    }

    private void setupKeyRelease(int key, Unit player, Scene scene) {
        switch (key) {
            case GLFW_KEY_ESCAPE: break;
            case GLFW_KEY_W: keyReleaseW(player, scene); break;
            case GLFW_KEY_S: keyReleaseS(player, scene); break;
            case GLFW_KEY_A: keyReleaseA(player, scene); break;
            case GLFW_KEY_D: keyReleaseD(player, scene); break;
            default: System.out.println("Key release not programmed id: " + key); break;
        }

        //resets the player to the standing position when not moving
        if(player.getSpeedY() == 0.0f && player.getSpeedX() == 0.0f) {
            player.setColNumber(1);
        }

    }


    private void keyPressW(Unit player, Scene scene) {

        player.setSpeedY(player.getSpeedY() + player.getAcceleration());
        if (player.getSpeedY() > maxSpeedY) {
            player.setSpeedY(maxSpeedY);
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
        if (player.getSpeedY() < minSpeedY) {
            player.setSpeedY(minSpeedY);
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
        if (player.getSpeedX() < minSpeedX) {
            player.setSpeedX(minSpeedX);
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
        if (player.getSpeedX() > maxSpeedX) {
            player.setSpeedX(maxSpeedX);
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
        //player.setSpeedY(0.0f);
    }

    private void keyReleaseS(Unit player, Scene scene) {
        //player.setSpeedY(0.0f);
    }

    private void keyReleaseA(Unit player, Scene scene) {
        //player.setSpeedX(0.0f);
    }

    private void keyReleaseD(Unit player, Scene scene) {
        //player.setSpeedX(0.0f);
    }

    private void keyPressEscape(long windowId) {
        glfwSetWindowShouldClose(windowId, true);
    }





}
