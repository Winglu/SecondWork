package alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jgraphtResearch.Vertex;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import ds.EdgeTrussness;
import ds.IndexUnit;

public class TCPIndexing {
	public  UndirectedGraph<Vertex, DefaultEdge> g;
	
	//need support of trussness
	EdgeTrussness et;
	//need neighborIndex
	NeighborIndex<Vertex, DefaultEdge> ni;
	public LinkedHashMap<Vertex,IndexUnit> TCPindex;
	
	public TCPIndexing(UndirectedGraph<Vertex, DefaultEdge> g, EdgeTrussness et){
		this.g = g;
		this.et = et;
		ni = new NeighborIndex<>(this.g);
		TCPindex = new LinkedHashMap<>(); 
	}
	
	
	
	
	public void TCPIndexConstruction(){
		ArrayList<Vertex> vl = new ArrayList<>(g.vertexSet());
		
		
		//create index for each vertex
		
		LinkedHashMap<Vertex,LinkedHashSet<DefaultEdge>> indexGraphs = new LinkedHashMap<>(); 
		//LinkedHashMap<Vertex,IndexUnit> TCPindex = new LinkedHashMap<>(); 
		
		
		
		
		for(Vertex v:vl){
			//collect all edges such that edges are in the triangles with vertex v and are not connected to v
			//need data structure to store the edge. 
			IndexUnit iu = new IndexUnit();
			UndirectedGraph<Vertex, DefaultEdge> gv = new SimpleGraph<Vertex, DefaultEdge>(DefaultEdge.class);
			LinkedHashSet<DefaultEdge> eov = new LinkedHashSet<>();
			edgesAgainstV(v,eov,gv);
			//System.out.println(gv.vertexSet());
			
			indexGraphs.put(v,eov);
			//continue play with eov
			LinkedHashMap<DefaultEdge,Integer> triTruss = new LinkedHashMap<>();
			//compute necessary statistics 
			int maxTruss =0;
			for(DefaultEdge e:eov){
				//this edge's vertexes: s and t, and with v form a triangle
				//compute the maximal k that this triangle might be contained in the k-truss communitiy
				//assign the k to this edge
				DefaultEdge e1 = e;
				int minTruss = et.et.get(e);
				Vertex s = g.getEdgeSource(e1);
				Vertex t = g.getEdgeSource(e1);
				DefaultEdge e2 = g.getEdge(v, s);
				if(minTruss>et.et.get(e2)){
					minTruss = et.et.get(e2);
				}
				DefaultEdge e3 = g.getEdge(v, t);
				if(minTruss>et.et.get(e3)){
					minTruss = et.et.get(e3);
				}
				
				/**********/
				triTruss.put(e, minTruss);
				/**********/
				
				if(minTruss>maxTruss){
					maxTruss = minTruss;
				}
				//System.out.println(e+": "+minTruss);
			}
			
			//construct the index
			
			//Tested
			//System.out.println(maxTruss);
			for(int k=maxTruss; k>=2; k--){
				
				//get all edges with triTruss of k
				for(DefaultEdge e:triTruss.keySet()){
					if(triTruss.get(e)==k){
						Vertex vs = g.getEdgeSource(e);
						Vertex vt = g.getEdgeTarget(e);
						//efficiency can be improved
						DijkstraShortestPath<Vertex,DefaultEdge> dj = new DijkstraShortestPath<>(gv,vs,vt);
						if(dj.getPathLength()==Double.POSITIVE_INFINITY){
							//System.out.println(vs+"-"+vt);
							gv.addEdge(vs, vt, e);
						}
						
					}
				}
				
				
			}
			
			iu.gv = gv;
			iu.triTruss = triTruss;
			
			TCPindex.put(v, iu);
			
		}
		
		
		
		
		/*
		 * testing:tested 
		 */
//		Set<Vertex> vs = TCPindex.keySet();
//		for(Vertex v:vs){
//			
//			System.out.println(v+":...."+TCPindex.get(v));
//		}
//		
		/*
		 * Has been debugged:correct
		 * Set<Vertex> vs = indexGraphs.keySet();
		for(Vertex v:vs){
			
			System.out.println(v+":"+indexGraphs.get(v));
			
		}*/
		
		
		
		
		
		
		
		
		
	}
	
	
	private void edgesAgainstV(Vertex v,LinkedHashSet<DefaultEdge> eov,UndirectedGraph<Vertex, DefaultEdge> gv){
		//sub 1: collect neighbor of v
		//HashSet<DefaultEdge> edgesOfV = new HashSet<>(g.edgesOf(v));
		List<Vertex> nOfV = ni.neighborListOf(v);  //fast iteration
		HashSet<Vertex> nOfVh = new HashSet<>(ni.neighborsOf(v)); //fast for contains check
		
		
		for(Vertex nv:nOfV){
			gv.addVertex(nv);
			List<Vertex> nOfNv = ni.neighborListOf(nv);
			HashSet<Vertex> nnofVh =  new HashSet<>(ni.neighborsOf(nv));
			
			
			if(nOfNv.size()<=nOfV.size()){
				for(Vertex vvv:nOfNv){
					if(nOfVh.contains(vvv)){
						
						DefaultEdge eav = g.getEdge(vvv, nv);
						if(eav!=null&&eov.contains(eav)==false){
							eov.add(eav);
							//System.out.println(eav);
						}
						//(nv vvv)
					}
					
				}
				
				
				
			}else{
				
				for(Vertex vvv:nOfV){
					
					if(nnofVh.contains(vvv)){
						DefaultEdge eav = g.getEdge(vvv, nv);
						if(eav!=null&&eov.contains(eav)==false){
							eov.add(eav);
							//System.out.println(eav);
						}
						//(nv vvv)
					}
					
				}
				
				
			}
			
			
		
		}
		

		
		
		
		
		
		
		//get all edges for neighbors of v
		/*ArrayList<HashSet<DefaultEdge>> edgesOfnsV = new ArrayList<>();
		
		for(Vertex nv:nOfV){
			
			List<Vertex> nlofNv = ni.neighborListOf(nv);
			
			for(Vertex nnv:nv){
				
				
			}
			
			HashSet<DefaultEdge> edgesOfnV = new HashSet<>(g.edgesOf(nv));
			edgesOfnsV.add(edgesOfnV);
			
		}
		
		for(HashSet<DefaultEdge> edgesOfnV: edgesOfnsV){
			
			if(edgesOfnV.size()<=edgesOfV.size()){
				//iterate over edgesOfnV
				for(DefaultEdge e:edgesOfnV){
					if(edgesOfV.contains(e)){
						
						
					}
					
				}
				
				
			}else{
				//iterate over edgesOfV
				
				
			}
			
			
		}*/
		
		
	}
	
	
}
