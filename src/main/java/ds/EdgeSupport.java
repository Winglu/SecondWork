package ds;


import org.jgrapht.graph.DefaultEdge;


/**
 * 
 * @author luchen
 *
 */

public class EdgeSupport {

	public DefaultEdge e;
	public Integer support;
	
	
	public boolean equals(Object o){
		if(this==o){
			
			return true;
		}
		
		if(!(o instanceof EdgeSupport)){
			
			return false;
		}
		//System.out.println(this.id.equals(((Vertex)o).id));
		return this.e.equals(((EdgeSupport)o).e);
		
	}
	
	/*
	 * @Override
	 * 
	 */
	public int hashCode(){
		return this.e.hashCode();
	}
}
