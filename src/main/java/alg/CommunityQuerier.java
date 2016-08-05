package alg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;





import ds.EdgeDirected;
//import ds.EdgeSupport;
import ds.EdgeTrussness;
import ds.IndexUnit;
import ds.TrussCommunity;
import jgraphtResearch.Vertex;

public class CommunityQuerier {

	
	public  UndirectedGraph<Vertex, DefaultEdge> g;
	
	//first index
	public EdgeTrussness et;
	
	//second index
	public LinkedHashMap<Vertex,IndexUnit> TCPIndex;
	
	public CommunityQuerier(EdgeTrussness et, UndirectedGraph<Vertex, DefaultEdge> g){
		this.et = et;
		this.g =g;
		
	}
	
	
	public CommunityQuerier(LinkedHashMap<Vertex,IndexUnit> TCPIndex, UndirectedGraph<Vertex, DefaultEdge> g){
		//this.et = et;
		this.TCPIndex = TCPIndex;
		this.g =g;
		
	}
	
	
	
	
	
	/*
	 * In this solution, since the edge need to be reversed, so the edge direction is important
	 */
	public void queryComminityByVertexTCP(Vertex v, int k){
		//Initialization 
		HashSet<EdgeDirected> visited = new HashSet<>();
		NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
		//ArrayList<DefaultEdge> nov = new ArrayList<>(g.edgesOf(v));
		List<Vertex> nov = ni.neighborListOf(v);
		System.out.println(g.edgeSet()+"~~~");
		for(Vertex nv:nov){
			DefaultEdge e = g.getEdge(v, nv);
			EdgeDirected dde = new EdgeDirected(v,nv);
			if(et.et.get(e)>=k && !visited.contains(dde)){
				
				TrussCommunity tc = new TrussCommunity();
				Queue<EdgeDirected> q = new LinkedBlockingQueue<>();
				EdgeDirected de = new EdgeDirected(v,nv);
				q.add(de);
				while(!q.isEmpty()){
					EdgeDirected cde = q.poll();
					DefaultEdge ce = g.getEdge(cde.s, cde.t);
					
					
					if(visited.contains(ce)!=true){
						IndexUnit iu = TCPIndex.get(cde.s);
						ArrayList<Vertex> vlLK =  iu.getVertexesLagerThanK(k,cde.t);
						
						for(Vertex vvv:vlLK){
							DefaultEdge pe = g.getEdge(cde.s, vvv);
							EdgeDirected eee = new EdgeDirected(cde.s,vvv);
							visited.add(eee);
							tc.tc.add(pe);
							
							EdgeDirected rde = new EdgeDirected(vvv,cde.s);
							if(visited.contains(rde)!=true){
								q.add(rde);
							}
						}
						
					}
				}
				System.out.println(tc.toString());
			}
			
			
		}
		
		
//		for(DefaultEdge ce:nov){
//			int ceSupport = et.et.get(ce);
//			if(ceSupport>=k && !visited.contains(ce)){
//				
//				Queue<DefaultEdge> q = new LinkedBlockingQueue<>();
//				TrussCommunity tc = new TrussCommunity();
//				q.add(ce);
//				//visited.add(ce);
//				
//				
//				
//				while(!q.isEmpty()){
//					DefaultEdge e = q.poll();
//					if(visited.contains(ce)==false){
//						
//						//get all vertexes with whose edges has trussness larger than k
//						//ArrayList<DefaultEdge> el = Tc
//						
//						
//					}
//					
//				}
//				
//				
//			}
//			
//		}
		
	}
	
	
	
	
	public void queryCommunityByVertex(Vertex v,int k){
		HashSet<DefaultEdge> visited = new HashSet<>();
		NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
		ArrayList<DefaultEdge> nov = new ArrayList<>(g.edgesOf(v));
	
		//System.out.println(et.et);
		
		for(DefaultEdge ce:nov){
			//System.out.println(ce);
			int ceSupport = et.et.get(ce);
			if(ceSupport>=k && !visited.contains(ce)){
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
					svn.clear();
					tvn.clear();
				}
				//debug only
				System.out.println(tc);
				
			}
			
		}
		
		
	}
	
	
}
