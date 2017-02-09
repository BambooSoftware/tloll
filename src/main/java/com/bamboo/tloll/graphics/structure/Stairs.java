package com.bamboo.tloll.graphics;

import com.bamboo.tloll.graphics.structure.Tile;

/**
 * Class to represent stairs.
 */

public class Stairs extends Tile
{

    // Direction to represent which way the stairs face.
    // 1 = North
    // 2 = East
    // 3 = South
    // 4 = West
    private int stairDirection;

    public Stairs()
    {
	super();
	this.stairDirection = 1;
    }

    public Stairs(float posX,
		  float posY,
		  float width,
		  float height,
		  boolean passable,
		  int direction,
		  int tileNum,
		  int stairDirection)
    {
	super(posX, posY, width, height, passable, direction, tileNum);
	this.stairDirection = stairDirection;
    }
    
}
