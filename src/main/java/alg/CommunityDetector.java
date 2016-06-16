package alg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import jgraphtResearch.Vertex;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;

import ds.Community;
import ds.EdgeTrussness;

public class CommunityDetector {
	
	

	public EdgeTrussness et;
	//public  UndirectedGraph<Vertex, DefaultEdge> g;
	
	public ArrayList<Community> comList;
	
	
	public CommunityDetector(){
		
		comList = new ArrayList<>();
		
	}
	
	
	
	/**
	 * 
	 * @param g the graph
	 * @param k the defined k
	 */
	public void detectCommunityOnGraph(UndirectedGraph<Vertex, DefaultEdge> g, int k){
		
		
		//each edge can only appear in a k-truss community
		//so each edge should be visited only once
		HashSet<DefaultEdge> visitedEdges = new HashSet<>();
		
		//build vertex adjacent index
		NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
		
		for(DefaultEdge e :g.edgeSet()){
			
			if(et.et.get(e)>=k){
				if(visitedEdges.contains(e)!=true){
				
					Queue<DefaultEdge> q = new LinkedBlockingQueue<>();
					Community com = new Community();
					q.add(e);
					visitedEdges.add(e);
					
					Vertex sv;
					Vertex tv;
					ArrayList<Vertex> svn = new ArrayList<>();
					ArrayList<Vertex> tvn = new ArrayList<>();
					while(q.size()>0){
						DefaultEdge pEdge = q.poll();//pEdge means the edge now is processing
						//add the current edge into the community
						
						sv = g.getEdgeSource(pEdge);
						tv = g.getEdgeTarget(pEdge);
						com.com.addVertex(sv);
						com.com.addVertex(tv);
						com.com.addEdge(sv, tv, pEdge);
						//
						svn.addAll(ni.neighborsOf(sv));
						tvn.addAll(ni.neighborsOf(tv)); 
						svn.retainAll(tvn);
						for(Vertex vn:svn){
							
							DefaultEdge  aes = g.getEdge(sv,vn);
							DefaultEdge  ats = g.getEdge(tv,vn);
							
							if(aes!=null && ats!=null){
								
								if(et.et.get(aes)>=k && et.et.get(ats)>=k){
									if(!visitedEdges.contains(aes)){
										q.add(aes);
										visitedEdges.add(aes);
									}
									if(!visitedEdges.contains(ats)){
										q.add(ats);
										visitedEdges.add(ats);
									}
								}
								
							}
						}
						svn.clear();
						tvn.clear();
					}
					comList.add(com);
				}
				
			}
			
		}
		
		
	}
	
}
