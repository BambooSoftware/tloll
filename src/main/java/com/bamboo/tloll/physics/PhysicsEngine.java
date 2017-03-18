package com.bamboo.tloll.physics;

import com.bamboo.tloll.Constants;

import com.bamboo.tloll.graphics.Unit;

import com.bamboo.tloll.graphics.structure.Scene;
import com.bamboo.tloll.graphics.structure.Tile;

public final class PhysicsEngine
{

    public PhysicsEngine()
    {
	// Empty constructor.
    }

    public static void movePlayer(Unit player, float deltaX, float deltaY, Scene currentScene)
    {

	if (!isOutOfBoundsX(player, deltaX) && isTilePassable(player, deltaX, deltaY, currentScene))
	    {
		player.setPosX(player.getPosX() + deltaX);

		if (moveInTileX(player, deltaX))
		    {
			player.setRelativeTileX(player.getRelativeTileX() + deltaX);
		    }
		else
		    {
			if (deltaX < 0.0)
			    {
				float newRelativeX = 64.0f - ((player.getRelativeTileX() + deltaX) * -1);
				player.setRelativeTileX(newRelativeX);
			    }
			else if (deltaX > 0.0)
			    {
				float newRelativeX = deltaX - (64.0f - player.getRelativeTileX());
				player.setRelativeTileX(newRelativeX);
			    }
		    }
	    }

	if (!isOutOfBoundsY(player, deltaY) && isTilePassable(player, deltaX, deltaY, currentScene))
	    {
		player.setPosY(player.getPosY() + deltaY);

		if (moveInTileY(player, deltaY))
		    {
			player.setRelativeTileY(player.getRelativeTileY() + deltaY);
		    }
		else
		    {
			if (deltaY < 0.0)
			    {
				float newRelativeY = 64.0f - ((player.getRelativeTileY() + deltaY) * -1);
				player.setRelativeTileY(newRelativeY);
			    }
			else if (deltaY > 0.0)
			    {
				float newRelativeY = deltaY - (64.0f - player.getRelativeTileY());
				player.setRelativeTileY(newRelativeY);
			    }
		    }

	    }
	
	System.out.println("Player X: " + player.getPosX());
	System.out.println("Player Y: " + player.getPosY());
	System.out.println("Center coords: " + player.getCenterX() + "," + player.getCenterY());
    }

    // Bind the player to the X dimension of the board.
    public static boolean isOutOfBoundsX(Unit player, float deltaX)
    {
	if (player.getCenterX() + player.getWidth() / 2  + deltaX > Constants.WIDTH)
	    {
		player.setOutOfBoundsRight(true);
		return true;
	    }
	else if (player.getCenterX() - player.getWidth() / 2 + deltaX < 0)
	    {
		player.setOutOfBoundsLeft(true);
		return true;
	    }
	player.setOutOfBoundsRight(false);
	player.setOutOfBoundsLeft(false);
	player.setCenterX(player.getCenterX() + deltaX);
	return false;
    }

    // Bind the player to the Y dimension of the board.
    public static boolean isOutOfBoundsY(Unit player, float deltaY)
    {
	if (player.getCenterY() + player.getHeight() / 4 + deltaY > Constants.HEIGHT)
	    {
		player.setOutOfBoundsUp(true);
		return true;
	    }
	else if (player.getCenterY() - player.getHeight() / 2 + deltaY < 0)
	    {
		player.setOutOfBoundsDown(true);
		return true;
	    }
	player.setOutOfBoundsUp(false);
	player.setOutOfBoundsDown(false);
	player.setCenterY(player.getCenterY() + deltaY);
	return false;
    }

    public static boolean moveInTileX(Unit player, float deltaX)
    {
	if (player.getRelativeTileX() + deltaX < 64.0 && player.getRelativeTileX() + deltaX > 0.0)
	    {
		return true;
	    }
	return false;
    }

    public static boolean moveInTileY(Unit player, float deltaY)
    {
	if (player.getRelativeTileY() + deltaY < 64.0  && player.getRelativeTileY() + deltaY > 0.0)
	    {
		return true;
	    }
	return false;
    }

    private static boolean isTilePassable(Unit player, float deltaX, float deltaY, Scene currentScene)
    {
	for (Tile tile : currentScene.getTileList())
	    {
		if ((player.getPosX() + deltaX) >= tile.getPosX() &&
		    (player.getPosX() + deltaX) <= (tile.getPosX() + tile.getWidth()) &&
		    (player.getPosY() + deltaY) >= tile.getPosY() &&
		    (player.getPosY() + deltaY) <= (tile.getPosY() + tile.getHeight()))
		    {
			return tile.isPassable();
		    }
	    }
	return true;
    }
        
}
