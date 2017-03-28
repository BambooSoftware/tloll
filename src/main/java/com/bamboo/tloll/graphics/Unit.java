package com.bamboo.tloll.graphics;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.input.KeyboardHandler;
import com.bamboo.tloll.physics.PhysicsEngine;
import com.bamboo.tloll.physics.Vector3;

import static com.bamboo.tloll.util.Utilities.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Class to represent all heroes, enemies, pets, etc.
 */

public class Unit extends Sprite {

    //TODO: have this handle player input config. That way we can have an up/left/down/right/tbdButtons assignments
    //TODO: then we can iterate over the list of inputs by player. Think input.java#pollInput doing something like a for
    //TODO: over each player and each player can handle their input mapping

    // TODO(map) : Vector3 needs to be handled in the following mannner:
    // Player speed represented as a Vector3 object.
    // If movement is to be applied, we will add a new vector to the current vector.
    // The sum of these vectors will be stored as the player's new speed up to a certain cap.
    // If the cap is reached and another vector in the same direction is added that will be ignored.

    // TODO(map) : Fix checking for facing left, right, up, or down based on Vector values.

    private Direction direction;
    private float acceleration;
    private Vector3 unitVector;
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
	this.unitVector = new Vector3(0.0f, 0.0f, 0.0f);
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

    public Unit(float posX, float posY, float width, float height, float acceleration, Vector3 v3, Direction direction) {
        super(posX, posY, width, height);
        this.acceleration = acceleration;
        this.unitVector = v3;
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

    public Vector3 getUnitVector()
    {
	return this.unitVector;
    }

    public void setUnitVector(Vector3 unitVector)
    {
	this.unitVector = unitVector;
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

    public void moveUpStart(Vector3 v3) {
        int numberOfSInputs  = getNumberOfSimultaneousInputs();

        //TODO: abstract away the speed calculation to be generic ?
        //TODO: also load max speeds on a per player basis
	setUnitVector(getUnitVector().add(v3));
	if (getUnitVector().getYComponent() > (Constants.MAX_SPEED_X * (1.0F / numberOfSInputs)))
	    {
		getUnitVector().setYComponent(Constants.MAX_SPEED_X * (1.0F / numberOfSInputs));
	    }
        PhysicsEngine.movePlayer(this, getUnitVector());
        setDirection(Direction.UP);
        calculateFrameSkip();
    }

    public void moveUpStop(Vector3 v3) {
	if (getUnitVector().getYComponent() > 0.0f) {
	    Vector3 unitReducedVector = getUnitVector().add(v3);
	    if (unitReducedVector.getYComponent() < 0.0f)
		{
		    unitReducedVector.setYComponent(0.0f);
		}
	    setUnitVector(unitReducedVector);
	    // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
	    PhysicsEngine.movePlayer(this, getUnitVector());
        }
    }
	
    public void moveDownStart(Vector3 v3) {
        int numberOfSInputs  = getNumberOfSimultaneousInputs();

	setUnitVector(getUnitVector().add(v3));
	if (getUnitVector().getYComponent() < (Constants.MIN_SPEED_X * (1.0F / numberOfSInputs)))
	    {
		getUnitVector().setYComponent(Constants.MIN_SPEED_X * (1.0F / numberOfSInputs));
	    }
        PhysicsEngine.movePlayer(this, getUnitVector());
        setDirection(Direction.DOWN);
        calculateFrameSkip();
    }

    public void moveDownStop(Vector3 v3) {
        if (getUnitVector().getYComponent() < 0.0f) {
	    Vector3 unitReducedVector = getUnitVector().add(v3);
	    if (unitReducedVector.getYComponent() > 0)
		{
		    unitReducedVector.setYComponent(0.0f);
		}
	    setUnitVector(unitReducedVector);
	    // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
	    PhysicsEngine.movePlayer(this, getUnitVector());
        }
    }

    public void moveLeftStart(Vector3 v3) {
        int numberOfSInputs  = getNumberOfSimultaneousInputs();

	setUnitVector(getUnitVector().add(v3));
	if (getUnitVector().getXComponent() < (Constants.MIN_SPEED_X * (1.0F / numberOfSInputs)))
	    {
		getUnitVector().setXComponent(Constants.MIN_SPEED_X * (1.0F / numberOfSInputs));
	    }
        PhysicsEngine.movePlayer(this, getUnitVector());
        setDirection(Direction.LEFT);
        calculateFrameSkip();
    }

    public void moveLeftStop(Vector3 v3) {
        if (getUnitVector().getXComponent() < 0.0f) {
	    Vector3 unitReducedVector = getUnitVector().add(v3);
	    if (unitReducedVector.getXComponent() > 0)
		{
		    unitReducedVector.setXComponent(0.0f);
		}
            setUnitVector(unitReducedVector);
	    // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
	    PhysicsEngine.movePlayer(this, getUnitVector());
        }
    }

    public void moveRightStart(Vector3 v3) {
        int numberOfSInputs  = getNumberOfSimultaneousInputs();

	setUnitVector(getUnitVector().add(v3));
	if (getUnitVector().getXComponent() > (Constants.MAX_SPEED_X * (1.0F / numberOfSInputs)))
	    {
		getUnitVector().setXComponent(Constants.MAX_SPEED_X * (1.0F / numberOfSInputs));
	    }
        PhysicsEngine.movePlayer(this, getUnitVector());
        setDirection(Direction.RIGHT);
        calculateFrameSkip();
    }

    public void moveRightStop(Vector3 v3) {
        if (getUnitVector().getXComponent() > 0.0f) {
            Vector3 unitReducedVector = getUnitVector().add(v3);
	    if (unitReducedVector.getXComponent() < 0)
		{
		    unitReducedVector.setXComponent(0.0f);
		}
            setUnitVector(unitReducedVector);
	    // TODO(map) : I've added this in but I'm not sure if we want to always call a physics engine move here.
	    PhysicsEngine.movePlayer(this, getUnitVector());
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
