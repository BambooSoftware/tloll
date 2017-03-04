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

    public Input()
    {
	// Empty constructor.
    }

    // Bind all key presses here to do something.
    public void checkKeyPressed(long windowId, Unit player, Scene currentScene)
    {
	if (glfwGetKey(windowId, GLFW_KEY_A) == 1 && glfwGetKey(windowId, GLFW_KEY_S) == 1)
	    {
		System.out.println("A + S Combo Key");
		player.setSpeedX(player.getSpeedX() - player.getAcceleration());
		player.setSpeedY(player.getSpeedY() - player.getAcceleration());
		if (player.getSpeedX() < -10.0f)
		    {
			player.setSpeedX(-10.0f);
		    }
		if (player.getSpeedY() < -10.0f)
		    {
			player.setSpeedY(-10.0f);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_A) == 1 && glfwGetKey(windowId, GLFW_KEY_W) == 1)
	    {
		System.out.println("A + W Combo Key");
		player.setSpeedX(player.getSpeedX() - player.getAcceleration());
		player.setSpeedY(player.getSpeedY() + player.getAcceleration());
		if (player.getSpeedX() < -10.0f)
		    {
			player.setSpeedX(-10.0f);
		    }
		if (player.getSpeedY() > 10.0f)
		    {
			player.setSpeedY(10.0f);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());

	    }
	else if (glfwGetKey(windowId, GLFW_KEY_D) == 1 && glfwGetKey(windowId, GLFW_KEY_S) == 1)
	    {
		System.out.println("D + S Combo Key");
		player.setSpeedX(player.getSpeedX() + player.getAcceleration());
		player.setSpeedY(player.getSpeedY() - player.getAcceleration());
		if (player.getSpeedX() > 10.0f)
		    {
			player.setSpeedX(10.0f);
		    }
		if (player.getSpeedY() < -10.0f)
		    {
			player.setSpeedY(-10.0f);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());

	    }
	else if (glfwGetKey(windowId, GLFW_KEY_D) == 1 && glfwGetKey(windowId, GLFW_KEY_W) == 1)
	    {
		System.out.println("D + W Combo Key");
		player.setSpeedX(player.getSpeedX() + player.getAcceleration());
		player.setSpeedY(player.getSpeedY() + player.getAcceleration());
		if (player.getSpeedX() > 10.0f)
		    {
			player.setSpeedX(10.0f);
		    }
		if (player.getSpeedY() > 10.0f)
		    {
			player.setSpeedY(10.0f);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), player.getSpeedY());

	    }
	else if (glfwGetKey(windowId, GLFW_KEY_A) == 1)
	    {
		System.out.println("A Key");
		player.setSpeedX(player.getSpeedX() - player.getAcceleration());
		if (player.getSpeedX() < -10.0f)
		    {
			player.setSpeedX(-10.0f);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), 0.0f);
		player.setDirection(Direction.LEFT);
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_D) == 1)
	    {
		System.out.println("D Key");
		player.setSpeedX(player.getSpeedX() + player.getAcceleration());
		if (player.getSpeedX() > 10.0f)
		    {
			player.setSpeedX(10.0f);
		    }
		PhysicsEngine.movePlayer(player, player.getSpeedX(), 0.0f);
		player.setDirection(Direction.RIGHT);
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_S) == 1)
	    {
		System.out.println("S Key");
		player.setSpeedY(player.getSpeedY() - player.getAcceleration());
		if (player.getSpeedY() < -10.0f)
		    {
			player.setSpeedY(-10.0f);
		    }
		PhysicsEngine.movePlayer(player, 0.0f, player.getSpeedY());
			player.setDirection(Direction.DOWN);
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_W) == 1)
	    {
		System.out.println("W Key");
		player.setSpeedY(player.getSpeedY() + player.getAcceleration());
		if (player.getSpeedY() > 10.0f)
		    {
			player.setSpeedY(10.0f);
		    }
		PhysicsEngine.movePlayer(player, 0.0f, player.getSpeedY());
		player.setDirection(Direction.UP);
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

    public void checkKeyRelease(long windowId, Unit player)
    {
	// Check if horzontal keys are not being pressed.
	// Not pressed, then we check for horizontal speed.
	// Horzontal speed not 0.0, apply decceleartaion.
	if (glfwGetKey(windowId, GLFW_KEY_A) == 0 && glfwGetKey(windowId, GLFW_KEY_D) == 0)
	    {
		if (player.getSpeedX() < 0.0f)
		    {
			player.setSpeedX(player.getSpeedX() + player.getAcceleration());
			PhysicsEngine.movePlayer(player, player.getSpeedX(), 0.0f);
		    }
		else if (player.getSpeedX() > 0.0f)
		    {
			player.setSpeedX(player.getSpeedX() - player.getAcceleration());
			PhysicsEngine.movePlayer(player, player.getSpeedX(), 0.0f);
		    }
	    }

	// Vertical decceleartion.
	if (glfwGetKey(windowId, GLFW_KEY_W) == 0 && glfwGetKey(windowId, GLFW_KEY_S) == 0)
	    {
		if (player.getSpeedY() < 0.0f)
		    {
			player.setSpeedY(player.getSpeedY() + player.getAcceleration());
			PhysicsEngine.movePlayer(player, 0.0f, player.getSpeedY());
		    }
		else if (player.getSpeedY() > 0.0f)
		    {
			player.setSpeedY(player.getSpeedY() - player.getAcceleration());
			PhysicsEngine.movePlayer(player, 0.0f, player.getSpeedY());
		    }
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


	private void updatePlayerTileId(Unit player, Scene currentScene) {
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
