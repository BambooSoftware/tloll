package com.bamboo.tloll.graphics;

import com.bamboo.tloll.physics.MapCoordinates;
import com.bamboo.tloll.physics.HitBox;
import com.bamboo.tloll.physics.Vertex;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Sprite class that represents the basic unit of everything in the game.
 */
public class Sprite {

    private float posX;
    private float posY;
    private float posZ;
    private float width;
    private float height;
    private float centerX;
    private float centerY;
    private int animatedFrameNumber;
    private boolean isJumping;
    private boolean isRising;
    private HitBox hitbox;
    
    private Map<Integer, SpriteBuffer> bufferMap;

    public Sprite() {
	this.posX = 0.0f;
	this.posY = 0.0f;
	this.posZ = 0.0f;
        this.width = 10.0f;
        this.height = 10.0f;
	this.isJumping = false;
	this.isRising = false;
	this.centerX = this.posX + (this.width / 2);
        this.centerY = this.posY + (this.height / 2);
        this.animatedFrameNumber = 1;
        this.bufferMap = new HashMap<>();
	this.hitbox = new HitBox(new Vertex(posX, posY),
				 new Vertex(posX + width, posY),
				 new Vertex(posX, posY + height),
				 new Vertex(posX + width, posY + height));
    }

    public Sprite(float posX, float posY, float width, float height) {
	this.posX = posX;
	this.posY = posY;
	this.posZ = 0.0f;
        this.width = width;
        this.height = height;
	this.isJumping = false;
	this.isRising = false;
        this.centerX = posX + (width / 2);
        this.centerY = posY + (height / 2);
        this.animatedFrameNumber = 1;
        this.bufferMap = new HashMap<>();
	this.hitbox = new HitBox(new Vertex(posX, posY),
				 new Vertex((posX + width), posY),
				 new Vertex(posX, (posY + height)),
				 new Vertex((posX + width), (posY + height)));
    }

    public float getPosX()
    {
	return posX;
    }

    public void setPosX(float posX)
    {
	this.posX = posX;
    }

    public float getPosY()
    {
	return posY;	
    }

    public void setPosY(float posY)
    {
	this.posY = posY;
    }
    
    public float getPosZ()
    {
	return posZ;	
    }

    public void setPosZ(float posZ)
    {
	this.posZ = posZ;
    }
    
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public boolean isJumping()
    {
	return isJumping;
    }

    public void setIsJumping(boolean isJumping)
    {
	this.isJumping = isJumping;
    }

    public boolean isRising()
    {
	return isRising;
    }

    public void setIsRising(boolean isRising)
    {
	this.isRising = isRising;
    }
    
    public int getAnimatedSpriteNumber() {
        return animatedFrameNumber;
    }

    public void setAnimatedSpriteNumber(int animatedFrameNumber) {
        this.animatedFrameNumber = animatedFrameNumber;
    }

    public Map<Integer, SpriteBuffer> getBufferMap() {
        return bufferMap;
    }

    public void addBufferToMap(Integer bufferId, SpriteBuffer buffer) {
        this.bufferMap.put(bufferId, buffer);
    }

    public HitBox getHitBox() {
	return hitbox;
    }

    public void updateHitBox(float deltaX, float deltaY, float deltaZ)
    {
	this.hitbox.getLowerLeft().setX(this.posX + deltaX);
	this.hitbox.getLowerLeft().setY(this.posY + deltaY);
	this.hitbox.getLowerRight().setX(this.posX + this.width + deltaX);
	this.hitbox.getLowerRight().setY(this.posY + deltaY);
	this.hitbox.getUpperLeft().setX(this.posX + deltaX);
	this.hitbox.getUpperLeft().setY(this.posY + this.height + deltaY);
	this.hitbox.getUpperRight().setX(this.posX + this.width + deltaX);
	this.hitbox.getUpperRight().setY(this.posY + this.height + deltaY);
    }
    // NOTE(map) : I'm not putting a setter in here for a hitbox since there is no need to dynamically change it
    // right now and would be a waste of code.

    // NOTE(map) : This is only doing single point collsion right now.  I need to update it to check all four corners
    // of the hit box and if any of them fall within the hitbox of the other sprite.
    public boolean collisionBetweenBoxes(float deltaX, float deltaY, Sprite aSprite)
    {
	if (this.hitbox.getLowerLeft().getX() + deltaX >= aSprite.getHitBox().getLowerLeft().getX() &&
	    this.hitbox.getLowerLeft().getX() + deltaX <= aSprite.getHitBox().getLowerRight().getX() &&
	    this.hitbox.getLowerLeft().getY() + deltaY >= aSprite.getHitBox().getLowerLeft().getY() &&
	    this.hitbox.getLowerLeft().getY() + deltaY <= aSprite.getHitBox().getUpperLeft().getY())
	    return true;
	else if (this.hitbox.getLowerRight().getX() + deltaX >= aSprite.getHitBox().getLowerLeft().getX() &&
		 this.hitbox.getLowerRight().getX() + deltaX <= aSprite.getHitBox().getLowerRight().getX() &&
		 this.hitbox.getLowerRight().getY() + deltaY >= aSprite.getHitBox().getLowerLeft().getY() &&
		 this.hitbox.getLowerRight().getY() + deltaY <= aSprite.getHitBox().getUpperLeft().getY())
	    return true;
	else if (this.hitbox.getUpperLeft().getX() + deltaX >= aSprite.getHitBox().getLowerLeft().getX() &&
		 this.hitbox.getUpperLeft().getX() + deltaX <= aSprite.getHitBox().getLowerRight().getX() &&
		 this.hitbox.getUpperLeft().getY() + deltaY >= aSprite.getHitBox().getLowerLeft().getY() &&
		 this.hitbox.getUpperLeft().getY() + deltaY <= aSprite.getHitBox().getUpperLeft().getY())
	    return true;
	else if (this.hitbox.getUpperRight().getX() + deltaX >= aSprite.getHitBox().getLowerLeft().getX() &&
		 this.hitbox.getUpperRight().getX() + deltaX <= aSprite.getHitBox().getLowerRight().getX() &&
		 this.hitbox.getUpperRight().getY() + deltaY >= aSprite.getHitBox().getLowerLeft().getY() &&
		 this.hitbox.getUpperRight().getY() + deltaY <= aSprite.getHitBox().getUpperLeft().getY())
	    return true;
	else
	    return false;
    }

}
