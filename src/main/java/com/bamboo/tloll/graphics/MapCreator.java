package com.bamboo.tloll.graphics;

import java.util.List;
import java.util.ArrayList;

public class MapCreator
{

    public MapCreator()
    {
    }

    // Method for creating a basic map.
    public static List<Sprite> createSampleMap()
    {
	List<Sprite> tileMap = new ArrayList<Sprite>();
	Sprite sprite;
	
	for (int i = 0; i < 64; i++)
	    {
		sprite = new Sprite(0.0f, 0.0f, 64.0f, 64.0f);
		tileMap.add(sprite);
	    }

	return tileMap;
    }

    // Method for more advanced map creation.
    public void createMap()
    {
	
    }
    
}
