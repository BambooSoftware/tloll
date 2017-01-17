package com.bamboo.tloll.graphics;

/**
 * Class to represent all heroes, enemies, pets, etc.
 */

public class Unit extends Sprite
{

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
    private int currentTileNum;
    private float relativeTileX;
    private float relativeTileY;
    private boolean isAttackingRanged;
    private boolean isAttackingMelee;
    private int hitPoints;
    
    public Unit()
    {
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
    }

    public Unit(float posX,
		float posY,
		float width,
		float height,
		float acceleration,
		float speed,
		float speedX,
		float speedY)
    {
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
    }

    public float getAcceleration()
    {
	return this.acceleration;
    }
    public void setAcceleration(float acceleration)
    {
	this.acceleration = acceleration;
    }

    public float getSpeed()
    {
	return this.speed;
    }
    public void setSpeed(float speed)
    {
	this.speed = speed;
    }

    public float getSpeedX()
    {
	return this.speedX;
    }
    public void setSpeedX(float speedX)
    {
	this.speedX = speedX;
    }

    public float getSpeedY()
    {
	return this.speedY;
    }
    public void setSpeedY(float speedY)
    {
	this.speedY = speedY;
    }

    public boolean getRight()
    {
	return right;
    }
    public void setRight(boolean right)
    {
	this.right = right;
    }

    public boolean getOutOfBoundsLeft()
    {
	return outOfBoundsLeft;
    }
    public void setOutOfBoundsLeft(boolean outOfBoundsLeft)
    {
	this.outOfBoundsLeft = outOfBoundsLeft;
    }

    public boolean getOutOfBoundsRight()
    {
	return outOfBoundsRight;
    }
    public void setOutOfBoundsRight(boolean outOfBoundsRight)
    {
	this.outOfBoundsRight = outOfBoundsRight;
    }

    public boolean getOutOfBoundsUp()
    {
	return outOfBoundsUp;
    }
    public void setOutOfBoundsUp(boolean outOfBoundsUp)
    {
	this.outOfBoundsUp = outOfBoundsUp;
    }

    public boolean getOutOfBoundsDown()
    {
	return outOfBoundsDown;
    }
    public void setOutOfBoundsDown(boolean outOfBoundsDown)
    {
	this.outOfBoundsDown = outOfBoundsDown;
    }

    public float getRelativeTileX()
    {
	return relativeTileX;
    }
    public void setRelativeTileX(float relativeTileX)
    {
	this.relativeTileX = relativeTileX;
    }

    public float getRelativeTileY()
    {
	return relativeTileY;
    }
    public void setRelativeTileY(float relativeTileY)
    {
	this.relativeTileY = relativeTileY;
    }

    public boolean isAttackingRanged()
    {
	return isAttackingRanged;
    }
    public void setIsAttackingRanged(boolean isAttackingRanged)
    {
	this.isAttackingRanged = isAttackingRanged;
    }

    public boolean isAttackingMelee()
    {
	return isAttackingMelee;
    }
    public void setIsAttackingMelee(boolean isAttackingMelee)
    {
	this.isAttackingMelee = isAttackingMelee;
    }

    public int getHitPoints()
    {
	return hitPoints;
    }
    public void setHitPoints(int hitPoints)
    {
	this.hitPoints = hitPoints;
    }
}
