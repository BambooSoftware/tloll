package com.bamboo.tloll.graphics.structure;

import com.bamboo.tloll.graphics.Sprite;

public class Tile extends Sprite
{

    private boolean passable;
    // TODO(map) : We will need to change this at some point.
    // This is a value to represent which side the piece is on.  For now the following applies:
    // 0 = Grass
    // 1 = Left
    // 2 = Top
    // 3 = Right
    // 4 = Bottom
    // 5 = Water
    // 6 = Lower Left Corner
    // 7 = Upper Left Corner
    // 8 = Upper Right Corner
    // 9 = Lower Right Corner
    private int direction;

    private int tileNum;
    
    public Tile()
    {
	super();
    }

    public Tile(float posX,
		float posY,
		float width,
		float height,
		boolean passable,
		int direction,
		int tileNum)
    {
	super(posX, posY, width, height);
	this.passable = passable;
	this.direction = direction;
	this.tileNum = tileNum;
    }

    public boolean isPassable()
    {
	return passable;
    }
    public void setPassable(boolean passable)
    {
	this.passable = passable;
    }

    public int getDirection()
    {
	return direction;
    }
    public void setDirection(int direction)
    {
	this.direction = direction;
    }

    public int getTileNum()
    {
	return tileNum;
    }

}
