package com.bamboo.tloll.input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.bamboo.tloll.graphics.Direction;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.physics.PhysicsEngine;

import com.bamboo.tloll.graphics.structure.Scene;
import com.bamboo.tloll.graphics.structure.Tile;

public class Input
{

    // NOTE(map) : These speeds are based on average human running speed in m/s.  This also assumes
    // that 1 pixel is equivalent to 1 meter game space.
    float maxSpeedX = 7.0f;
    float minSpeedX = -7.0f;
    float maxSpeedY = 7.0f;
    float minSpeedY = -7.0f;
    
    public Input()
    {
	// Empty constructor.
    }

    // Bind all key presses here to do something.
    public void checkKeyPressed(long windowId, Unit player, Scene currentScene)
    {
	// TODO(map) : This will be added back in once we have the sprites for the necessary diagonal
	// movements.
	/*
	if (glfwGetKey(windowId, GLFW_KEY_A) == 1 && glfwGetKey(windowId, GLFW_KEY_S) == 1)
	    {
		System.out.println("A + S Combo Key");
		player.setSpeedX(player.getSpeedX() - player.getAcceleration());
		player.setSpeedY(player.getSpeedY() - player.getAcceleration());
		if (player.getSpeedX() < minSpeedX)
		    {
			player.setSpeedX(minSpeedX);
		    }
		if (player.getSpeedY() < minSpeedY)
		    {
			player.setSpeedY(minSpeedY);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_A) == 1 && glfwGetKey(windowId, GLFW_KEY_W) == 1)
	    {
		System.out.println("A + W Combo Key");
		player.setSpeedX(player.getSpeedX() - player.getAcceleration());
		player.setSpeedY(player.getSpeedY() + player.getAcceleration());
		if (player.getSpeedX() < minSpeedX)
		    {
			player.setSpeedX(minSpeedX);
		    }
		if (player.getSpeedY() > maxSpeedY)
		    {
			player.setSpeedY(maxSpeedY);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());

	    }
	else if (glfwGetKey(windowId, GLFW_KEY_D) == 1 && glfwGetKey(windowId, GLFW_KEY_S) == 1)
	    {
		System.out.println("D + S Combo Key");
		player.setSpeedX(player.getSpeedX() + player.getAcceleration());
		player.setSpeedY(player.getSpeedY() - player.getAcceleration());
		if (player.getSpeedX() > maxSpeedX)
		    {
			player.setSpeedX(maxSpeedX);
		    }
		if (player.getSpeedY() < minSpeedY)
		    {
			player.setSpeedY(minSpeedY);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());

	    }
	else if (glfwGetKey(windowId, GLFW_KEY_D) == 1 && glfwGetKey(windowId, GLFW_KEY_W) == 1)
	    {
		System.out.println("D + W Combo Key");
		player.setSpeedX(player.getSpeedX() + player.getAcceleration());
		player.setSpeedY(player.getSpeedY() + player.getAcceleration());
		if (player.getSpeedX() > maxSpeedX)
		    {
			player.setSpeedX(maxSpeedX);
		    }
		if (player.getSpeedY() > maxSpeedY)
		    {
			player.setSpeedY(maxSpeedY);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());

	    }
	*/
	if (glfwGetKey(windowId, GLFW_KEY_A) == 1)
	    {
		System.out.println("A Key");
		player.setSpeedX(player.getSpeedX() - player.getAcceleration());
		System.out.println("Player's acceleration: " + (player.getAcceleration() * -1));
		System.out.println("Players speed: " + player.getSpeedX());
		if (player.getSpeedX() < minSpeedX)
		    {
			player.setSpeedX(minSpeedX);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), currentScene);
		player.setDirection(Direction.LEFT);
		if (player.getFrameSkip() < 0)
		    {
			player.setColNumber(player.getColNumber() + 1);
			if (player.getColNumber() > 4)
			    {
				player.setColNumber(1);
			    }
		    }
		else
		    {
			player.setFrameSkip(player.getFrameSkip() - 1);
		    }
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_D) == 1)
	    {
		System.out.println("D Key");
		player.setSpeedX(player.getSpeedX() + player.getAcceleration());
		if (player.getSpeedX() > maxSpeedX)
		    {
			player.setSpeedX(maxSpeedX);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), currentScene);
		player.setDirection(Direction.RIGHT);
		if (player.getFrameSkip() < 0)
		    {
			player.setColNumber(player.getColNumber() + 1);
			if (player.getColNumber() > 4)
			    {
				player.setColNumber(1);
			    }
		    }
		else
		    {
			player.setFrameSkip(player.getFrameSkip() - 1);
		    }
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_S) == 1)
	    {
		System.out.println("S Key");
		player.setSpeedY(player.getSpeedY() - player.getAcceleration());
		if (player.getSpeedY() < minSpeedY)
		    {
			player.setSpeedY(minSpeedY);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), currentScene);
		player.setDirection(Direction.DOWN);
		if (player.getFrameSkip() < 0)
		    {
			player.setColNumber(player.getColNumber() + 1);
			if (player.getColNumber() > 4)
			    {
				player.setColNumber(1);
			    }
		    }
		else
		    {
			player.setFrameSkip(player.getFrameSkip() - 1);
		    }
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_W) == 1)
	    {
		System.out.println("W Key");
		player.setSpeedY(player.getSpeedY() + player.getAcceleration());
		if (player.getSpeedY() > maxSpeedY)
		    {
			player.setSpeedY(maxSpeedY);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), currentScene);
		player.setDirection(Direction.UP);
		if (player.getFrameSkip() < 0)
		    {
			player.setColNumber(player.getColNumber() + 1);
			if (player.getColNumber() > 4)
			    {
				player.setColNumber(1);
			    }
		    }
		else
		    {
			player.setFrameSkip(player.getFrameSkip() - 1);
		    }
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_SPACE) == 1  && glfwGetKey(windowId, GLFW_KEY_LEFT_SHIFT) == 1 && !player.isAttackingRanged())
	    {
		player.setIsAttackingRanged(true);
		System.out.println("Ranged Attacked");
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_SPACE) == 1 && glfwGetKey(windowId, GLFW_KEY_LEFT_SHIFT) == 0 && !player.isAttackingMelee())
	    {
		player.setIsAttackingMelee(true);
		System.out.println("Melee Attack");
	    }

	//TODO: Make me pretty -- really the whole class 

	updatePlayerTileId(player, currentScene);

    }

    public void checkKeyRelease(long windowId, Unit player, Scene currentScene)
    {
	// Check if horzontal keys are not being pressed.
	// Not pressed, then we check for horizontal speed.
	// Horzontal speed not 0.0, apply decceleartaion.
	if (glfwGetKey(windowId, GLFW_KEY_A) == 0 && glfwGetKey(windowId, GLFW_KEY_D) == 0)
	    {
		// NOTE(map) : This is only temporary to stop movementin the diagonal.
		player.setSpeedX(0.0f);
		// TODO(map) : We will put this back in when we handle diagonal stuff.
		/*
		if (player.getSpeedX() < 0.0f)
		    {
			player.setSpeedX(player.getSpeedX() + player.getAcceleration());
			PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), currentScene);
			player.setColNumber(player.getColNumber() + 1);
			if (player.getColNumber() > 4)
			    {
				player.setColNumber(1);
			    }
		    }
		else if (player.getSpeedX() > 0.0f)
		    {
			player.setSpeedX(player.getSpeedX() - player.getAcceleration());
			PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), currentScene);
			player.setColNumber(player.getColNumber() + 1);
			if (player.getColNumber() > 4)
			    {
				player.setColNumber(1);
			    }
		    }
		*/
	    }

	// Vertical decceleartion.
	if (glfwGetKey(windowId, GLFW_KEY_W) == 0 && glfwGetKey(windowId, GLFW_KEY_S) == 0)
	    {
		// NOTE(map) : This is only temporary to stop the movement in the diagonal.
		player.setSpeedY(0.0f);
		// TODO(map) : We will pu this back in when we handle diagonal stuff.
		/*
		if (player.getSpeedY() < 0.0f)
		    {
			player.setSpeedY(player.getSpeedY() + player.getAcceleration());
			PhysicsEngine.movePlayer(player, player.getSpeed(), player.getSpeedY(), currentScene);
			player.setColNumber(player.getColNumber() + 1);
			if (player.getColNumber() > 4)
			    {
				player.setColNumber(1);
			    }
		    }
		else if (player.getSpeedY() > 0.0f)
		    {
			player.setSpeedY(player.getSpeedY() - player.getAcceleration());
			PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY(), currentScene);
			player.setColNumber(player.getColNumber() + 1);
			if (player.getColNumber() > 4)
			    {
				player.setColNumber(1);
			    }
		    }
		*/
	    }

	if (player.getSpeedY() == 0.0f && player.getSpeedX() == 0.0f)
	    {
		player.setColNumber(1);
	    }
    }

    public void bindResetKey(long windowId, Unit enemyTarget)
    {
	// NOTE(map) : This should reset the state of the entire game to a previous point.
	// It is currently only resetting the HP of a single enemy for testing purposes.
	if (glfwGetKey(windowId, GLFW_KEY_R) == 1)
	    {
		System.out.println("Resetting your game state.");
		enemyTarget.setHitPoints(3);
	    }
    }
    
    // Bind key to pass back close window.
    public boolean bindEscape(long windowId)
    {
	if (glfwGetKey(windowId, GLFW_KEY_ESCAPE) == 1)
	    {
		return false;
	    }
	return true;
    }

    // Binding the "P" key to the debug information method
    public void bindDebugKey(long windowId,
			     Unit player,
			     Scene currentScene)
    {
	if (glfwGetKey(windowId, GLFW_KEY_P) == 1)
	    {
		System.out.println("~~~~ Printing debug info ~~~~");
		printPositionalInformation(player, currentScene);
	    }
    }

    // NOTE: This method is for debugging purposes only.  It is bound to the "P" key for printing
    // and works displaying the position of the player in relative to the tile as well as the
    // scene.  It also determines if the current tile is passable.

    // This method can have parts taken and repurposed if they are needed.
    public void printPositionalInformation(Unit player, Scene currentScene)
    {
	for (Tile tile : currentScene.getTileList())
	    {
		if (player.getPosX() >= tile.getPosX() &&
		    player.getPosX() <= (tile.getPosX() + tile.getWidth()) &&
		    player.getPosY() >= tile.getPosY() &&
		    player.getPosY() <= (tile.getPosY() + tile.getHeight())) {
		    System.out.println("Player in tile number: " + tile.getTileId());
		    System.out.println("Tile passability = " + tile.isPassable());
		    player.setCurrentTileId(tile.getTileId());
		}
	    }
	System.out.println("Player scene position (X,Y) : " + player.getPosX() + ", " + player.getPosY());
	System.out.println("Player tile position (X,Y) : " + player.getRelativeTileX() + ", " + player.getRelativeTileY());

    }


    private void updatePlayerTileId(Unit player, Scene currentScene)
    {
	for (Tile tile : currentScene.getTileList()) {
	    if (player.getPosX() >= tile.getPosX() &&
		player.getPosX() <= (tile.getPosX() + tile.getWidth()) &&
		player.getPosY() >= tile.getPosY() &&
		player.getPosY() <= (tile.getPosY() + tile.getHeight())) {
		player.setCurrentTileId(tile.getTileId());
		break;
	    }
	}
    }
    
}
