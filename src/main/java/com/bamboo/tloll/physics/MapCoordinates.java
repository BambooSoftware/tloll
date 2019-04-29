/**
 * @author: MAPlatt
 * @date: 2019-04-22
 * @license:
 */
package com.bamboo.tloll.physics;

public class MapCoordinates
{
    private float posX;
    private float posY;
    private float posZ;

    public MapCoordinates()
    {
	
    }

    public MapCoordinates(float posX, float posY, float posZ)
    {
	this.posX = posX;
	this.posY = posY;
	this.posZ = posZ;
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
        
}
