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
    
    public static void drawSprite(Unit unit, int bufferId)
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
    public static void drawSpriteAnimation(Unit unit, int bufferId, int maxFrames, float width, float height, int imageWidth, int imageHeight)
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
	glTexCoord2f(xValMin, height);
	glVertex3f(unit.getPosX(), unit.getPosY(), 0.0f);
	glTexCoord2f(xValMax, height);
	glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY(), 0.0f);
	glTexCoord2f(xValMax, 0.0f);
	glVertex3f(unit.getPosX() + unit.getWidth(), unit.getPosY() + unit.getHeight(), 0.0f);
	glTexCoord2f(xValMin, 0.0f);
	glVertex3f(unit.getPosX(), unit.getPosY() + unit.getHeight(), 0.0f);
	glEnd();
    }
    
}
