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

public class WorldMap {

    private static WorldMap instance;
    private Map<Integer, Scene> sceneMap;
    private Scene currentScene;

    public static WorldMap getInstance() {
        if (instance == null) {
            instance = new WorldMap();
        }
        return instance;
    }

    private WorldMap() {
        sceneMap = loadMapFromJson();
	currentScene = sceneMap.get(0);
    }

    public Map<Integer, Scene> getSceneMap() {
        return sceneMap;
    }

    public Scene getCurrentScene()
    {
	return currentScene;
    }

    public void setCurrentScene(Scene currentScene)
    {
	this.currentScene = currentScene;
    }

    private Map<Integer, Scene> loadMapFromJson() {
        Map<Integer, Scene> sceneMap = new HashMap<>();
        List<Tile> tileList = new ArrayList<>();
        List<Obstacle> obstacleList = new ArrayList<>();

        try {
            String contents = new String(Files.readAllBytes(Paths.get(Constants.USER_DIR + JsonConstants.WORLD_MAP_PATH)));

            // Grab the world object as a whole from the JSON.
            JSONObject worldObj = new JSONObject(contents);
            // Get the actual world within the JSON file.
            JSONObject world = worldObj.getJSONObject(JsonConstants.WORLD);
            JSONArray scenes = world.getJSONArray(JsonConstants.SCENES);

            // Loop over every scene here.
            for (int i = 0; i < scenes.length(); i++) {
                // Grab all tiles for a given scene and loop over.
                JSONArray tiles = scenes.getJSONObject(i).getJSONArray(JsonConstants.TILES);
                for (int j = 0; j < tiles.length(); j++) {
                    float posX = 80.0f * (int) (j / 8);
                    float posY = 80.0f * (j % 8);
                    Tile tile = new Tile(posX,
					 posY,
					 (float) tiles.getJSONObject(j).getInt(JsonConstants.WIDTH),
					 (float) tiles.getJSONObject(j).getInt(JsonConstants.HEIGHT),
					 tiles.getJSONObject(j).getBoolean(JsonConstants.PASSABLE),
					 tiles.getJSONObject(j).getString(JsonConstants.BUFFER_ID),
					 tiles.getJSONObject(j).getInt(JsonConstants.ID),
					 tiles.getJSONObject(j).getBoolean(JsonConstants.EXIT)
					 );
                    tileList.add(tile);
		}

		// TODO(map) : Consider putting a check in here for if there are no obstacles.
		JSONArray obstacles = scenes.getJSONObject(i).getJSONArray(JsonConstants.OBSTACLES);
                for (int j = 0; j < obstacles.length(); j++) {
                    float posX = obstacles.getJSONObject(j).getInt(JsonConstants.POS_X);
                    float posY = obstacles.getJSONObject(j).getInt(JsonConstants.POS_Y);
                    Obstacle obstacle = new Obstacle(posX,
						     posY,
						     (float) obstacles.getJSONObject(j).getInt(JsonConstants.WIDTH),
						     (float) obstacles.getJSONObject(j).getInt(JsonConstants.HEIGHT),
						     (float) obstacles.getJSONObject(j).getInt(JsonConstants.PROT_HEIGHT),
						     obstacles.getJSONObject(j).getBoolean(JsonConstants.PASSABLE),
						     obstacles.getJSONObject(j).getString(JsonConstants.BUFFER_ID),
						     obstacles.getJSONObject(j).getInt(JsonConstants.ID)
						     );
                    obstacleList.add(obstacle);
		}
		
		JSONArray jsonLinks = scenes.getJSONObject(i).getJSONArray(JsonConstants.LINKS);

                Map<Integer, Link> links = new HashMap<>();
                for (int k = 0; k < jsonLinks.length(); ++k) {
                    JSONObject link = jsonLinks.getJSONObject(k);
                    int newSceneId = link.getInt(JsonConstants.NEW_SCENE_ID);
                    int newTileId = link.getInt(JsonConstants.NEW_TILE_ID);
                    int exitTileId = link.getInt(JsonConstants.EXIT_TILE_ID);
                    links.put(exitTileId, new Link(newSceneId, newTileId));
                }


                int sceneId = scenes.getJSONObject(i).getInt(JsonConstants.ID);
                Scene scene = new Scene(sceneId, tileList, obstacleList, links);
                sceneMap.put(sceneId, scene);
                tileList.clear();
                obstacleList.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sceneMap;

    }


}
