package com.bamboo.tloll.physics;

public class Vector3
{
    private float xComponent;
    private float yComponent;
    private float zComponent;

    public Vector3()
    {
	this.xComponent = 0.0f;
	this.yComponent = 0.0f;
	this.zComponent = 0.0f;
    }
    
    public Vector3(float xComponent, float yComponent, float zComponent)
    {
	this.xComponent = xComponent;
	this.yComponent = yComponent;
	this.zComponent = zComponent;
    }

    // Getters and setters.
    public float getXComponent()
    {
	return xComponent;
    }

    public void setXComponent(float xComponent)
    {
	this.xComponent = xComponent;
    }

    public float getYComponent()
    {
	return yComponent;
    }

    public void setYComponent(float yComponent)
    {
	this.yComponent = yComponent;
    }

    public float getZComponent()
    {
	return zComponent;
    }

    public void setZComponent(float zComponent)
    {
	this.zComponent = zComponent;
    }

    /**
     * Vector Math funcitons.
     */
    public Vector3 add(Vector3 vector)
    {
	return new Vector3(this.xComponent + vector.getXComponent(), this.yComponent + vector.getYComponent(), this.zComponent + vector.getZComponent());
    }

    public Vector3 subtract(Vector3 vector)
    {
	return new Vector3(this.xComponent - vector.getXComponent(), this.yComponent - vector.getYComponent(), this.zComponent - vector.getZComponent());
    }

    // Negate function for if friction is needed to be applied in exact opposite direction.
    public Vector3 negate()
    {
	return new Vector3(this.xComponent * -1, this.yComponent * -1, this.zComponent * -1);
    }

    // NOTE(map) : We may need more math here but I'm not sure yet.
    
}
