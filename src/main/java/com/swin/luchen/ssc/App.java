package com.swin.luchen.ssc;

import java.util.Hashtable;

//import java.util.List;

//import org.jgrapht.alg.DijkstraShortestPath;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

import alg.TrussDecomposition;
import jgraphtResearch.Graph;
import utility.FileReader;
//import jgraphtResearch.Graph;
import jgraphtResearch.Vertex;





public class App 
{
    public static void main(String[] args )
    {
    	
        FileReader.readAdjacentList();
        
        TrussDecomposition td = new TrussDecomposition(Graph.uGraph);
        
        td.trussDecomposition();
        
        
        
  
        
        
        //Set<DefaultEdge> s = Graph.uGraph.edgeSet();
       /*NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(Graph.uGraph);
       List<Vertex> l = ni.neighborListOf(new Vertex("t"));
       List<Vertex> l2 = ni.neighborListOf(new Vertex("p1"));
       System.out.println(l);
       System.out.println(l2);
       l.retainAll(l2);
       System.out.println(l);
       System.out.println(ni.neighborListOf(new Vertex("t")));*/
        
        
        //System.out.println(Graph.uGraph);
       //System.out.println(Graph.uGraph.getEdge(new Vertex("t"), new Vertex("p1")));
        
    }
}
