package ds;

import java.util.Hashtable;

import org.jgrapht.graph.DefaultEdge;


/**
 * It is a index that contains edges and their trussness
 * @author luchen
 *
 */
public class EdgeTrussness {

	 
	public Hashtable<DefaultEdge,Integer> et;
	//since the graph is modified we may need use string rather than object as the key of hash table
	public Hashtable<String,Integer> ets;
	public EdgeTrussness(){
		
		et = new Hashtable<>();
		ets = new Hashtable<>();
	}
	
	
	
	/**
	 * Insert a trussness into the index
	 * @param e
	 * @param trussness
	 */
	public void insertEdgeTrussness(DefaultEdge e, Integer trussness){
		et.put(e, trussness);
		
	}
	
	/**
	 * Get the trussness of a edge
	 * @param e
	 * @return
	 */
	public Integer getEdgeTrussness(DefaultEdge e){
		
		return et.get(e);
		
	}
	
	public String toString(){
		
		return "size "+et.size()+","+et.toString();
	}
	
	
}
