package com.bamboo.tloll;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.constants.JsonConstants;
import com.bamboo.tloll.graphics.Direction;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.SpriteBuffer;
import com.bamboo.tloll.graphics.Unit;
import com.bamboo.tloll.physics.Vector3;
import com.bamboo.tloll.player.Controls;
import com.bamboo.tloll.player.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * This holds information from the config for the running instance of the game.
 *
 * Created by ablackbu on 3/28/17.
 */
public class Config {

    private long windowId;
    private Map<Integer, Player> players;

    private static Config _instance;

    public static Config getInstance() {
        if(_instance == null) {
            _instance = new Config();
        }
        return _instance;
    }

    private Config() {
    }

    private void loadPlayersFromJson() {
        players = new HashMap<>();
        Map<Integer, Controls> controlsMap = loadPlayerControls();
        Map<Integer, String> playerUnitMap = loadPlayerUnitMap();
        Map<String, Unit> unitMap = loadUnitMap();


        for(int playerId: controlsMap.keySet()) {
            Controls controls = controlsMap.get(playerId);
            String unitId = playerUnitMap.get(playerId);
            Unit unit = unitMap.get(unitId);
            Player player = new Player(unit, controls);
            players.put(playerId, player);
        }
    }

    public long getWindowId() {
        return windowId;
    }

    public void setWindowId(long windowId) {
        this.windowId = windowId;
    }

    /**
     * Will load players from json if it hasn't already been loaded.
     * Ideally this would have been done when the singleton is initialized but since we load the buffers
     * we must load the gu context first.
     * @return
     */
    public Map<Integer, Player> getPlayers() {
        if(players == null) {
            loadPlayersFromJson();
        }

        return players;
    }

    private Map<Integer, Controls> loadPlayerControls()  {
        Map<Integer, Controls> controls = new HashMap<>();

        try  {
            byte[] contents = Files.readAllBytes(Paths.get(Constants.USER_DIR + JsonConstants.CONTROLS_PATH));
            JSONObject jsonObject = new JSONObject(new String(contents));

            JSONArray players = jsonObject.getJSONArray(JsonConstants.PLAYERS);
            for(int i = 0; i < players.length(); ++i ) {
                JSONObject player = players.getJSONObject(i);
                Integer id = player.getInt(JsonConstants.ID);
                int up = player.getInt(JsonConstants.UP);
                int down = player.getInt(JsonConstants.DOWN);
                int left = player.getInt(JsonConstants.LEFT);
                int right = player.getInt(JsonConstants.RIGHT);
                int primary = player.getInt(JsonConstants.PRIMARY);
                int secondary = player.getInt(JsonConstants.SECONDARY);
                controls.put(id, new Controls(up, down, left, right, primary, secondary));
            }
        }  catch (IOException e)  {
            e.printStackTrace();
        }  catch (JSONException e)  {
            e.printStackTrace();
        }
        return controls;
    }

    private Map<Integer, String> loadPlayerUnitMap()  {
        Map<Integer, String> playerUnitMap = new HashMap<>();

        try  {
            byte[] contents = Files.readAllBytes(Paths.get(Constants.USER_DIR + JsonConstants.PLAYER_UNIT_MAP_PATH));
            JSONObject jsonObject = new JSONObject(new String(contents));

            JSONArray players = jsonObject.getJSONArray(JsonConstants.PLAYER_UNIT_MAP);
            for(int i = 0; i < players.length(); ++i ) {
                JSONObject player = players.getJSONObject(i);
                Integer playerId = player.getInt(JsonConstants.PLAYER_ID);
                String unitId = player.getString(JsonConstants.UNIT_ID);
                playerUnitMap.put(playerId, unitId);
            }
        }  catch (IOException e)  {
            e.printStackTrace();
        }  catch (JSONException e)  {
            e.printStackTrace();
        }
        return playerUnitMap;
    }

    private Map<String, Unit> loadUnitMap()  {

        GraphicsUtil gu = GraphicsUtil.getInstance();

        Map<String, Unit> unitMap = new HashMap<>();

        try  {
            byte[] contents = Files.readAllBytes(Paths.get(Constants.USER_DIR + JsonConstants.UNITS_PATH));
            JSONObject jsonObject = new JSONObject(new String(contents));

            JSONArray players = jsonObject.getJSONArray(JsonConstants.UNITS);
            for(int i = 0; i < players.length(); ++i ) {
                JSONObject player = players.getJSONObject(i);
                String id = player.getString(JsonConstants.ID);
                int height = player.getInt(JsonConstants.HEIGHT);
                int width = player.getInt(JsonConstants.WIDTH);
                float acceleration = Double.valueOf(player.getDouble(JsonConstants.ACCELERATION)).floatValue();
                float maxSpeed = Double.valueOf(player.getDouble(JsonConstants.MAX_SPEED)).floatValue();
                boolean debug = player.getBoolean(JsonConstants.DEBUG);

                //TODO: We probably should figure out a better span position than 100,100
                Unit unit = new Unit(100.0f, 100.0f, height, width, acceleration, new Vector3(), Direction.DOWN, maxSpeed);
                unit.setDebug(debug);

                JSONObject spriteBufferJson = player.getJSONObject(JsonConstants.SPRITE_BUFFER);
                int spriteBufferId = spriteBufferJson.getInt(JsonConstants.ID);
                String spriteBufferPath = spriteBufferJson.getString(JsonConstants.PATH);
                int spriteBufferRows = spriteBufferJson.getInt(JsonConstants.SPRITE_SHEET_ROWS);
                int spriteBufferCols = spriteBufferJson.getInt(JsonConstants.SPRITE_SHEET_COLS);

                SpriteBuffer spriteBuffer =  new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + spriteBufferPath),
                        height, width, spriteBufferRows, spriteBufferCols);

                unit.addBufferToMap(spriteBufferId, spriteBuffer);

                unitMap.put(id, unit);
            }
        }  catch (IOException e)  {
            e.printStackTrace();
        }  catch (JSONException e)  {
            e.printStackTrace();
        }
        return unitMap;
    }


}
