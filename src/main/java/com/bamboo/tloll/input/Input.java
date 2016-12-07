package com.bamboo.tloll.input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.physics.PhysicsEngine;

public class Input
{

    public Input()
    {
	// Empty constructor.
    }

    // Bind all key presses here to do something.
    public void checkKeyPressed(long windowId, Unit player)
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
	    }
	else if (glfwGetKey(windowId, GLFW_KEY_SPACE) == 1)
	    {
		System.out.println("Attack");
	    }
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
    
    public boolean bindEscape(long windowId)
    {
	if (glfwGetKey(windowId, GLFW_KEY_ESCAPE) == 1)
	    {
		return false;
	    }
	return true;
    }
    
}
