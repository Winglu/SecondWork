package ds;

import jgraphtResearch.Vertex;

public class EdgeDirected {
	public Vertex s;
	public Vertex t;
	
	public String id;
	
	public EdgeDirected(Vertex s, Vertex t){
		this.s = s;
		this.t = t;
		id=s.id+t.id;
	}
	
	
	
	public boolean equals(Object o){
		if(this==o){
			
			return true;
		}
		
		if(!(o instanceof EdgeDirected)){
			
			return false;
		}
		//System.out.println(this.id.equals(((Vertex)o).id));
		return this.id.equals(((EdgeDirected)o).id);
		
	}
	
	
	
	public int hashCode(){
		return this.id.hashCode();
		
	}
	
	
	public String toString(){
		
		return this.id;
	}
}
