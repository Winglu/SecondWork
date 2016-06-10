package ds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import jgraphtResearch.Vertex;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class IndexUnit {
	public UndirectedGraph<Vertex, DefaultEdge> gv;
	
	public LinkedHashMap<DefaultEdge,Integer> triTruss;
	
	public IndexUnit(){
		gv = new SimpleGraph<Vertex, DefaultEdge>(DefaultEdge.class);
		triTruss = new LinkedHashMap<>();
		
	}
	
	
	/**
	 * to support the community querying
	 * @param k
	 * @return
	 */
	
	public ArrayList<DefaultEdge> getEdgesLagerThanK(int k){
		ArrayList<DefaultEdge> el = new ArrayList<>();
		
		Set<DefaultEdge> es = gv.edgeSet();
		for(DefaultEdge e: es){
			if(triTruss.get(e)>=k){
				el.add(e);
			}
		}
		
		return el;
	}
	
	public ArrayList<Vertex> getVertexesLagerThanK(int k, Vertex s){
		//HashSet<Vertex> uv = new HashSet<>();
		ArrayList<Vertex> vl = new ArrayList<>();
		
		
		
		Set<Vertex> vs = gv.vertexSet();
		for(Vertex v: vs){
			if(!v.equals(s)){
				DijkstraShortestPath<Vertex,DefaultEdge> dj = new DijkstraShortestPath<>(gv,s,v);
				List<DefaultEdge> el = dj.getPathEdgeList();
				if(el!=null){
					int flg = 1;
					for(DefaultEdge e:el){
						if(triTruss.get(e)<k){
							flg =0;
						}
					}
					if(flg==1){
						vl.add(v);
					}
					
				}
				

				
				
			}
		}
		return vl;
	}
	
	
	
	public String toString(){
		
		return gv.toString();
	}
}
