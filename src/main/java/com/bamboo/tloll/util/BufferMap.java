package com.bamboo.tloll.util;

import com.bamboo.tloll.constants.Constants;
import com.bamboo.tloll.constants.JsonConstants;
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

        try  {
            byte[] contents = Files.readAllBytes(Paths.get(Constants.USER_DIR + JsonConstants.BUFFER_MAP_PATH));
            JSONObject jsonObject = new JSONObject(new String(contents));

            JSONArray bufferMapConfig = jsonObject.getJSONArray(JsonConstants.BUFFER_MAP);
            for(int i = 0; i < bufferMapConfig.length(); ++i ) {
                JSONObject bufferItem = bufferMapConfig.getJSONObject(i);
                String id = bufferItem.getString(JsonConstants.ID);
                String path = bufferItem.getString(JsonConstants.PATH);
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

    public SpriteBuffer getSpriteBuffer(String id, int width, int height) {
        GraphicsUtil gu = GraphicsUtil.getInstance();
        if(idToSpriteBufferMap.get(id) == null) {
	    System.out.println(Constants.USER_DIR + idToPathBufferMap.get(id));
            SpriteBuffer sBuffer = new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + idToPathBufferMap.get(id)), height, width);
            idToSpriteBufferMap.put(id, sBuffer);
        }

        return idToSpriteBufferMap.get(id);
    }

    public SpriteBuffer getSpriteBuffer(String id, int width, int height, char aChar) {
        GraphicsUtil gu = GraphicsUtil.getInstance();
        if(idToSpriteBufferMap.get(String.valueOf(aChar)) == null) {
            SpriteBuffer sBuffer = new SpriteBuffer(gu.loadTexture(Constants.USER_DIR + idToPathBufferMap.get(id) + aChar + ".png"), height, width);
            idToSpriteBufferMap.put(String.valueOf(aChar), sBuffer);
        }

        return idToSpriteBufferMap.get(String.valueOf(aChar));
	}

    public void unloadAllBuffers() {
        idToSpriteBufferMap = new HashMap<>();
    }

    public void unloadBuffer(String id) {
        idToSpriteBufferMap.remove(id);
    }
}
