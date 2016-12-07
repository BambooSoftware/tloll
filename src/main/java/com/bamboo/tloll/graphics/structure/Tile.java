package com.bamboo.tloll.graphics.structure;

import com.bamboo.tloll.graphics.Sprite;

public class Tile extends Sprite
{

    //TODO: this should probably be expanded upon to be a more complex structure maybe have 4 ?
    //TODO: PASSBALE TOP LEFT RIGHT BOTTOM so we can have one way terrain.
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
