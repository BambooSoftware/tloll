package com.bamboo.tloll.graphics;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.debug.Logger;
import com.bamboo.tloll.input.KeyboardHandler;
import com.bamboo.tloll.physics.PhysicsEngine;
import com.bamboo.tloll.physics.Vector3;

import static com.bamboo.tloll.util.Utilities.asInt;
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
    private Vector3 unitVector;
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

    private boolean debug;

    public Unit() {
        super();
        this.acceleration = 1.0f;
        this.unitVector = new Vector3(0.0f, 0.0f, 0.0f);
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
        this.debug = false;
    }

    public Unit(float posX, float posY, float width, float height, float acceleration, Vector3 v3, Direction direction) {
        super(posX, posY, width, height);
        this.acceleration = acceleration;
        this.unitVector = v3;
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
        this.debug = false;
    }

    public float getAcceleration() {
        return this.acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public Vector3 getUnitVector() {
        return this.unitVector;
    }

    public void setUnitVector(Vector3 unitVector) {
        this.unitVector = unitVector;
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

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void moveUpStart(Vector3 v3) {
        int numberOfSInputs = getNumberOfSimultaneousInputs();

        //TODO: abstract away the speed calculation to be generic ?
        //TODO: also load max speeds on a per player basis
        setUnitVector(getUnitVector().add(v3));
        if (getUnitVector().getYComponent() > (Constants.MAX_PLAYER_SPEED_UP * (1.0F / numberOfSInputs))) {
            getUnitVector().setYComponent(Constants.MAX_PLAYER_SPEED_UP * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(this);
        setDirection(Direction.UP);
        calculateFrameSkip();
    }

    public void moveUpStop(Vector3 v3) {
        if (getUnitVector().getYComponent() > 0.0f) {
            Vector3 unitReducedVector = getUnitVector().add(v3);
            if (unitReducedVector.getYComponent() < 0.0f) {
                unitReducedVector.setYComponent(0.0f);
            }
            setUnitVector(unitReducedVector);
            // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
            PhysicsEngine.movePlayer(this);
        }
    }

    public void moveDownStart(Vector3 v3) {
        int numberOfSInputs = getNumberOfSimultaneousInputs();

        setUnitVector(getUnitVector().add(v3));
        if (getUnitVector().getYComponent() < (Constants.MAX_PLAYER_SPEED_DOWN * (1.0F / numberOfSInputs))) {
            getUnitVector().setYComponent(Constants.MAX_PLAYER_SPEED_DOWN * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(this);
        setDirection(Direction.DOWN);
        calculateFrameSkip();
    }

    public void moveDownStop(Vector3 v3) {
        if (getUnitVector().getYComponent() < 0.0f) {
            Vector3 unitReducedVector = getUnitVector().add(v3);
            if (unitReducedVector.getYComponent() > 0) {
                unitReducedVector.setYComponent(0.0f);
            }
            setUnitVector(unitReducedVector);
            // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
            PhysicsEngine.movePlayer(this);
        }
    }

    public void moveLeftStart(Vector3 v3) {
        int numberOfSInputs = getNumberOfSimultaneousInputs();

        setUnitVector(getUnitVector().add(v3));
        if (getUnitVector().getXComponent() < (Constants.MAX_PLAYER_SPEED_LEFT * (1.0F / numberOfSInputs))) {
            getUnitVector().setXComponent(Constants.MAX_PLAYER_SPEED_LEFT * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(this);
        setDirection(Direction.LEFT);
        calculateFrameSkip();
    }

    public void moveLeftStop(Vector3 v3) {
        if (getUnitVector().getXComponent() < 0.0f) {
            Vector3 unitReducedVector = getUnitVector().add(v3);
            if (unitReducedVector.getXComponent() > 0) {
                unitReducedVector.setXComponent(0.0f);
            }
            setUnitVector(unitReducedVector);
            // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
            PhysicsEngine.movePlayer(this);
        }
    }

    public void moveRightStart(Vector3 v3) {
        int numberOfSInputs = getNumberOfSimultaneousInputs();

        setUnitVector(getUnitVector().add(v3));
        if (getUnitVector().getXComponent() > (Constants.MAX_PLAYER_SPEED_RIGHT * (1.0F / numberOfSInputs))) {
            getUnitVector().setXComponent(Constants.MAX_PLAYER_SPEED_RIGHT * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.movePlayer(this);
        setDirection(Direction.RIGHT);
        calculateFrameSkip();
    }

    public void moveRightStop(Vector3 v3) {
        if (getUnitVector().getXComponent() > 0.0f) {
            Vector3 unitReducedVector = getUnitVector().add(v3);
            if (unitReducedVector.getXComponent() < 0) {
                unitReducedVector.setXComponent(0.0f);
            }
            setUnitVector(unitReducedVector);
            // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
            PhysicsEngine.movePlayer(this);
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
        return asInt(KeyboardHandler.isKeyDown(GLFW_KEY_W)) + asInt(KeyboardHandler.isKeyDown(GLFW_KEY_S)) +
                asInt(KeyboardHandler.isKeyDown(GLFW_KEY_A)) + asInt(KeyboardHandler.isKeyDown(GLFW_KEY_D));

        //TODO: move me out
    }

    public void draw() {
        Renderer.drawAnimatedUnit(this, 0, this.getColNumber());

        if(debug) {
            highlightUnit();
            debugUnit();
        }
    }

    /**
     * Highlight the current tile the player lives on.
     */
    private void highlightUnit() {
        Logger.getInstance().highlightCurrentTile(this);
    }

    /**
     * Print out some debug info on the screen.
     */
    private void debugUnit() {
        Logger.getInstance().displayPlayerInfo(this);
        Logger.getInstance().displayOccupiedTiles(this);
    }

}
