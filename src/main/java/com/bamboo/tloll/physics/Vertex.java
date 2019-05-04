/**
 * @author: MAPlatt
 * @date: 2019-04-30
 * @license:
 */

package com.bamboo.tloll.physics;

public class Vertex
{

    private float x, y;

    public Vertex()
    {
	x = 0.0f;
	y = 0.0f;
    }

    public Vertex(float x, float y)
    {
	this.x = x;
	this.y = y;
    }

    public float getX()
    {
	return x;
    }

    public void setX(float x)
    {
	this.x = x;
    }

    public float getY()
    {
	return y;
    }

    public void setY(float y)
    {
	this.y = y;
    }}
