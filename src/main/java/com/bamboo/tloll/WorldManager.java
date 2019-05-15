package com.bamboo.tloll;

/**
 * Class for managing world level objects like what mode the game is in (World vs Combat) and anything else that
 * could be useful down the road.
 */
public class WorldManager {

    private static WorldManager instance;

    private boolean inCombat;

    public static WorldManager getInstance() {
        if (instance == null) {
            instance = new WorldManager();
        }
        return instance;
    }

    private WorldManager() {
	this.inCombat = false;
    }
    
    public boolean getInCombat() {
	return inCombat;
    }

    public void setInCombat(boolean inCombat) {
	this.inCombat = inCombat;
    }

}
