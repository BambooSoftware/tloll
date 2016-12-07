package com.bamboo.tloll.graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

public final class Renderer
{

    public Renderer()
    {
	// Empty constructor.
    }
    
    public static void drawSprite(Sprite unit, int bufferId)
    {
	glBindTexture(GL_TEXTURE_2D, bufferId);
	glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
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

    // TODO(map) : Need to get the width and height of the image being passed in
    // so we can calulate the min and max values
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

    public static void drawSpriteNormalized(Sprite sprite, int bufferId)
    {
	// NOTE(map) : We have to do (1 - VALUE) for the Y because the map travels from the upper
	// left downward.  The X value travels normally from left to right. 
	float xMin = ((sprite.getPosX()) / 512);
	float xMax = ((sprite.getPosX() + sprite.getWidth()) / 512);
	float yMin = 1 - ((sprite.getPosY()) / 512);
	float yMax = 1 - ((sprite.getPosY() + sprite.getHeight()) / 512);
	glBindTexture(GL_TEXTURE_2D, bufferId);
	glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 512, 512, 0, GL_RGBA, GL_UNSIGNED_BYTE, sprite.getBufferMap().get(bufferId));
	glBegin(GL_QUADS);
	glTexCoord2f(xMin, yMin);
	glVertex3f(sprite.getPosX(), sprite.getPosY(), 0.0f);
	glTexCoord2f(xMax, yMin);
	glVertex3f(sprite.getPosX() + sprite.getWidth(), sprite.getPosY(), 0.0f);
	glTexCoord2f(xMax, yMax);
	glVertex3f(sprite.getPosX() + sprite.getWidth(), sprite.getPosY() + sprite.getHeight(), 0.0f);
	glTexCoord2f(xMin, yMax);
	glVertex3f(sprite.getPosX(), sprite.getPosY() + sprite.getHeight(), 0.0f);
	glEnd();
    }
    
}
