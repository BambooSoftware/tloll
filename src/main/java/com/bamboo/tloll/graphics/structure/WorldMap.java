package com.bamboo.tloll.graphics.structure;

import com.bamboo.tloll.Constants;
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

public class WorldMap {

    private static WorldMap instance;
    private Map<Integer, Scene> sceneMap;

    public static WorldMap getInstance() {
        if (instance == null) {
            instance = new WorldMap();
        }
        return instance;
    }

    private WorldMap() {
        sceneMap = loadMapFromJson();
    }

    public Map<Integer, Scene> getSceneMap() {
        return sceneMap;
    }


    private Map<Integer, Scene> loadMapFromJson() {
        Map<Integer, Scene> sceneMap = new HashMap<>();
        List<Tile> tileList = new ArrayList<>();

        try {
            String contents = new String(Files.readAllBytes(Paths.get(Constants.USER_DIR + "/Configs/Worlds/test_world.json")));

            // Grab the world object as a whole from the JSON.
            JSONObject worldObj = new JSONObject(contents);
            // Get the actual world within the JSON file.
            JSONObject world = worldObj.getJSONObject("world");
            JSONArray scenes = world.getJSONArray("scenes");

            // Loop over every scene here.
            for (int i = 0; i < scenes.length(); i++) {
                // Grab all tiles for a given scene and loop over.
                JSONArray tiles = scenes.getJSONObject(i).getJSONArray("tiles");
                for (int j = 0; j < tiles.length(); j++) {


                    float posX = 80.0f * (int) (j / 8);
                    float posY = 80.0f * (j % 8);
                    Tile tile = new Tile(posX,
                            posY,
                            (float) tiles.getJSONObject(j).getInt("width"),
                            (float) tiles.getJSONObject(j).getInt("height"),
                            tiles.getJSONObject(j).getBoolean("passable"),
                            tiles.getJSONObject(j).getString("bufferId"),
                            tiles.getJSONObject(j).getInt("id"),
                            tiles.getJSONObject(j).getBoolean("exit")
                    );
                    tileList.add(tile);
                }

                JSONArray jsonLinks = scenes.getJSONObject(i).getJSONArray("links");

                Map<Integer, Link> links = new HashMap<>();
                //TODO: squish me and make me pretty
                for (int k = 0; k < jsonLinks.length(); ++k) {
                    JSONObject link = jsonLinks.getJSONObject(k);
                    int newSceneId = link.getInt("newSceneId");
                    int newTileId = link.getInt("newTileId");
                    int exitTileId = link.getInt("exitTileId");
                    links.put(exitTileId, new Link(newSceneId, newTileId));
                }


                int sceneId = scenes.getJSONObject(i).getInt("id");
                Scene scene = new Scene(sceneId, tileList, links);
                sceneMap.put(sceneId, scene);
                tileList.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sceneMap;

    }


}
