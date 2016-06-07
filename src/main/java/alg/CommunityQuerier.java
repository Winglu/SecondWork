package alg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;

import ds.EdgeSupport;
import ds.EdgeTrussness;
import ds.TrussCommunity;
import jgraphtResearch.Vertex;

public class CommunityQuerier {

	EdgeTrussness et;
	public  UndirectedGraph<Vertex, DefaultEdge> g;
	
	public CommunityQuerier(EdgeTrussness et, UndirectedGraph<Vertex, DefaultEdge> g){
		this.et = et;
		this.g =g;
	}
	
	
	public void queryCommunityByVertex(Vertex v,int k){
		HashSet<DefaultEdge> visited = new HashSet<>();
		NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
		ArrayList<DefaultEdge> nov = new ArrayList<>(g.edgesOf(v));
	
		System.out.println(et.et);
		
		for(DefaultEdge ce:nov){
			System.out.println(ce);
			int ceSupport = et.et.get(ce);
			if(ceSupport>=k && !visited.contains(visited)){
				//need a data structure to store the k-truss community
				//the bfs goes here
				Queue<DefaultEdge> q = new LinkedBlockingQueue<>();
				TrussCommunity tc = new TrussCommunity();
				q.add(ce);
				visited.add(ce);
				
				
				//reducing creation of variables
				Vertex sv;
				Vertex tv;
				ArrayList<Vertex> svn = new ArrayList<>();
				ArrayList<Vertex> tvn = new ArrayList<>();
				while(!q.isEmpty()){
					DefaultEdge e = q.poll();
					tc.tc.add(e);
					
					sv = g.getEdgeSource(e);
					tv = g.getEdgeTarget(e);
					svn.addAll(ni.neighborsOf(sv));
					tvn.addAll(ni.neighborsOf(tv)); 
					svn.retainAll(tvn);
					
					for(Vertex vn:svn){
						DefaultEdge  aes = g.getEdge(sv,vn);
						DefaultEdge  ats = g.getEdge(tv,vn);
						
						if(aes!=null && ats!=null){
							if(et.et.get(aes)>=k && et.et.get(ats)>=k){
								if(!visited.contains(aes)){
									q.add(aes);
									visited.add(aes);
								}
								if(!visited.contains(ats)){
									q.add(ats);
									visited.add(ats);
								}
							}
						}
					}
				}
				//debug only
				System.out.println(tc);
				
			}
			
		}
		
		
	}
	
	
}
