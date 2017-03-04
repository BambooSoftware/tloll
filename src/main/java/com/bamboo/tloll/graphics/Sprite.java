package com.bamboo.tloll.graphics;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Sprite class that represents the basic unit of everything in the game.
 */
public class Sprite {

    private float posX;
    private float posY;
    private float width;
    private float height;
    private float centerX;
    private float centerY;
    private int animatedFrameNumber;

    private Map<Integer, SpriteBuffer> bufferMap;

    public Sprite() {
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.width = 10.0f;
        this.height = 10.0f;
        this.centerX = this.posX + (this.width / 2);
        this.centerY = this.posY + (this.height / 2);
        this.animatedFrameNumber = 1;
        this.bufferMap = new HashMap<>();
    }

    public Sprite(float posX, float posY, float width, float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.centerX = posX + (width / 2);
        this.centerY = posY + (height / 2);
        this.animatedFrameNumber = 1;
        this.bufferMap = new HashMap<>();
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public int getAnimatedSpriteNumber() {
        return animatedFrameNumber;
    }

    public void setAnimatedSpriteNumber(int animatedFrameNumber) {
        this.animatedFrameNumber = animatedFrameNumber;
    }

    public Map<Integer, SpriteBuffer> getBufferMap() {
        return bufferMap;
    }

    public void addBufferToMap(Integer bufferId, SpriteBuffer buffer) {
        this.bufferMap.put(bufferId, buffer);
    }
}
