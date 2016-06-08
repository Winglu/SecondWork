package ds;

import java.util.ArrayList;

import org.jgrapht.graph.DefaultEdge;

public class TrussCommunity {
	public ArrayList<DefaultEdge> tc;
	
	public TrussCommunity(){
		tc = new ArrayList<>();
		
	}
	
	
	
	
	public String toString(){
		
		return tc.toString();
	}
	
	
}
