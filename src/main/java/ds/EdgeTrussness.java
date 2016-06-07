package ds;

import java.util.Hashtable;

import org.jgrapht.graph.DefaultEdge;


/**
 * It is a index that contains edges and their trussness
 * @author luchen
 *
 */
public class EdgeTrussness {

	//
	public Hashtable<DefaultEdge,Integer> et;
	
	public EdgeTrussness(){
		
		et = new Hashtable<>();
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
	
	
}
