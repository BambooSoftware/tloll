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
    // 10 = Stairs
    // 11 = Dirt
    private int direction;

    private int tileId;

    private boolean exit;
    
    public Tile()
    {
	super();
    }

    // NOTE(map) : There are two constructors, one that take posX/Y and one that doesn't. If no posX/Y
    // is provided, it calculates.  Currently there is no posX/Y in JSON.  They are optional values.
    public Tile(float posX,
		float posY,
		float width,
		float height,
		boolean passable,
		int direction,
		int tileId,
        boolean exit)
    {
	super(posX, posY, width, height);
	this.passable = passable;
	this.direction = direction;
	this.tileId = tileId;
    this.exit = exit;
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

    public int getTileId()
    {
	return tileId;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

}
