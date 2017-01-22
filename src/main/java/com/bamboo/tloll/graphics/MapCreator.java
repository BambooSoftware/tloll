package com.bamboo.tloll.graphics;

import com.bamboo.tloll.graphics.structure.Tile;

import java.util.List;
import java.util.ArrayList;

public class MapCreator
{

    public MapCreator()
    {
    }

    // Method for creating a basic map.
    public static List<Tile> createSampleMapLowerLeft()
    {
	List<Tile> tileMap = new ArrayList<Tile>();
	Tile tile;

	int tileCounter = 0;
	
	for (int i = 0; i < 8; i++)
	    {
		for (int j = 0; j < 8; j++)
		    {
			// float posX = i * 64.0f;
			// float posY = j * 64.0f;
			float posX = 64.0f * (int) (tileCounter / 8);
			float posY = 64.0f * (tileCounter % 8);
			if (i == 0 || j == 0)
			    {
				// One Tile layer of water.
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 5, tileCounter);
			    }
			else if (i == 1 && j != 1)
			    {
				// Left
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 1, tileCounter);
			    }
			else if (i != 1 && j == 1)
			    {
				// Bottom
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 4, tileCounter);
			    }
			else if (i == 1 && j == 1)
			    {
				// Corner piece.
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 6, tileCounter);
			    }
			else if (i == 4 && j == 4)
			    {
				// Stairs
				tile = new Tile(posX, posY, 64.0f, 64.0f, true, 10, tileCounter);
			    }
			else
			    {
				// Everything else is grass.
				tile = new Tile(posX, posY, 64.0f, 64.0f, true, 0, tileCounter);
			    }
			tileMap.add(tile);
			tileCounter++;
		    }
	    }

	return tileMap;
    }

    // Method for creating a basic map.
    public static List<Tile> createSampleMapLowerRight()
    {
	List<Tile> tileMap = new ArrayList<Tile>();
	Tile tile;

	int tileCounter = 0;
	
	for (int i = 0; i < 8; i++)
	    {
		for (int j = 0; j < 8; j++)
		    {
			// float posX = i * 64.0f;
			// float posY = j * 64.0f;
			float posX = 64.0f * (int) (tileCounter / 8);
			float posY = 64.0f * (tileCounter % 8);
			if (i == 7 || j == 0)
			    {
				// One Tile layer of water.
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 5, tileCounter);
			    }
			else if (i == 6 && j != 1)
			    {
				// Right
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 3, tileCounter);
			    }
			else if (j == 1 && i != 6)
			    {
				// Bottom
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 4, tileCounter);
			    }
			else if (j == 1 && i == 6)
			    {
				// Corner piece.
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 9, tileCounter);
			    }
			else
			    {
				// Everything else is grass.
				tile = new Tile(posX, posY, 64.0f, 64.0f, true, 0, tileCounter);
			    }
			tileMap.add(tile);
			tileCounter++;
		    }
	    }

	return tileMap;
    }

    // Method for creating a basic map.
    public static List<Tile> createSampleMapUpperLeft()
    {
	List<Tile> tileMap = new ArrayList<Tile>();
	Tile tile;

	int tileCounter = 0;
	
	for (int i = 0; i < 8; i++)
	    {
		for (int j = 0; j < 8; j++)
		    {
			// float posX = i * 64.0f;
			// float posY = j * 64.0f;
			float posX = 64.0f * (int) (tileCounter / 8);
			float posY = 64.0f * (tileCounter % 8);
			if (i == 0 || j == 7)
			    {
				// One Tile layer of water.
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 5, tileCounter);
			    }
			else if (i == 1 && j != 6)
			    {
				// Left
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 1, tileCounter);
			    }
			else if (i != 1 && j == 6)
			    {
				// Top
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 2, tileCounter);
			    }
			else if (1 == 1 && j == 6)
			    {
				// Corner piece.
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 7, tileCounter);
			    }
			else
			    {
				// Everything else is grass.
				tile = new Tile(posX, posY, 64.0f, 64.0f, true, 0, tileCounter);
			    }
			tileMap.add(tile);
			tileCounter++;
		    }
	    }

	return tileMap;
    }

    // Method for creating a basic map.
    public static List<Tile> createSampleMapUpperRight()
    {
	List<Tile> tileMap = new ArrayList<Tile>();
	Tile tile;

	int tileCounter = 0;
	
	for (int i = 0; i < 8; i++)
	    {
		for (int j = 0; j < 8; j++)
		    {
			// float posX = i * 64.0f;
			// float posY = j * 64.0f;
			float posX = 64.0f * (int) (tileCounter / 8);
			float posY = 64.0f * (tileCounter % 8);
			if (i == 7 || j == 7)
			    {
				// One Tile layer of water.
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 5, tileCounter);
			    }
			else if (i == 6 && j != 6)
			    {
				// Right
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 3, tileCounter);
			    }
			else if (i != 6 && j == 6)
			    {
				// Top
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 2, tileCounter);
			    }
			else if (i == 6 && j == 6)
			    {
				// Corner piece.
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 8, tileCounter);
			    }
			else
			    {
				// Everything else is grass.
				tile = new Tile(posX, posY, 64.0f, 64.0f, true, 0, tileCounter);
			    }
			tileMap.add(tile);
			tileCounter++;
		    }
	    }

	return tileMap;
    }

    public static List<Tile> createSampleMapLeftRight()
    {
	List<Tile> tileMap = new ArrayList<Tile>();
	Tile tile;

	int tileCounter = 0;
	
	for (int i = 0; i < 8; i++)
	    {
		for (int j = 0; j < 8; j++)
		    {
			// float posX = i * 64.0f;
			// float posY = j * 64.0f;
			float posX = 64.0f * (int) (tileCounter / 8);
			float posY = 64.0f * (tileCounter % 8);
			if (j == 0 || j == 7)
			    {
				// One Tile layer of water.
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 5, tileCounter);
			    }
			else if (j == 6)
			    {
				// Top
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 2, tileCounter);
			    }
			else if (j == 1)
			    {
				// Bottom
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 4, tileCounter);
			    }
			else
			    {
				// Everything else is grass.
				tile = new Tile(posX, posY, 64.0f, 64.0f, true, 0, tileCounter);
			    }
			tileMap.add(tile);
			tileCounter++;
		    }
	    }
	return tileMap;
    }

    public static List<Tile> createSampleMapTopDown()
    {
	List<Tile> tileMap = new ArrayList<Tile>();
	Tile tile;

	int tileCounter = 0;
	
	for (int i = 0; i < 8; i++)
	    {
		for (int j = 0; j < 8; j++)
		    {
			// float posX = i * 64.0f;
			// float posY = j * 64.0f;
			float posX = 64.0f * (int) (tileCounter / 8);
			float posY = 64.0f * (tileCounter % 8);
			if (i == 0 || i == 7)
			    {
				// One Tile layer of water.
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 5, tileCounter);
			    }
			else if (i == 6)
			    {
				// Right
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 3, tileCounter);
			    }
			else if (i == 1)
			    {
				// Left
				tile = new Tile(posX, posY, 64.0f, 64.0f, false, 1, tileCounter);
			    }
			else
			    {
				// Everything else is grass.
				tile = new Tile(posX, posY, 64.0f, 64.0f, true, 0, tileCounter);
			    }
			tileMap.add(tile);
			tileCounter++;
		    }
	    }
	return tileMap;
    }
    
    // Method for more advanced map creation.
    public void createMap()
    {
	
    }
    
}
