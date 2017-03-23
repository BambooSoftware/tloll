package com.bamboo.tloll.graphics;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.input.KeyboardHandler;
import com.bamboo.tloll.physics.PhysicsEngine;

import static com.bamboo.tloll.util.Utilities.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Class to represent all heroes, enemies, pets, etc.
 */

public class Unit extends Sprite {

    //TODO: have this handle player input config. That way we can have an up/left/down/right/tbdButtons assignments
    //TODO: then we can iterate over the list of inputs by player. Think input.java#pollInput doing something like a for
    //TODO: over each player and each player can handle their input mapping

    private Direction direction;
    private float acceleration;
    private float speed;
    private float speedX;
    private float speedY;
    private boolean right;
    private boolean up;
    private boolean outOfBoundsLeft;
    private boolean outOfBoundsRight;
    private boolean outOfBoundsUp;
    private boolean outOfBoundsDown;
    private int currentTileId;
    private float relativeTileX;
    private float relativeTileY;
    private boolean isAttackingRanged;
    private boolean isAttackingMelee;
    private int hitPoints;
    private int colNumber;
    private int frameSkip;

    public Unit() {
        super();
        this.acceleration = 1.0f;
        this.speed = 0.0f;
        this.speedX = 0.0f;
        this.speedY = 0.0f;
        this.right = false;
        this.outOfBoundsLeft = false;
        this.outOfBoundsRight = false;
        this.outOfBoundsUp = false;
        this.outOfBoundsDown = false;
        this.isAttackingRanged = false;
        this.isAttackingMelee = false;
        this.hitPoints = 3;
        this.direction = Direction.DOWN;
        this.colNumber = 1;
        this.frameSkip = 10;
    }

    public Unit(float posX, float posY, float width, float height, float acceleration, float speed, float speedX, float speedY, Direction direction) {
        super(posX, posY, width, height);
        this.acceleration = acceleration;
        this.speed = speed;
        this.speedX = speedX;
        this.speedY = speedY;
        this.right = false;
        this.outOfBoundsLeft = false;
        this.outOfBoundsRight = false;
        this.outOfBoundsUp = false;
        this.outOfBoundsDown = false;
        this.isAttackingRanged = false;
        this.isAttackingMelee = false;
        this.hitPoints = 3;
        this.direction = direction;
        this.colNumber = 1;
        this.frameSkip = 10;
    }

    public float getAcceleration() {
        return this.acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeedX() {
        return this.speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return this.speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public boolean getRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean getOutOfBoundsLeft() {
        return outOfBoundsLeft;
    }

    public void setOutOfBoundsLeft(boolean outOfBoundsLeft) {
        this.outOfBoundsLeft = outOfBoundsLeft;
    }

    public boolean getOutOfBoundsRight() {
        return outOfBoundsRight;
    }

    public void setOutOfBoundsRight(boolean outOfBoundsRight) {
        this.outOfBoundsRight = outOfBoundsRight;
    }

    public boolean getOutOfBoundsUp() {
        return outOfBoundsUp;
    }

    public void setOutOfBoundsUp(boolean outOfBoundsUp) {
        this.outOfBoundsUp = outOfBoundsUp;
    }

    public boolean getOutOfBoundsDown() {
        return outOfBoundsDown;
    }

    public void setOutOfBoundsDown(boolean outOfBoundsDown) {
        this.outOfBoundsDown = outOfBoundsDown;
    }

    public float getRelativeTileX() {
        return relativeTileX;
    }

    public void setRelativeTileX(float relativeTileX) {
        this.relativeTileX = relativeTileX;
    }

    public float getRelativeTileY() {
        return relativeTileY;
    }

    public void setRelativeTileY(float relativeTileY) {
        this.relativeTileY = relativeTileY;
    }

    public boolean isAttackingRanged() {
        return isAttackingRanged;
    }

    public void setIsAttackingRanged(boolean isAttackingRanged) {
        this.isAttackingRanged = isAttackingRanged;
    }

    public boolean isAttackingMelee() {
        return isAttackingMelee;
    }

    public void setIsAttackingMelee(boolean isAttackingMelee) {
        this.isAttackingMelee = isAttackingMelee;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getCurrentTileId() {
        return currentTileId;
    }

    public void setCurrentTileId(int currentTileId) {
        this.currentTileId = currentTileId;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getColNumber() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public int getFrameSkip() {
        return frameSkip;
    }

    public void setFrameSkip(int frameSkip) {
        this.frameSkip = frameSkip;
    }

    public void moveUpStart() {
        int numberOfSInputs  = getNumberOfSimultaneousInputs();

        //TODO: abstract away the speed calculation to be generic ?
        setSpeedY(getSpeedY() + getAcceleration());
        if (getSpeedY() > (Constants.MAX_SPEED_Y * (1.0F / numberOfSInputs))) {
            setSpeedY(Constants.MAX_SPEED_Y * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(this);
        setDirection(Direction.UP);
        calculateFrameSkip();
    }

    /**
     * This means we want to stop moving up and start to slow down.
     */
    public void moveUpStop() {
        if (getSpeedY() > 0.0f) {
            float playerReducedSpeed = getSpeedX() - getAcceleration();
            setSpeedY((playerReducedSpeed < 0) ? 0.0f : playerReducedSpeed);
        }
    }

    public void moveDownStart() {

        int numberOfSInputs  = getNumberOfSimultaneousInputs();


        setSpeedY(getSpeedY() - getAcceleration());
        if (getSpeedY() < (Constants.MIN_SPEED_Y * (1.0F / numberOfSInputs))) {
            setSpeedY(Constants.MIN_SPEED_Y * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(this);
        setDirection(Direction.DOWN);
        calculateFrameSkip();

    }

    public void moveDownStop() {
        if (getSpeedY() < 0.0f) {
            float playerReducedSpeed = getSpeedX() + getAcceleration();
            setSpeedY((playerReducedSpeed > 0) ? 0.0f : playerReducedSpeed);
        }
    }

    public void moveLeftStart() {

        int numberOfSInputs  = getNumberOfSimultaneousInputs();

        setSpeedX(getSpeedX() - getAcceleration());
        if (getSpeedX() < (Constants.MIN_SPEED_X * (1.0F / numberOfSInputs))) {
            setSpeedX(Constants.MIN_SPEED_X * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(this);
        setDirection(Direction.LEFT);
        calculateFrameSkip();

    }

    public void moveLeftStop() {
        if (getSpeedX() < 0.0f) {
            float playerReducedSpeed = getSpeedX() + getAcceleration();
            setSpeedX((playerReducedSpeed > 0) ? 0.0f : playerReducedSpeed);
        }
    }

    public void moveRightStart() {

        int numberOfSInputs  = getNumberOfSimultaneousInputs();


        setSpeedX(getSpeedX() + getAcceleration());
        if (getSpeedX() > (Constants.MAX_SPEED_X * (1.0F / numberOfSInputs))) {
            setSpeedX(Constants.MAX_SPEED_X * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(this);
        setDirection(Direction.RIGHT);
        calculateFrameSkip();
    }

    public void moveRightStop() {
        if (getSpeedX() > 0.0f) {
            float playerReducedSpeed = getSpeedX() - getAcceleration();
            setSpeedX((playerReducedSpeed < 0) ? 0.0f : playerReducedSpeed);
        }
    }

    //Puts a hard frame skip on the character
    //remove when we add FPS limit
    @Deprecated
    private void calculateFrameSkip() {
        if (getFrameSkip() < 0) {
            setColNumber(getColNumber() + 1);
            if (getColNumber() > 4) {
                setColNumber(1);
            }
        } else {
            setFrameSkip(getFrameSkip() - 1);
        }
    }

    //TODO: when the movement is refactored out perhaps we can have the players "input" choices be on a list.
    //TODO: that would help facilitate this to be more configurable in the future.
    private int getNumberOfSimultaneousInputs() {
        return  asInt(KeyboardHandler.isKeyDown(GLFW_KEY_W)) + asInt(KeyboardHandler.isKeyDown(GLFW_KEY_S)) +
                asInt(KeyboardHandler.isKeyDown(GLFW_KEY_A)) + asInt(KeyboardHandler.isKeyDown(GLFW_KEY_D));
    }

}
