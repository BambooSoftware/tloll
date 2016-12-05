package com.bamboo.tloll.physics;

import com.bamboo.tloll.graphics.Unit;

public final class PhysicsEngine
{

    public PhysicsEngine()
    {
	// Empty constructor.
    }

    public static void movePlayer(Unit player, float deltaX, float deltaY)
    {
	player.setPosX(player.getPosX() + isOutOfBoundsX(player, deltaX));
	player.setPosY(player.getPosY() + isOutOfBoundsY(player, deltaY));

	System.out.println("Player X: " + player.getPosX());
	System.out.println("Player Y: " + player.getPosY());
	System.out.println("Center coords: " + player.getCenterX() + "," + player.getCenterY());
    }

    // Bind the player to the X dimension of the board.
    public static float isOutOfBoundsX(Unit player, float deltaX)
    {
	// TODO(map) : Hard coded value needs to be passed at some point.
	if (player.getCenterX() + player.getWidth() / 2  + deltaX > 512)
	    {
		player.setOutOfBoundsRight(true);
		return 0.0f;
	    }
	else if (player.getCenterX() - player.getWidth() / 2 + deltaX < 0)
	    {
		player.setOutOfBoundsLeft(true);
		return 0.0f;
	    }
	player.setOutOfBoundsRight(false);
	player.setOutOfBoundsLeft(false);
	player.setCenterX(player.getCenterX() + deltaX);
	return deltaX;
    }

    // Bind the palyer to the Y dimension of the board.
    public static float isOutOfBoundsY(Unit player, float deltaY)
    {
	// TODO(map) : Hard coded value needs to be passed at some point.
	if (player.getCenterY() + player.getHeight() / 4 + deltaY > 512)
	    {
		player.setOutOfBoundsUp(true);
		return 0.0f;
	    }
	else if (player.getCenterY() - player.getHeight() / 2 + deltaY < 0)
	    {
		player.setOutOfBoundsDown(true);
		return 0.0f;
	    }
	player.setOutOfBoundsUp(false);
	player.setOutOfBoundsDown(false);
	player.setCenterY(player.getCenterY() + deltaY);
	return deltaY;
    }

        
}
