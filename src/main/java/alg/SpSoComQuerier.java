package alg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import jgraphtResearch.Vertex;
import ds.Community;
import ds.DistanceIndex;
import ds.EdgeTrussness;
import ds.SpatialVertexPair;

public class SpSoComQuerier {
	
	public EdgeTrussness et;
	
	//distIndex
	//it can be get from a static class directly
	
	
	public ArrayList<Community> comList;
	
	Queue<SpatialVertexPair> feq;
	ArrayList<SpatialVertexPair> fel;
	
	
	public void test(Community com, double d, int k){
		
		verifyingCommunityDistance(com,d);
		ArrayList<UndirectedGraph<Vertex, DefaultEdge>> gl = spSoComDetectionFramework(com);
		
		for(UndirectedGraph<Vertex, DefaultEdge> cg:gl){
			
			TrussDecomposition td = new TrussDecomposition(cg);
			td.trussDecomposition();
			CommunityDetector cd = new CommunityDetector();
			
			cd.et = td.et;
			cd.detectCommunityOnGraph(cg, k);
			System.out.println(cd.comList);
		}
	}
	
	
	public void verifyingCommunityDistance(Community com, double d){
		
		//
		feq = new LinkedBlockingQueue<>();
		fel = new ArrayList<>();
		
		//get all vertexes
		HashMap<String,Double> distIndex = DistanceIndex.distIndex;
		ArrayList<Vertex> comVertexes = new ArrayList<>(com.com.vertexSet());
		for(int i=0; i<comVertexes.size(); i++){
			for(int j=i+1; j<comVertexes.size(); j++){
				String v1id = comVertexes.get(i).id;
				String v2id = comVertexes.get(j).id;
				String keyOfDist = v1id+v2id;
				if(distIndex.get(keyOfDist)>d){
					SpatialVertexPair svp = new SpatialVertexPair();
					svp.v1 = comVertexes.get(i);
					svp.v2 = comVertexes.get(j);
					fel.add(svp);
					feq.add(svp);
					//System.out.println(svp+" dist "+ distIndex.get(keyOfDist));
				}
			}
		}
		
	}
	
	
	/**
	 * only focus on a k-truss community
	 * @param com
	 * @param eTR
	 */
	public ArrayList<UndirectedGraph<Vertex, DefaultEdge>> spSoComDetectionFramework(Community com){ //eTR:edges to remove
		
		ArrayList<UndirectedGraph<Vertex, DefaultEdge>> gl = new ArrayList<>();
		gl.add(com.com);
		//after this iteration, all subgraphs of the community that meet the spatial constraint are generated
		while(feq.size()!=0){
			
			ArrayList< UndirectedGraph<Vertex, DefaultEdge>> ngl = new ArrayList<>();
			SpatialVertexPair svp = feq.poll();
			
			for(UndirectedGraph<Vertex, DefaultEdge> sug:gl){
				//remove vertex 1 and generate graph 1
				UndirectedGraph<Vertex, DefaultEdge> g1 = new SimpleGraph<Vertex, DefaultEdge>(DefaultEdge.class);
				newGraphByRemoveAVertex(sug,g1,svp.v1);
				//remove vertex 2 and generate graph 2
				UndirectedGraph<Vertex, DefaultEdge> g2 = new SimpleGraph<Vertex, DefaultEdge>(DefaultEdge.class);
				newGraphByRemoveAVertex(sug,g2,svp.v2);
				ngl.add(g1);
				ngl.add(g2);
			}
			
			gl = ngl;
			
		}	
		
		//debug purpose only
		return gl;
	}
	
	
	private void newGraphByRemoveAVertex(UndirectedGraph<Vertex, DefaultEdge>og,
			UndirectedGraph<Vertex, DefaultEdge>ng,
			Vertex v){
		
		
		if(og.containsVertex(v)==true){
			Set<DefaultEdge> edgesOfV = og.edgesOf(v);
			HashSet<DefaultEdge> edgesOfVH = new HashSet<>(edgesOfV);

			for(Vertex nv:og.vertexSet()){
				if(nv.equals(v)==false){
					ng.addVertex(nv);
				}
				
			}
			
			for(DefaultEdge ne:og.edgeSet()){
				if(edgesOfVH.contains(ne)!=true){
					ng.addEdge(og.getEdgeSource(ne), og.getEdgeTarget(ne), ne);
				}
				
				
			}
		}else{
			
			//Set<DefaultEdge> edgesOfV = og.edgesOf(v);
			//HashSet<DefaultEdge> edgesOfVH = new HashSet<>(edgesOfV);
			
			for(Vertex nv:og.vertexSet()){
				
				ng.addVertex(nv);
				
				
			}
			
			for(DefaultEdge ne:og.edgeSet()){
				ng.addEdge(og.getEdgeSource(ne), og.getEdgeTarget(ne), ne);

			}
			
		}
		
	}
}
