package jgraphtResearch;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Graph {

	//There should be only 1 graph in the memory
	public static UndirectedGraph<Vertex, DefaultEdge> uGraph = new SimpleGraph<Vertex, DefaultEdge>(DefaultEdge.class);

	
	
	public static void addVertexToGraph(Vertex v){	
		
		uGraph.addVertex(v);
	}
	
	
	public static void addEdgeToGraph(Vertex vs, Vertex vd){
		//may have perfomance issue
		addVertexToGraph(vs);
		addVertexToGraph(vd);
		
		uGraph.addEdge(vs, vd);
		//uGraph.addEdge(vd, vs);
		
	}
	
	
	
}
