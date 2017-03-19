package com.bamboo.tloll.util;

import com.bamboo.tloll.Constants;
import com.bamboo.tloll.graphics.GraphicsUtil;
import com.bamboo.tloll.graphics.SpriteBuffer;
import com.google.common.collect.ImmutableMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public final class BufferMap {

    private Map<String, String> idToPathBufferMap;
    private Map<String, SpriteBuffer> idToSpriteBufferMap;

    private static BufferMap _instance;

    private BufferMap() {
        idToPathBufferMap = loadIdToPathBufferMap();
        idToSpriteBufferMap = new HashMap<>();
    }

    public static BufferMap getInstance() {
        if (_instance == null) {
            _instance = new BufferMap();
        }
        return _instance;
    }

    private Map<String, String> loadIdToPathBufferMap() {

        Map<String, String> bufferMap = new HashMap<>();

        //TODO replace me with try with resources. Also see what needs to be closed for the exception on file load.
        try  {
            String contents = new String(Files.readAllBytes(Paths.get(Constants.USER_DIR+ "/Configs/Buffers/buffer_map.json")));

            // Grab the world object as a whole from the JSON.
            JSONObject jsonObject = new JSONObject(contents);

            // Get the actual world within the JSON file.
            JSONArray bufferMapConfig = jsonObject.getJSONArray("bufferMap");
            for(int i = 0; i < bufferMapConfig.length(); ++i ) {
                JSONObject bufferItem = bufferMapConfig.getJSONObject(i);
                String id = bufferItem.getString("id");
                String path = bufferItem.getString("path");
                bufferMap.put(id, path);
            }

        }  catch (IOException e)  {
            e.printStackTrace();
        }  catch (JSONException e)  {
            e.printStackTrace();
        }
        return bufferMap;
    }

    public ImmutableMap<String, SpriteBuffer> getSpriteBufferMap() {
        return ImmutableMap.copyOf(idToSpriteBufferMap);
    }

    public SpriteBuffer getSpriteBuffer(String id) {
        GraphicsUtil gu = GraphicsUtil.getInstance();
        if(idToSpriteBufferMap.get(id) == null) {
            SpriteBuffer sBuffer = new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + idToPathBufferMap.get(id)), Constants.TILE_HEIGHT, Constants.TILE_WIDTH);
            idToSpriteBufferMap.put(id, sBuffer);
        }

        return idToSpriteBufferMap.get(id);
    }

    public void unloadAllBuffers() {
        idToSpriteBufferMap = new HashMap<>();
    }

    public void unloadBuffer(String id) {
        idToSpriteBufferMap.remove(id);
    }
}