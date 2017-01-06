package com.bamboo.tloll.graphics;

import com.bamboo.tloll.graphics.structure.Tile;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import java.util.List;

import com.bamboo.tloll.Constants;

import com.bamboo.tloll.graphics.structure.Tile;
import com.bamboo.tloll.graphics.structure.Scene;

public final class Renderer
{

    public Renderer()
    {
	// Empty constructor.
    }
    
    public static void drawSprite(Sprite unit, int bufferId)
    {
	glBindTexture(GL_TEXTURE_2D, bufferId);
	glPixelStoref(GL_UNPACK_ALIGNMENT, 1);
	glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE );
	glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE );
	glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, (int)unit.getWidth(), (int)unit.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, unit.getBufferMap().get(bufferId));
	glBegin(GL_QUADS);
	glTexCoord2f(0.0f, 1.0f);
	glVertex3f(unit.getPosX(), unit.getPosY(), 0.0f);
	glTexCoord2f(1.0f, 1.0f);
	glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY(), 0.0f);
	glTexCoord2f(1.0f, 0.0f);
	glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY() + unit.getHeight(), 0.0f);
	glTexCoord2f(0.0f, 0.0f);
	glVertex3f(unit.getPosX(), unit.getPosY() + unit.getHeight(), 0.0f);
	glEnd();
    }

    public static void drawSpriteAnimation(Sprite unit, int bufferId, int maxFrames, float width, float minHeight, float maxHeight, int imageWidth, int imageHeight)
    {
	
	if (unit.getAnimatedSpriteNumber() > maxFrames)
	    {
		unit.setAnimatedSpriteNumber(1);
	    }
	float xValMin = width * (unit.getAnimatedSpriteNumber() - 1);
	float xValMax = width * unit.getAnimatedSpriteNumber();
	glBindTexture(GL_TEXTURE_2D, bufferId);
	glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	// NOTE(map) : The animated guy randomly disappears if we use GL_CLAMP_TO_EDGE
	// and I'm not sure why right now.
	//glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE );
	//glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE );
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageWidth, imageHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, unit.getBufferMap().get(bufferId));
	glBegin(GL_QUADS);
	glTexCoord2f(xValMin, maxHeight);
	glVertex3f(unit.getPosX(), unit.getPosY(), 0.0f);
	glTexCoord2f(xValMax, maxHeight);
	glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY(), 0.0f);
	glTexCoord2f(xValMax, minHeight);
	glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY() + unit.getHeight(), 0.0f);
	glTexCoord2f(xValMin, minHeight);
	glVertex3f(unit.getPosX(), unit.getPosY() + unit.getHeight(), 0.0f);
	glEnd();
    }

    public static void drawSpriteFromSheet(Sprite sprite, int bufferId, int imageWidth, int imageHeight)
    {
	float xMin = 0.0f;
	float xMax = 1.0f / 36.0f;
	float yMin = 0.0f;
	float yMax = 1.0f;
	glBindTexture(GL_TEXTURE_2D, bufferId);
	glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
	glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE );
	glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE );
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageWidth, imageHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, sprite.getBufferMap().get(bufferId));
	glBegin(GL_QUADS);
	glTexCoord2f(xMin, yMax);
	glVertex3f(sprite.getPosX(), sprite.getPosY(), 0.0f);
	glTexCoord2f(xMax, yMax);
	glVertex3f(sprite.getPosX() + sprite.getWidth(), sprite.getPosY(), 0.0f);
	glTexCoord2f(xMax, yMin);
	glVertex3f(sprite.getPosX() + sprite.getWidth(), sprite.getPosY() + sprite.getHeight(), 0.0f);
	glTexCoord2f(xMin, yMin);
	glVertex3f(sprite.getPosX(), sprite.getPosY() + sprite.getHeight(), 0.0f);
	glEnd();
    }

    // Method for drawing a string onto the screen.  Only draws left to right curently.
    // NOTE(map) : There are hard coded values currently because they match with the png file and have
    // no need to be changed at the moment.  They could be changed later if desired.
    public static void drawString(GraphicsUtil gu, String currentDir, float posX, float posY, String message)
    {
	int imageWidth = 468;
	int imageHeight = 25;
	
	message = message.toUpperCase();
	char[] characters = message.toCharArray();

	Sprite sprite = new Sprite(posX, posY, 13.0f, 25.0f);
	sprite.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Images/alphabet.png"));
	int bufferId = 0;

	for(char character : characters)
	    {
		// Setting up the parameters for getting the correct letter from the alphabet.
		int numRepOfChar = character - 'A';
		float xMin = (1.0f / 36.0f) * (numRepOfChar * 1);
		float xMax = (1.0f / 36.0f) * ((numRepOfChar + 1) * 1);
		float yMin = 0.0f;
		float yMax = 1.0f;

		// Doing the actual rendering here.
		glBindTexture(GL_TEXTURE_2D, bufferId);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE );
		glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE );
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageWidth, imageHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, sprite.getBufferMap().get(bufferId));
		glBegin(GL_QUADS);
		glTexCoord2f(xMin, yMax);
		glVertex3f(sprite.getPosX(), sprite.getPosY(), 0.0f);
		glTexCoord2f(xMax, yMax);
		glVertex3f(sprite.getPosX() + sprite.getWidth(), sprite.getPosY(), 0.0f);
		glTexCoord2f(xMax, yMin);
		glVertex3f(sprite.getPosX() + sprite.getWidth(), sprite.getPosY() + sprite.getHeight(), 0.0f);
		glTexCoord2f(xMin, yMin);
		glVertex3f(sprite.getPosX(), sprite.getPosY() + sprite.getHeight(), 0.0f);
		glEnd();
		sprite.setPosX(sprite.getPosX() + sprite.getWidth());;
	    }

    }
    
    public static void drawScene(Scene scene)
    {
	for (Tile tile : scene.getTileList())
	    {
		drawSprite(tile, 0);
	    }
    }

    public static void loadTileBuffers(Scene scene, GraphicsUtil gu, String currentDir)
    {
	for (Tile tile : scene.getTileList())
	    {
		if (tile.isPassable())
		    {
			tile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Tiles/Water_Grass/grass.PNG"));
		    }
		else
		    {
			if (tile.getDirection() == 1)
			    {
				tile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Tiles/Water_Grass/left_water_right_grass.PNG"));
			    }
			else if (tile.getDirection() == 2)
			    {
				tile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Tiles/Water_Grass/top_water_bottom_grass.PNG"));
			    }
			else if (tile.getDirection() == 3)
			    {
				tile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Tiles/Water_Grass/left_grass_right_water.PNG"));				
			    }
			else if (tile.getDirection() == 4)
			    {
				tile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Tiles/Water_Grass/top_grass_bottom_water.PNG"));				
			    }
			else if (tile.getDirection() == 5)
			    {
				tile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Tiles/Water_Grass/water.PNG"));
	        	    }
			else if (tile.getDirection() == 6)
			    {
				tile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Tiles/Water_Grass/grass_bottom_left_water.PNG"));				
			    }
			else if (tile.getDirection() == 7)
			    {
				tile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Tiles/Water_Grass/grass_top_left_water.PNG"));				
			    }
			else if (tile.getDirection() == 8)
			    {
				tile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Tiles/Water_Grass/grass_top_right_water.PNG"));				
			    } 
			else if (tile.getDirection() == 9)
			    {
				tile.addBufferToMap(0, gu.loadTexture(currentDir + "/Assets/Map/Tiles/Water_Grass/grass_bottom_right_water.PNG"));				
			    }
		    }
	    }
    }
    
}
