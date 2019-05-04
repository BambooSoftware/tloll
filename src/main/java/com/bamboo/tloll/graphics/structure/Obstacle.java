/**
 * @author: MAPlatt
 * @date: 2019-04-21
 * @license:
 */

package com.bamboo.tloll.graphics.structure;

import com.bamboo.tloll.graphics.Sprite;

public class Obstacle extends Sprite {

    private boolean passable;
    private String bufferId;
    private int obstacleId;
    private float protrusionHeight;
    
    public Obstacle() {
        super();
    }

    // NOTE(map) : There are two constructors, one that take posX/Y and one that doesn't. If no posX/Y
    // is provided, it calculates.  Currently there is no posX/Y in JSON.  They are optional values.
    public Obstacle(float posX, float posY, float width, float height, float protrusionHeight, boolean passable, String bufferId, int obstacleId) {
        super(posX, posY, width, height);
	this.protrusionHeight = protrusionHeight;
        this.passable = passable;
        this.bufferId = bufferId;
        this.obstacleId = obstacleId;
    }

    public boolean isPassable() {
        return passable;
    }

    public float getProtrusionHeight() {
        return protrusionHeight;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public String getBufferId() {
        return bufferId;
    }

    public void setBufferId(String direction) {
        this.bufferId = bufferId;
    }

    public int getObstacleId() {
        return obstacleId;
    }

}
