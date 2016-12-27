package com.bamboo.tloll;

import com.bamboo.tloll.graphics.structure.Tile;
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
import java.util.List;

import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.Renderer;
import com.bamboo.tloll.graphics.MapCreator;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.Scene;

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
	MapCreator mc = new MapCreator();
	
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
	//glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE );
	//glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE );
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	// Sample squares that probably won't stick around.
        Unit player = new Unit(256.0f, 256.0f, 68, 100, 1.0f, 0.0f, 0.0f, 0.0f);
	Unit enemy = new Unit(0.0f, 200.0f, 128, 128, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit enemySprite = new Unit(0.0f, 350.0f, 108, 140, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit lingling = new Unit(100.0f, 100.0f, 32, 32, 0.0f, 0.0f, 0.0f, 0.0f);

	player.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/player.png"));
	enemy.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/enemy.png"));
	enemy.addBufferToMap(1, gu.loadTexture(currentDir + "/Assets/Images/enemy_left.png"));
	enemySprite.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/SpriteSheet.png"));
	lingling.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Actors/panda_f_base.png"));
	
	int backgroundId = 0;

	// TODO(map) : Let's get this working by drawing alternating tiles between water and grass
	// from the image assets file.  Once we are doing that, we will change to load different parts
	// of the actual map instead.  Normalization needs to happen for this to occur.
	Scene lowerLeft = new Scene(1, 1);
	Scene upperLeft = new Scene(2, 2);
	Scene lowerRight = new Scene(3, 3);
	Scene upperRight = new Scene(4, 4);
	Scene straightUpDown = new Scene(5, 5);
	Scene straightLeftRight = new Scene(6, 6);

	Renderer.loadTileBuffers(lowerLeft, gu, currentDir);
	Renderer.loadTileBuffers(upperLeft, gu, currentDir);
	Renderer.loadTileBuffers(lowerRight, gu, currentDir);
	Renderer.loadTileBuffers(upperRight, gu, currentDir);
	Renderer.loadTileBuffers(straightUpDown, gu, currentDir);
	Renderer.loadTileBuffers(straightLeftRight, gu, currentDir);
	
	while (isRunning)
	    {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the frame buffer.

		Renderer.drawScene(lowerLeft);

		checkBackgroundStuff(backgroundId,
				     player,
				     enemy,
				     enemySprite,
				     lingling,
				     lowerLeft,
				     lowerRight,
				     upperLeft,
				     upperRight,
				     straightLeftRight,
				     straightUpDown
				     );

		// Draw caveman sprite.
		Renderer.drawSprite(player, 0);

		// Set up keyboard controls.
		in.checkKeyPressed(tloll.windowId, player);
		in.checkKeyRelease(tloll.windowId, player);
		in.bindDebugKey(tloll.windowId, player, lowerLeft, upperLeft, lowerRight, upperRight, straightUpDown, straightLeftRight);

		isRunning = in.bindEscape(tloll.windowId);

		backgroundId = checkPlayerTransition(player, backgroundId);
				
		glfwSwapBuffers(tloll.windowId); // Swaps buffers that will be drawn.

		glfwPollEvents(); // Continuosuly polls.

		// Check window close was called.
		if (glfwWindowShouldClose(tloll.windowId))
		    {
			isRunning = false;
		    }
	    }
    }

    /**
     * 0 = Lower Left
     * 1 = Straight Left/Right Bottom
     * 2 = Lower Right
     * 3 = Straight Up/Down Left
     * 4 = Upper Right 
     * 5 = Straight Left/Right Top
     * 6 = Upper Left
     * 7 = Straight Up/Down Right
     */
    public static int checkPlayerTransition(Unit player,
					    int backgroundId)
    {
	// Go right from lower left.
	if (player.getOutOfBoundsRight() && backgroundId == 0)
	    {
		player.setPosX(0.0f);
		player.setCenterX(player.getWidth() / 2);
		return 1;
	    }
	// Go up from lower left.
	else if (player.getOutOfBoundsUp() && backgroundId == 0)
	    {
		player.setPosY(0.0f);
		player.setCenterY(player.getHeight() / 2);
		return 3;
	    }
	// Go left from middle bottom.
	else if (player.getOutOfBoundsLeft() && backgroundId == 1)
	    {
		player.setPosX(512 - player.getWidth());
		player.setCenterX(512 - (player.getWidth() / 2));
		return 0;
	    }
	// Go down from middle left.
	else if (player.getOutOfBoundsDown() && backgroundId == 3)
	    {
		player.setPosY(512 - player.getHeight());
		player.setCenterY(512 - (player.getHeight() / 2));
		return 0;
	    }
	// Go right from middle bottom.
	else if (player.getOutOfBoundsRight() && backgroundId == 1)
	    {
	        player.setPosX(0.0f);
		player.setCenterX(player.getWidth() / 2);
		return 2;
	    }
	// Go up from lower right.
	else if (player.getOutOfBoundsUp() && backgroundId == 2)
	    {
		player.setPosY(0.0f);
		player.setCenterY(player.getHeight() / 2);
		return 7;
	    }
	// Go down from middle right.
	else if (player.getOutOfBoundsDown() && backgroundId == 7)
	    {
		player.setPosY(512 - player.getHeight());
		player.setCenterY(512 - (player.getHeight() / 2));
		return 2;
	    }
	// Go left from lower right.
	else if (player.getOutOfBoundsLeft() && backgroundId == 2)
	    {
		player.setPosX(512 - player.getWidth());
		player.setCenterX(512 - (player.getWidth() / 2));
		return 1;
	    }
	// Go up from middle right.
	else if (player.getOutOfBoundsUp() && backgroundId == 7)
	    {
		player.setPosY(0.0f);
		player.setCenterY(player.getHeight() / 2);
		return 4;
	    }
	// Go down from upper right.
	else if (player.getOutOfBoundsDown() && backgroundId == 4)
	    {
		player.setPosY(512 - player.getHeight());
		player.setCenterY(512 - (player.getHeight() / 2));
		return 7;
	    }
	// Go left from upper right.
	else if (player.getOutOfBoundsLeft() && backgroundId == 4)
	    {
		player.setPosX(512 - player.getWidth());
		player.setCenterX(512 - (player.getWidth() / 2));
		return 5;
	    }
	// Go right from middle top.
	else if (player.getOutOfBoundsRight() && backgroundId == 5)
	    {
		player.setPosX(0.0f);
		player.setCenterX(player.getWidth() / 2);
		return 4;
	    }
	// Go left from middle top.
	else if (player.getOutOfBoundsLeft() && backgroundId == 5)
	    {
		player.setPosX(512 - player.getWidth());
		player.setCenterX(512 - (player.getWidth() / 2));
		return 6;
	    }
	// Go right from upper left.
	else if (player.getOutOfBoundsRight() && backgroundId == 6)
	    {
		player.setPosX(0.0f);
		player.setCenterX(player.getWidth() / 2);
		return 5;
	    }
	// Go down from upper left.
	else if (player.getOutOfBoundsDown() && backgroundId == 6)
	    {
		player.setPosY(512 - player.getHeight());
		player.setCenterY(512 - (player.getHeight() / 2));
		return 3;
	    }
	// Go up from middle left.
	else if (player.getOutOfBoundsUp() && backgroundId == 3)
	    {
		player.setPosY(0.0f);
		player.setCenterY(player.getHeight() / 2);
		return 6;
	    }
		
	// Some arbitrary value.  Maybe we make this the current ID value???
	return backgroundId;
    }
    
    /**
     * 0 = Lower Left
     * 1 = Straight Left/Right Bottom
     * 2 = Lower Right
     * 3 = Straight Up/Down Left
     * 4 = Upper Right
     * 5 = Straight Left/Right Top
     * 6 = Upper Left
     * 7 = Straight Up/Down Right
     */
    public static void checkBackgroundStuff(int backgroundId,
					    Unit player,
					    Unit enemy,
					    Unit enemySprite,
					    Unit lingling,
					    Scene lowerLeft,
					    Scene upperLeft,
					    Scene lowerRight,
					    Scene upperRight,
					    Scene straightUpDown,
					    Scene straightLeftRight)
    {
	if (backgroundId == 1 || backgroundId == 5)
	    {
		Renderer.drawScene(straightLeftRight);
	    }

	if (backgroundId == 2)
	    {
		Renderer.drawScene(lowerRight);
		if (enemy.getRight())
		    {
			Renderer.drawSprite(enemy, 0);
		    }
		else
		    {
			Renderer.drawSprite(enemy, 1);
		    }
		// Set enemies to patrol.
		BaseBehaviors.patrolUnitLeftRight(enemy, 10.0f);
	    }

	if (backgroundId == 3 || backgroundId == 7)
	    {
		Renderer.drawScene(straightUpDown);
	    }
	
	if (backgroundId == 4)
	    {
		Renderer.drawScene(upperRight);
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
		BaseBehaviors.patrolUnitLeftRight(enemySprite, 5.0f);
	    }

	if (backgroundId == 6)
	    {
		Renderer.drawScene(upperLeft);
		Renderer.drawSpriteAnimation(lingling, 0, 4, 0.25f, 0.0f, 0.25f, 128, 128);
		if (frameSkip < 0)
		    {
			lingling.setAnimatedSpriteNumber(lingling.getAnimatedSpriteNumber() + 1);
			frameSkip = 2;
		    }
		frameSkip--;
	    }
    }

}
