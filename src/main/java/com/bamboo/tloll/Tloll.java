package com.bamboo.tloll;

import org.lwjgl.opengl.*;
import org.lwjgl.system.Platform;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.BufferUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.Renderer;
import com.bamboo.tloll.input.Input;
import com.bamboo.tloll.behaviors.BaseBehaviors;

public class Tloll
{

    private long windowId;
    private static int WIDTH = 512;
    private static int HEIGHT = 512;
    private static String TITLE = "The Legend of Ling Ling";
    private static boolean isRunning = true;
    private static int frameSkip = 2;
    private static String currentDir = System.getProperty("user.dir");

    // Game initializer.
    public void initializeGame()
    {
	System.out.println("Game is being initialized");
    }

    // Main method to run.
    public static void main(String[] args)
    {

	Tloll tloll = new Tloll();
	GraphicsUtil gu = new GraphicsUtil();
	Input in = new Input();
	
	gu.initializeGL();
	glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
	tloll.windowId = gu.createWindow(WIDTH, HEIGHT, TITLE);
	glfwMakeContextCurrent(tloll.windowId);
	glfwSwapInterval(1);
	glfwShowWindow(tloll.windowId);

	GL.createCapabilities(); // Creates bindings for OpenGL to be used.

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0, 500, 0, 500, 1, -1);
	glMatrixMode(GL_MODELVIEW);
	glEnable(GL_TEXTURE_2D);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	// Sample squares that probably won't stick around.
        Unit player = new Unit(256.0f, 256.0f, 68, 100, 1.0f, 0.0f, 0.0f, 0.0f);
	Unit enemy = new Unit(0.0f, 200.0f, 128, 128, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit enemySprite = new Unit(0.0f, 350.0f, 108, 140, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit background1 = new Unit(0.0f, 0.0f, 512, 512, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit lingling = new Unit(100.0f, 100.0f, 32, 32, 0.0f, 0.0f, 0.0f, 0.0f);
	
	player.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/player.png"));
	enemy.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/enemy.png"));
	enemy.addBufferToMap(1, gu.loadTexture(currentDir + "/Assets/Images/enemy_left.png"));
	enemySprite.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/SpriteSheet.png"));
	background1.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Grass_Tree_Square/Grass__Tree_LowerLeft_512x512.PNG"));
	background1.addBufferToMap(1, gu.loadTexture(currentDir + "/Assets/Map/Grass_Tree_Square/Grass__Tree_LowerRight_512x512.PNG"));
	background1.addBufferToMap(2, gu.loadTexture(currentDir + "/Assets/Map/Grass_Tree_Square/Grass__Tree_UpperLeft_512x512.PNG"));
	background1.addBufferToMap(3, gu.loadTexture(currentDir + "/Assets/Map/Grass_Tree_Square/Grass__Tree_UpperRight_512x512.PNG"));
	lingling.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Actors/panda_f_base.png"));
	
	int backgroundId = 0;

	System.out.println("Player x, width, center: " + player.getPosX() + " " + player.getWidth() + " " + player.getCenterX());
	System.out.println("Player y, height, center: " + player.getPosY() + " " + player.getHeight() + " " + player.getCenterY());
	
	while (isRunning)
	    {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the frame buffer.

		// Draw oversized characters.
		Renderer.drawSprite(background1, backgroundId);
		Renderer.drawSprite(player, 0);

		if (backgroundId == 1)
		    {
			if (enemy.getRight())
			    {
				Renderer.drawSprite(enemy, 0);
			    }
			else
			    {
				Renderer.drawSprite(enemy, 1);
			    }
		    }

		if (backgroundId == 2)
		    {
			if (enemySprite.getRight())
			    {
				Renderer.drawSpriteAnimation(enemySprite, 0, 8, 0.125f, 0.0f, 0.5f, 864, 280);
				if (frameSkip < 0)
				    {
					enemySprite.setAnimatedSpriteNumber(enemySprite.getAnimatedSpriteNumber() + 1);
					frameSkip = 2;
				    }
				frameSkip--;
			    }
			else
			    {
				Renderer.drawSpriteAnimation(enemySprite, 0, 8, 0.125f, 0.5f, 1.0f, 864, 280);
				if (frameSkip < 0)
				    {
					enemySprite.setAnimatedSpriteNumber(enemySprite.getAnimatedSpriteNumber() - 1);
					frameSkip = 2;
				    }
				frameSkip--;
			    }
		    }

		if (backgroundId == 3)
		    {
			Renderer.drawSpriteAnimation(lingling, 0, 4, 0.25f, 0.0f, 0.25f, 128, 128);
			if (frameSkip < 0)
			    {
				lingling.setAnimatedSpriteNumber(lingling.getAnimatedSpriteNumber() + 1);
				frameSkip = 2;
			    }
			frameSkip--;
		    }
		
		// Set enemies to patrol.
		BaseBehaviors.patrolUnitLeftRight(enemy, 10.0f);
		BaseBehaviors.patrolUnitLeftRight(enemySprite, 5.0f);

		// Set up keyboard controls.
		in.checkKeyPressed(tloll.windowId, player);
		in.checkKeyRelease(tloll.windowId, player);
		isRunning = in.bindEscape(tloll.windowId);

		if(player.getOutOfBoundsRight() && backgroundId == 0)
		    {
			backgroundId = 1;
			player.setPosX(0.0f);
			player.setCenterX(player.getWidth() / 2);
		    }
		else if (player.getOutOfBoundsLeft() && backgroundId == 1)
		    {
			backgroundId = 0;
			player.setPosX(512 - player.getWidth());
			player.setCenterX(512 - (player.getWidth() / 2));
		    }
		else if (player.getOutOfBoundsRight() && backgroundId == 2)
		    {
			backgroundId = 3;
			player.setPosX(0.0f);
			player.setCenterX(player.getWidth() / 2);
		    }
		else if (player.getOutOfBoundsLeft() && backgroundId == 3)
		    {
			backgroundId = 2;
			player.setPosX(512 - player.getWidth());
			player.setCenterX(512 - (player.getWidth() / 2));
		    }
		else if (player.getOutOfBoundsUp() && backgroundId == 0)
		    {
			backgroundId = 2;
			player.setPosY(0.0f);
			player.setCenterY(player.getHeight() / 2);
		    }
		else if (player.getOutOfBoundsDown() && backgroundId == 2)
		    {
			backgroundId = 0;
			player.setPosY(512 - player.getHeight());
			player.setCenterY(512 - (player.getHeight() / 2));
		    }
		else if (player.getOutOfBoundsUp() && backgroundId == 1)
		    {
			backgroundId = 3;
			player.setPosY(0.0f);
			player.setCenterY(player.getHeight() / 2);
		    }
		else if (player.getOutOfBoundsDown() && backgroundId == 3)
		    {
			backgroundId = 1;
			player.setPosY(512 - player.getHeight());
			player.setCenterY(512 - (player.getHeight() / 2));
		    }
		
		glfwSwapBuffers(tloll.windowId); // Swaps buffers that will be drawn.

		glfwPollEvents(); // Continuosuly polls.

		// Check window close was called.
		if (glfwWindowShouldClose(tloll.windowId))
		    {
			isRunning = false;
		    }
	    }
    }
}
