package com.bamboo.tloll.graphics.structure;

import com.bamboo.tloll.graphics.Sprite;

public class Tile extends Sprite {

    private boolean passable;
    private String bufferId;
    private int tileId;
    private boolean exit;

    public Tile() {
        super();
    }

    // NOTE(map) : There are two constructors, one that take posX/Y and one that doesn't. If no posX/Y
    // is provided, it calculates.  Currently there is no posX/Y in JSON.  They are optional values.
    public Tile(float posX, float posY, float width, float height, boolean passable, String bufferId, int tileId, boolean exit) {
        super(posX, posY, width, height);
        this.passable = passable;
        this.bufferId = bufferId;
        this.tileId = tileId;
        this.exit = exit;
    }

    public boolean isPassable() {
        return passable;
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

    public int getTileId() {
        return tileId;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

}
