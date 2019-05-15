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

public class CombatMap extends GameMap {

    private static CombatMap instance;

    public static CombatMap getInstance() {
        if (instance == null) {
            instance = new CombatMap();
        }
        return instance;
    }

    private CombatMap() {
	super.setSceneMap(super.loadMapFromJson(Paths.get(Constants.USER_DIR + JsonConstants.COMBAT_MAP_PATH)));
	super.setCurrentScene(super.getSceneMap().get(0));
    }

}
