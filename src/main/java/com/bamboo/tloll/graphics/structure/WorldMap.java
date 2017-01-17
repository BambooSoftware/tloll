package com.bamboo.tloll.graphics.structure;

import org.jgrapht.DirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.*;

import java.util.List;

public class WorldMap {

    DirectedGraph<Scene, DefaultEdge> worldMap;

    Scene currentScene;
    List<Scene> currentSceneConnections;

    public void WorldMap() {

        worldMap = new DirectedMultigraph<>(DefaultEdge.class);
        Scene lowerLeft = new Scene();
        Scene middleBottom = new Scene();
        Scene lowerRight = new Scene();
        Scene middleRight = new Scene();
	Scene upperRight = new Scene();
	Scene middleTop = new Scene();
	Scene upperLeft = new Scene();
	Scene middleLeft = new Scene();

        //Adds each vertex (scene) to the map
        worldMap.addVertex(lowerLeft);
	worldMap.addVertex(middleBottom);
        worldMap.addVertex(lowerRight);
        worldMap.addVertex(middleRight);
	worldMap.addVertex(upperRight);
	worldMap.addVertex(middleTop);
	worldMap.addVertex(upperLeft);
	worldMap.addVertex(middleLeft);

        //Creates edges between scenes, each edge can go only one direction but a single vertex may have multiple edges
        worldMap.addEdge(lowerLeft, middleBottom); 



        //https://github.com/jgrapht/jgrapht/wiki/HelloWorld

    }


}
