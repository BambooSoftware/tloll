package com.bamboo.tloll.graphics.structure;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.constants.JsonConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMap extends GameMap {

    private static WorldMap instance;

    public static WorldMap getInstance() {
        if (instance == null) {
            instance = new WorldMap();
        }
        return instance;
    }

    private WorldMap() {
	super.setSceneMap(super.loadMapFromJson(Paths.get(Constants.USER_DIR + JsonConstants.WORLD_MAP_PATH)));
	super.setCurrentScene(super.getSceneMap().get(0));
    }

}
