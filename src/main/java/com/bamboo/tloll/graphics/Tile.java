package com.bamboo.tloll.graphics;

public class Tile extends Sprite
{

    private boolean passable;
    
    public Tile()
    {
	super();
    }

    public Tile(float posX,
		float posY,
		float width,
		float height,
		boolean passable)
    {
	super(posX, posY, width, height);
	this.passable = passable;
    }

    public boolean isPassable()
    {
	return passable;
    }
    public void setPassable(boolean passable)
    {
	this.passable = passable;
    }

}
