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
    private static int animateSpriteFrame = 1;
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
	Unit lingling = new Unit(50.0f, 50.0f, 32, 32, 1.0f, 0.0f, 0.0f, 0.0f);
	
	player.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/player.png"));
	enemy.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/enemy.png"));
	enemy.addBufferToMap(1, gu.loadTexture(currentDir + "/Assets/Images/enemy_left.png"));
	enemySprite.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/SpriteSheet.png"));
	background1.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Grass_Tree_Square/Grass__Tree_LowerLeft_512x512.png"));
	lingling.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Actors/panda_f_base.png"));
	
	while (isRunning)
	    {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the frame buffer.

		// Draw oversized characters.
		Renderer.drawSprite(background1, 0);
		Renderer.drawSprite(player, 0);
		if (enemy.getRight())
		    {
			Renderer.drawSprite(enemy, 0);
		    }
		else
		    {
			Renderer.drawSprite(enemy, 1);
		    }

		Renderer.drawSpriteAnimation(enemySprite, 0, 8);
		//Renderer.drawSpriteAnimation(lingling, 0, 4);
		if (frameSkip < 0)
		    {
			
			enemySprite.setAnimatedSpriteNumber(enemySprite.getAnimatedSpriteNumber() + 1);
			//lingling.setAnimatedSpriteNumber(lingling.getAnimatedSpriteNumber() + 1);
			frameSkip = 2;
		    }
		frameSkip--;
		
		// Set enemies to patrol.
		BaseBehaviors.patrolUnitLeftRight(enemy, 10.0f);

		// Set up keyboard controls.
		in.checkKeyPressed(tloll.windowId, player);
		in.checkKeyRelease(tloll.windowId, player);
		isRunning = in.bindEscape(tloll.windowId);

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
