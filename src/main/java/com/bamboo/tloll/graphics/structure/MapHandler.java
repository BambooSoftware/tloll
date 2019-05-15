package com.bamboo.tloll.graphics.structure;

import com.bamboo.tloll.WorldManager;

public class MapHandler {

    private static MapHandler instance;
    
    
    public static MapHandler getInstance() {
        if (instance == null) {
            instance = new MapHandler();
        }
        return instance;
    }

    public MapHandler() {
    }

    public GameMap getMapBasedOnCombat() {
	if (WorldManager.getInstance().getInCombat()) {
	    return CombatMap.getInstance();
	}
	else {
	    return WorldMap.getInstance();
	}
    }
    
    public CombatMap getCombatMap() {
	return CombatMap.getInstance();
    }

    public WorldMap getWorldMap() {
	return WorldMap.getInstance();
    }
}
