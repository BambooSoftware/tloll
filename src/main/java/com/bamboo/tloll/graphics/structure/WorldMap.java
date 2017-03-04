package com.bamboo.tloll.graphics.structure;

import com.bamboo.tloll.graphics.structure.Scene;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class WorldMap {

    private static WorldMap instance;

    private Map<Integer, Scene> sceneMap;

    //Scene currentScene; TODO: add me back when we make tloll.java great again
    //List<Scene> currentSceneConnections;

    public static WorldMap getInstance() {
        if(instance == null) {
			instance = new WorldMap();
        }
		return instance;
    }

    private WorldMap() {
        sceneMap = new HashMap<>();
    }

    public Map<Integer, Scene> getSceneMap() {
         return sceneMap;
    }


}
