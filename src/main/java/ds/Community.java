package ds;

import jgraphtResearch.Vertex;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Community {
	public UndirectedGraph<Vertex, DefaultEdge> com;
	
	
	
	public Community(){
		com = new SimpleGraph<Vertex, DefaultEdge>(DefaultEdge.class);
	}
	
	
	
	public String toString(){
		
		return com.toString();
	}
}
