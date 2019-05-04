/**
 * @author: MAPlatt
 * @date: 2019-04-30
 * @license:
 */

package com.bamboo.tloll.physics;

import com.bamboo.tloll.physics.Vertex;

public class HitBox
{

    private Vertex lowerLeft, lowerRight, upperLeft, upperRight;
    
    public HitBox()
    {
	this.lowerLeft = new Vertex();
	this.lowerRight = new Vertex();
	this.upperLeft = new Vertex();
	this.upperRight = new Vertex();
    }

    public HitBox(Vertex lowerLeft, Vertex lowerRight, Vertex upperLeft, Vertex upperRight)
    {
	this.lowerLeft = lowerLeft;
	this.lowerRight = lowerRight;
	this.upperLeft = upperLeft;
	this.upperRight = upperRight;
    }

    public Vertex getLowerLeft()
    {
	return lowerLeft;
    }

    public Vertex getLowerRight()
    {
	return lowerRight;
    }

    public Vertex getUpperLeft()
    {
	return upperLeft;
    }

    public Vertex getUpperRight()
    {
	return upperRight;
    }
}
