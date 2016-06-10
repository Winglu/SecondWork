package ds;

import java.util.ArrayList;
import java.util.HashSet;

import org.jgrapht.graph.DefaultEdge;

public class TrussCommunity {
	public HashSet<DefaultEdge> tc;
	
	public TrussCommunity(){
		tc = new HashSet<>();
		
	}
	
	
	
	
	public String toString(){
		
		return tc.toString();
	}
	
	
}
