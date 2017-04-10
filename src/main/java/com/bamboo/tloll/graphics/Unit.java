package com.bamboo.tloll.graphics;

import com.bamboo.tloll.debug.Logger;
import com.bamboo.tloll.physics.PhysicsEngine;
import com.bamboo.tloll.physics.Vector3;

/**
 * Class to represent all heroes, enemies, pets, etc.
 */

public class Unit extends Sprite {

    //TODO: have this handle player input config. That way we can have an up/left/down/right/tbdButtons assignments
    //TODO: then we can iterate over the list of inputs by player. Think input.java#pollInput doing something like a for
    //TODO: over each player and each player can handle their input mapping

    private Direction direction;
    private float acceleration;
    private float maxSpeed;
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

    private boolean debug;

    private long lastMoved;

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
        this.debug = false;
        this.lastMoved = 0;
    }

    public Unit(float posX, float posY, float width, float height, float acceleration, Vector3 v3, Direction direction, float maxSpeed) {
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
        this.debug = false;
        this.maxSpeed = maxSpeed;
        this.lastMoved = 0;
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

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void moveUpStart(Vector3 v3, int numberOfSInputs) {

        //TODO: abstract away the speed calculation to be generic ?
        setUnitVector(getUnitVector().add(v3));

        if (getUnitVector().getYComponent() > (getMaxSpeedUp() * (1.0F / numberOfSInputs))) {
            getUnitVector().setYComponent(getMaxSpeedUp() * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.getInstance().movePlayer(this);
        setDirection(Direction.UP);
    }

    public void moveUpStop(Vector3 v3) {
        if (getUnitVector().getYComponent() > 0.0f) {
            Vector3 unitReducedVector = getUnitVector().add(v3);
            if (unitReducedVector.getYComponent() < 0.0f) {
                unitReducedVector.setYComponent(0.0f);
            }
            setUnitVector(unitReducedVector);
            // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
            PhysicsEngine.getInstance().movePlayer(this);
        }
    }

    public void moveDownStart(Vector3 v3, int numberOfSInputs) {
        setUnitVector(getUnitVector().add(v3));
        if (getUnitVector().getYComponent() < (getMaxSpeedDown() * (1.0F / numberOfSInputs))) {
            getUnitVector().setYComponent(getMaxSpeedDown() * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.getInstance().movePlayer(this);
        setDirection(Direction.DOWN);
    }

    public void moveDownStop(Vector3 v3) {
        if (getUnitVector().getYComponent() < 0.0f) {
            Vector3 unitReducedVector = getUnitVector().add(v3);
            if (unitReducedVector.getYComponent() > 0) {
                unitReducedVector.setYComponent(0.0f);
            }
            setUnitVector(unitReducedVector);
            // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
            PhysicsEngine.getInstance().movePlayer(this);
        }
    }

    public void moveLeftStart(Vector3 v3, int numberOfSInputs) {
        setUnitVector(getUnitVector().add(v3));
        if (getUnitVector().getXComponent() < (getMaxSpeedLeft() * (1.0F / numberOfSInputs))) {
            getUnitVector().setXComponent(getMaxSpeedLeft() * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.getInstance().movePlayer(this);
        setDirection(Direction.LEFT);
    }

    public void moveLeftStop(Vector3 v3) {
        if (getUnitVector().getXComponent() < 0.0f) {
            Vector3 unitReducedVector = getUnitVector().add(v3);
            if (unitReducedVector.getXComponent() > 0) {
                unitReducedVector.setXComponent(0.0f);
            }
            setUnitVector(unitReducedVector);
            // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
            PhysicsEngine.getInstance().movePlayer(this);
        }
    }

    public void moveRightStart(Vector3 v3, int numberOfSInputs) {
        setUnitVector(getUnitVector().add(v3));
        if (getUnitVector().getXComponent() > (getMaxSpeedRight() * (1.0F / numberOfSInputs))) {
            getUnitVector().setXComponent(getMaxSpeedRight() * (1.0F / numberOfSInputs));
        }
        PhysicsEngine.getInstance().movePlayer(this);
        setDirection(Direction.RIGHT);
    }

    public void moveRightStop(Vector3 v3) {
        if (getUnitVector().getXComponent() > 0.0f) {
            Vector3 unitReducedVector = getUnitVector().add(v3);
            if (unitReducedVector.getXComponent() < 0) {
                unitReducedVector.setXComponent(0.0f);
            }
            setUnitVector(unitReducedVector);
            // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
            PhysicsEngine.getInstance().movePlayer(this);
        }
    }

    public void draw() {
        if(debug) {
            highlightUnit();
            debugUnit();
        }

        Renderer.drawAnimatedUnit(this, 0, this.getColNumber());
    }

    /**
     * Highlight the current tile the player lives on.F
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

    private float getMaxSpeedUp() {
        return this.maxSpeed;
    }

    private float getMaxSpeedDown() {
        return this.maxSpeed * -1.0f;
    }

    private float getMaxSpeedLeft() {
        return this.maxSpeed * -1.0f;
    }

    private float getMaxSpeedRight() {
        return this.maxSpeed;
    }

    public long getLastMoved() {
        return lastMoved;
    }

    public void setLastMoved(long lastMoved) {
        this.lastMoved = lastMoved;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }


}
