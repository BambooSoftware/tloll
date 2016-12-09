package com.bamboo.tloll.graphics.structure;

import org.jgrapht.DirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

public class WorldMap {


    DirectedGraph<MapPiece, DefaultEdge> worldMap;

    public void WorldMap() {

        worldMap = new DirectedMultigraph<>();

        MapPiece mp1 = new MapPiece();
        MapPiece mp2 = new MapPiece();
        MapPiece mp3 = new MapPiece();
        MapPiece mp4 = new MapPiece();


        worldMap.addVertex(mp1);
        worldMap.addVertex(mp2);
        worldMap.addVertex(mp3);
        worldMap.addVertex(mp4);

        worldMap.addEdge(mp1, mp2);

        //https://github.com/jgrapht/jgrapht/wiki/HelloWorld

    }


}