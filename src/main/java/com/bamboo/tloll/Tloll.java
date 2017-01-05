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

import com.bamboo.tloll.Constants;

import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.Renderer;
import com.bamboo.tloll.graphics.MapCreator;
import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.Scene;

import com.bamboo.tloll.input.Input;

import com.bamboo.tloll.behaviors.BaseBehaviors;

import com.sun.opengl.util.awt.TextRenderer;


public class Tloll
{

    private long windowId;
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

	double angle = 45.0;
	
	gu.initializeGL();
	glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
	tloll.windowId = gu.createWindow(Constants.WIDTH, Constants.HEIGHT, Constants.TITLE);
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
        Unit player = new Unit(0.0f, 0.0f, 68, 100, 1.0f, 0.0f, 0.0f, 0.0f);
	Unit bullet = new Unit(68.0f, 50.0f, 30, 10, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit sword = new Unit(0.0f, 0.0f, 80, 20, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit enemy = new Unit(0.0f, 200.0f, 128, 128, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit enemyTarget = new Unit(400.0f, 100.0f, 64.0f, 64.0f, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit enemyHp = new Unit(400.0f, 90.0f, 64.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit enemySprite = new Unit(0.0f, 350.0f, 108, 140, 0.0f, 0.0f, 0.0f, 0.0f);
	Unit lingling = new Unit(100.0f, 100.0f, 32, 32, 0.0f, 0.0f, 0.0f, 0.0f);

	player.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/player.png"));
	bullet.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/bullet.png"));
	sword.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/sword.png"));
	enemy.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/enemy.png"));
	enemy.addBufferToMap(1, gu.loadTexture(currentDir + "/Assets/Images/enemy_left.png"));
	enemyTarget.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/enemy_target.png"));
	enemyHp.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/hp_bar_0.png"));
	enemyHp.addBufferToMap(1, gu.loadTexture(currentDir + "/Assets/Images/hp_bar_1.png"));
	enemyHp.addBufferToMap(2, gu.loadTexture(currentDir + "/Assets/Images/hp_bar_2.png"));
	enemyHp.addBufferToMap(3, gu.loadTexture(currentDir + "/Assets/Images/hp_bar_3.png"));
	enemySprite.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/SpriteSheet.png"));
	lingling.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Actors/panda_f_base.png"));
	
	int backgroundId = 0;

	// Drawing some sample scenes here.
	Scene lowerLeft = new Scene(1, 1);
	Scene upperLeft = new Scene(2, 2);
	Scene lowerRight = new Scene(3, 3);
	Scene upperRight = new Scene(4, 4);
	Scene straightUpDown = new Scene(5, 5);
	Scene straightLeftRight = new Scene(6, 6);
	Scene currentScene = null;
	
	Renderer.loadTileBuffers(lowerLeft, gu, currentDir);
	Renderer.loadTileBuffers(upperLeft, gu, currentDir);
	Renderer.loadTileBuffers(lowerRight, gu, currentDir);
	Renderer.loadTileBuffers(upperRight, gu, currentDir);
	Renderer.loadTileBuffers(straightUpDown, gu, currentDir);
	Renderer.loadTileBuffers(straightLeftRight, gu, currentDir);
	
	while (isRunning)
	    {

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the frame buffer.

		currentScene = getCurrentScene(backgroundId,
					       lowerLeft,
					       lowerRight,
					       upperLeft,
					       upperRight,
					       straightLeftRight,
					       straightUpDown);
		Renderer.drawScene(currentScene);

		setEnemyUnitActions(backgroundId,
				    player,
				    enemy,
				    enemyTarget,
				    enemyHp,
				    enemySprite,
				    lingling,
				    lowerLeft,
				    lowerRight,
				    upperLeft,
				    upperRight,
				    straightLeftRight,
				    straightUpDown
				    );

		// Highlight the current tile the player lives on.
		highlightCurrentTile(currentScene, gu, player);

		// Handle attacking input and moving the direction once to the right pre frame.
		// TODO(map) : Need to be able to take in a variety of directions.
		// TODO(map) : Need to move this code and melee animation out to their own methods
		// and clean them up a bit.  The math is very good for doing what we want in the sword
		// animation hit box though.
		if (player.isAttackingRanged())
		    {
			handleBulletAnimation(currentScene, player, "right", bullet);
			Renderer.drawSprite(bullet, 0);
			highlightCurrentTile(currentScene, gu, bullet);
			if (backgroundId == 1)
			    {
				System.out.println("Targetting enemy.");
				if ((bullet.getPosX() + bullet.getWidth()) > enemyTarget.getPosX() &&
				    (bullet.getPosX() + bullet.getWidth()) < (enemyTarget.getPosX() + enemyTarget.getWidth()) &&
				    bullet.getPosY() > enemyTarget.getPosY() &&
				    bullet.getPosY() < (enemyTarget.getPosY() + enemyTarget.getHeight()) &&
				    enemyTarget.getHitPoints() > 0)
				    {
					player.setIsAttackingRanged(false);
					enemyTarget.setHitPoints(enemyTarget.getHitPoints() - 1);
					System.out.println("Enemy HP: " + enemyTarget.getHitPoints());
				    }
			    }
		    }
		else
		    {
			bullet.setPosX(player.getPosX() + player.getWidth());
			bullet.setPosY(player.getPosY() + (player.getHeight() / 2));
		    }

		if (player.isAttackingMelee())
		    {
			handleSwordAnimation(currentScene, player, "right", sword, angle);
			Renderer.drawSprite(sword, 0);
			highlightCurrentTile(currentScene, gu, sword);
			double x = Math.toRadians(angle);
			double y = Math.toRadians(angle);
			double nextX = (double) player.getCenterX() + (player.getWidth() / 2) * Math.cos(x);
			double nextY = (double) player.getCenterY() + (player.getHeight() / 2) * Math.sin(x);
			
			sword.setPosX((float) nextX);
			sword.setPosY((float) nextY);
			angle-=6;
			if (backgroundId == 1)
			    {
				System.out.println("Targetting enemy.");
				if((sword.getPosX() + sword.getWidth()) > enemyTarget.getPosX() &&
				   (sword.getPosX() + sword.getWidth()) < (enemyTarget.getPosX() + enemyTarget.getWidth()) &&
				   sword.getPosY() > enemyTarget.getPosY() &&
				   sword.getPosY() < (enemyTarget.getPosY() + enemyTarget.getHeight()) &&
				   enemyTarget.getHitPoints() > 0)
				    {
					player.setIsAttackingMelee(false);
					enemyTarget.setHitPoints(enemyTarget.getHitPoints() - 1);
					System.out.println("Enemy HP: " + enemyTarget.getHitPoints());
				    }
			    }
		    }
		else
		    {
			angle = 45.0;
			sword.setPosX(player.getPosX() + player.getWidth());
			sword.setPosY(player.getPosY() + player.getHeight() / 2);
		    }
		
		// Draw caveman sprite.
		Renderer.drawSprite(player, 0);

		// Set up keyboard controls.
		in.checkKeyPressed(tloll.windowId, player);
		in.checkKeyRelease(tloll.windowId, player);
		in.bindDebugKey(tloll.windowId, player, lowerLeft);
		in.bindResetKey(tloll.windowId, enemyTarget);

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
		player.setPosX(Constants.WIDTH - player.getWidth());
		player.setCenterX(Constants.WIDTH - (player.getWidth() / 2));
		return 0;
	    }
	// Go down from middle left.
	else if (player.getOutOfBoundsDown() && backgroundId == 3)
	    {
		player.setPosY(Constants.HEIGHT - player.getHeight());
		player.setCenterY(Constants.HEIGHT - (player.getHeight() / 2));
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
		player.setPosY(Constants.HEIGHT - player.getHeight());
		player.setCenterY(Constants.HEIGHT - (player.getHeight() / 2));
		return 2;
	    }
	// Go left from lower right.
	else if (player.getOutOfBoundsLeft() && backgroundId == 2)
	    {
		player.setPosX(Constants.WIDTH - player.getWidth());
		player.setCenterX(Constants.WIDTH - (player.getWidth() / 2));
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
		player.setPosY(Constants.HEIGHT - player.getHeight());
		player.setCenterY(Constants.HEIGHT - (player.getHeight() / 2));
		return 7;
	    }
	// Go left from upper right.
	else if (player.getOutOfBoundsLeft() && backgroundId == 4)
	    {
		player.setPosX(Constants.WIDTH - player.getWidth());
		player.setCenterX(Constants.WIDTH - (player.getWidth() / 2));
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
		player.setPosX(Constants.WIDTH - player.getWidth());
		player.setCenterX(Constants.WIDTH - (player.getWidth() / 2));
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
		player.setPosY(Constants.HEIGHT - player.getHeight());
		player.setCenterY(Constants.HEIGHT - (player.getHeight() / 2));
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
    

    // Sets the enemy units in the scenes to do their actions.
    public static void setEnemyUnitActions(int backgroundId,
					   Unit player,
					   Unit enemy,
					   Unit enemyTarget,
					   Unit enemyHp,
					   Unit enemySprite,
					   Unit lingling,
					   Scene lowerLeft,
					   Scene upperLeft,
					   Scene lowerRight,
					   Scene upperRight,
					   Scene straightUpDown,
					   Scene straightLeftRight)
    {
	if (backgroundId == 1)
	    {
		Renderer.drawSprite(enemyTarget, 0);
		if (enemyTarget.getHitPoints() == 3)
		    {
			Renderer.drawSprite(enemyHp, 3);
		    }
		else if (enemyTarget.getHitPoints() == 2)
		    {
			Renderer.drawSprite(enemyHp, 2);
		    }
		else if (enemyTarget.getHitPoints() == 1)
		    {
			Renderer.drawSprite(enemyHp, 1);
		    }
		else
		    {
			Renderer.drawSprite(enemyHp, 0);
		    }
	    }
	
	if (backgroundId == 2)
	    {
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

	if (backgroundId == 4)
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
		BaseBehaviors.patrolUnitLeftRight(enemySprite, 5.0f);
	    }

	if (backgroundId == 6)
	    {
		Renderer.drawSpriteAnimation(lingling, 0, 4, 0.25f, 0.0f, 0.25f, 128, 128);
		if (frameSkip < 0)
		    {
			lingling.setAnimatedSpriteNumber(lingling.getAnimatedSpriteNumber() + 1);
			frameSkip = 2;
		    }
		frameSkip--;
	    }
    }

    // Returns a scene based on a given background ID.
    public static Scene getCurrentScene(int backgroundId,
					Scene lowerLeft,
					Scene upperLeft,
					Scene lowerRight,
					Scene upperRight,
					Scene straightUpDown,
					Scene straightLeftRight)
    {
	switch (backgroundId)
	    {
	    case 1:
		return straightLeftRight;
	    case 2:
		return lowerRight;
	    case 3:
		return straightUpDown;
	    case 4:
		return upperRight;
	    case 5:
		return straightLeftRight;
	    case 6:
		return upperLeft;
	    case 7:
		return straightUpDown;
	    default:
		return lowerLeft;
	    }
    }	
    
    // Debug method for highlighting the current tile the player lives in.
    // NOTE(map) : This is only based on the lower left corner of the player
    public static void highlightCurrentTile(Scene currentScene, GraphicsUtil gu, Unit player)
    {
	for (Tile tile : currentScene.getTileList())
	    {
		if (player.getPosX() <= (tile.getPosX() + tile.getWidth()) &&
		    player.getPosX() >= tile.getPosX() &&
		    player.getPosY() <= (tile.getPosY() + tile.getHeight()) &&
		    player.getPosY() >= tile.getPosY())
		    {
			Tile highlightTile = new Tile(tile.getPosX(), tile.getPosY(), tile.getWidth(), tile.getHeight(), tile.isPassable(), tile.getDirection(), tile.getTileNum());
			highlightTile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/highlight.png"));
			Renderer.drawSprite(highlightTile, 0);
		    }
	    }
    }

    public static void handleBulletAnimation(Scene currentScene, Unit player, String directionOfAttack, Unit bullet)
    {
	System.out.println("Bullet travelling...");
	switch (directionOfAttack)
	    {
	    case "right":
	        if (bullet.getPosX() + 10 > Constants.WIDTH)
		    {
			player.setIsAttackingRanged(false);
			break;
		    }
		bullet.setPosX(bullet.getPosX() + 10.0f);
		break;
	    case "left":
		if (bullet.getPosX() - 10 < 0)
		    {
			player.setIsAttackingRanged(false);
			break;
		    }
		bullet.setPosX(bullet.getPosX() - 10.0f);
		break;
	    case "up":
		if (bullet.getPosY() + 10 > Constants.HEIGHT)
		    {
			player.setIsAttackingRanged(false);
			break;
		    }
		bullet.setPosY(bullet.getPosY() + 10.0f);
		break;
	    case "down":
		if (bullet.getPosY() - 10 < 0)
		    {
			player.setIsAttackingRanged(false);
			break;
		    }
		bullet.setPosY(bullet.getPosY() + 10.0f);
		break;
	    }
	System.out.println("Bullet Position (X,Y): " + bullet.getPosX() + "," + bullet.getPosY());
    }

    public static void handleSwordAnimation(Scene currentScene, Unit player, String directionOfAttack, Unit sword, double angle)
    {
	System.out.println("Sword swinging...");
	switch (directionOfAttack)
	    {
	    case "right":
		// This works based on Y position for sword only.
		if (sword.getPosY() < player.getPosY())
		    {
			player.setIsAttackingMelee(false);
			break;
		    }
		// WOrks based on angles instead of single coordinate.
		if (angle < -45)
		    {
			player.setIsAttackingMelee(false);
			break;
		    }
	    }
	System.out.println("Sword Position (X,Y): " + sword.getPosX() + "," + sword.getPosY());
    }

}
