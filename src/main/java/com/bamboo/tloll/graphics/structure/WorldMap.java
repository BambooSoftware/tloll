package com.bamboo.tloll.graphics.structure;

import org.jgrapht.DirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.*;

import java.util.List;

public class WorldMap {

    DirectedGraph<MapPiece, DefaultEdge> worldMap;

    MapPiece currentScene;
    List<MapPiece> currentSceneConnections;

    public void WorldMap() {

        worldMap = new DirectedMultigraph<>(DefaultEdge.class);

        MapPiece mp1 = new MapPiece(); //BOTTOM
        MapPiece mp2 = new MapPiece(); //LEFT
        MapPiece mp3 = new MapPiece(); //TOP
        MapPiece mp4 = new MapPiece(); //RIGHT
        MapPiece mp5 = new MapPiece(); //MIDDLE

        //Adds each vertex (scene) to the map
        worldMap.addVertex(mp1);
        worldMap.addVertex(mp2);
        worldMap.addVertex(mp3);
        worldMap.addVertex(mp4);
        worldMap.addVertex(mp5);

        //Creates edges between scenes, each edge can go only one direction but a single vertex may have multiple edges
        worldMap.addEdge(mp1, mp5); //
        worldMap.addEdge(mp2, mp5); //
        worldMap.addEdge(mp3, mp5); //
        worldMap.addEdge(mp4, mp5); //
        worldMap.addEdge(mp5, mp1); //
        worldMap.addEdge(mp5, mp2); //
        worldMap.addEdge(mp5, mp3); //
        worldMap.addEdge(mp5, mp4); //



        //https://github.com/jgrapht/jgrapht/wiki/HelloWorld

    }


}