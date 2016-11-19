package com.game.mygame;

/**
 *
 * Square class that holds the RGBA, Position (x,y) of the bottom left corner, and center.  The values are used
 * by the main class to create Quads that will be used to have textures mapped to it.
 *
 */

public class Square
    {

	private float red;
	private float green;
	private float blue;
	private float alpha;
	private float posX;
	private float posY;
	private float width;
	private float height;
	private float centerX;
	private float centerY;
	private float acceleration;
	private float speed;
	private float speedX;
	private float speedY;
	
	Square()
	{
	    this.red = 0.0f;
	    this.green = 0.0f;
	    this.blue = 0.0f;
	    this.alpha = 0.0f;
	    this.posX = 0.0f;
	    this.posY = 0.0f;
	    this.width = 10.0f;
	    this.height = 10.0f;
	    this.centerX = this.posX + (this.width / 2);
	    this.centerY = this.posY + (this.height / 2);
	    this.acceleration = 1.0f;
	    this.speedX = 0.0f;
	    this.speedY = 0.0f;
	}
	
    
	Square(float red, float green, float blue, float alpha, float posX, float posY, float width, float height)
	{
	    this.red = red;
	    this.green = green;
	    this.blue = blue;
	    this.alpha = alpha;
	    this.posX = posX;
	    this.posY = posY;
	    this.width = width;
	    this.height = height;
	    this.centerX = posX + (width / 2);
	    this.centerY = posY + (height / 2);
	    this.acceleration = 1.0f;
	    this.speedX = 0.0f;
	    this.speedY = 0.0f;
	}

	public float getCenterX()
	{
	    return this.centerX;
	}

	public void setCenterX(float centerX)
	{
	    this.centerX = centerX;
	}

	public float getCenterY()
	{
	    return this.centerY;
	}

	public void setCenterY(float centerY)
	{
	    this.centerY = centerY;
	}
    
	public float getRed()
	{
	    return red;
	}

	public void setRed(float red)
	{
	    this.red = red;
	}

	public float getGreen()
	{
	    return green;
	}

	public void setGreen(float green)
	{
	    this.green = green;
	}

	public float getBlue()
	{
	    return blue;
	}

	public void setBlue(float blue)
	{
	    this.blue = blue;
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
        
	public float getWidth()
	{
	    return width;
	}

	public void setWidth(float width)
	{
	    this.width = width;
	}

	public float getHeight()
	{
	    return height;
	}

	public void setHeight(float height)
	{
	    this.height = height;
	}

	public float getAcceleration()
	{
	    return acceleration;
	}

	public float getSpeedX()
	{
	    return speedX;
	}

	public void setSpeedX(float speedX)
	{
	    this.speedX = speedX;
	}

	public float getSpeedY()
	{
	    return speedY;
	}

	public void setSpeedY(float speedY)
	{
	    this.speedY = speedY;
	}

    }
