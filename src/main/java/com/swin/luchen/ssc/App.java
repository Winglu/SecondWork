package com.swin.luchen.ssc;

import java.util.ArrayList;

import org.jgrapht.graph.DefaultEdge;

import alg.CommunityQuerier;

//import java.util.Hashtable;

//import java.util.List;

//import org.jgrapht.alg.DijkstraShortestPath;
//import java.util.List;
//import java.util.Set;

//import org.jgrapht.alg.NeighborIndex;
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.traverse.BreadthFirstIterator;

import alg.TCPIndexing;
import alg.TrussDecomposition;
import jgraphtResearch.Graph;
import jgraphtResearch.Vertex;
import utility.FileReader;
//import jgraphtResearch.Graph;
//import jgraphtResearch.Vertex;





public class App 
{
    public static void main(String[] args )
    {
    	
    	
    	
    	/*Vertex v1 = new Vertex("1");
    	Vertex v2 = new Vertex("2");
    	Graph.uGraph.addVertex(v1);
    	Graph.uGraph.addVertex(v2);
    	Graph.uGraph.addEdge(v1, v2);
    	
    	DefaultEdge e1 = Graph.uGraph.getEdge(v1, v2);
    	DefaultEdge e2 = Graph.uGraph.getEdge(v1, v2);
    	//Graph.uGraph.removeEdge(e1);	
    	//Graph.uGraph.addEdge(v1, v2);
    	//DefaultEdge e2 = Graph.uGraph.getEdge(v1, v2);
    	
    	System.out.println(e1.equals(e2));*/
    	
    	
/*    	FileReader.readAdjacentList();
        TrussDecomposition td = new TrussDecomposition(Graph.uGraph);
        td.trussDecomposition();
        TCPIndexing idex = new TCPIndexing(Graph.uGraph,td.et);
        idex.TCPIndexConstruction();
        FileReader.readAdjacentList();
        CommunityQuerier cq = new CommunityQuerier(td.et,Graph.uGraph);
        cq.TCPIndex = idex.TCPindex;
    	cq.queryComminityByVertexTCP(new Vertex("q"), 4);*/
    	
        FileReader.readCoords();

        

    }
}
