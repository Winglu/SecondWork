package jgraphtResearch;

public class Vertex {
	public String id;
	
	
	
	/*
	 * Override equals
	 * 
	 * ensure that a vertex is identified by id
	 */
	public Vertex(String s){
		
		this.id = s;
	}
	
	public boolean equals(Object o){
		if(this==o){
			
			return true;
		}
		
		if(!(o instanceof Vertex)){
			
			return false;
		}
		//System.out.println(this.id.equals(((Vertex)o).id));
		return this.id.equals(((Vertex)o).id);
		
	}
	
	/*
	 * @Override
	 * 
	 */
	public int hashCode(){
		return this.id.hashCode();
		
	}
	
	
	public String toString(){
		
		return this.id;
	}
	
}
