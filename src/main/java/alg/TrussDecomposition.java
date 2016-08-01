package alg;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import jgraphtResearch.Vertex;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.graph.DefaultEdge;

import cutility.FileReader;
import ds.EdgeSupport;
import ds.EdgeTrussness;

/**
 * After calling trussDecomposition, the graph must be reloaded
 * Moved to git hub
 * @author luchen
 *
 */


public class TrussDecomposition {

	public  UndirectedGraph<Vertex, DefaultEdge> g;
	
	private Queue<DefaultEdge> removedEdges;
	
	public  EdgeTrussness et; 
	
	
	public Hashtable<DefaultEdge, EdgeSupport> esh;
	public TrussDecomposition(UndirectedGraph<Vertex, DefaultEdge> g){
		this.g = g;
		removedEdges = new LinkedBlockingQueue<>();
		et = new EdgeTrussness();
		esh = new Hashtable<>();
		
	}
	
	
	
	/**
	 * Advanced implementation
	 */
	
	
	public void sortBasedTD(){
		/*compute support of each edge*/
		//Hashtable<DefaultEdge, EdgeSupport> esh = new Hashtable<>();
		//int maxSupport = FileReader.maxs;
		
		int maxSupport = edgesSupport(esh);
		
		//System.out.println(maxSupport);
		/*initialize auxiliary array*/
		int a[] = new int [maxSupport+1]; // a[0] is used for edges that do not belong to any triangles 
		for(int i=0; i<=maxSupport; i++){
			a[i]=0;
		}
		/*collect support statistics*/
		Set<DefaultEdge> edges = g.edgeSet();
		for(DefaultEdge e:edges){
			a[esh.get(e).support]++;
		}
		
		
		int l = 0;
		for(int i=0; i<=maxSupport; i++){
			int t = a[i];
			a[i] = l;
			l = l+t;
		}//now a[i] stores the position of first edge with support of i in s
		
		
		/*initialize edge array*/
		DefaultEdge [] s = new DefaultEdge[edges.size()];
		
		Hashtable<DefaultEdge, Integer> eph = new Hashtable<>();
		for(DefaultEdge e:edges){
			s[a[esh.get(e).support]] = e;
			eph.put(e, a[esh.get(e).support]);
			a[esh.get(e).support]++;
			//need to store position information for each edge
		}
		
		//printArray(s);
		for(int i=0; i<a.length; i++){
			a[i]--;
		}
		
		
		/*Testing：pass*/
//		for(int i=0; i<s.length; i++){
//			System.out.println(s[i]+"support is:"+esh.get(s[i]).support);
//			
//		}

		//System.out.println(s.length);
		//System.out.println(a.length);
	    System.out.println("******************************************************");
		/*Testing*/
		
		/*real decomposition*/
		efficientTD(s,a,eph,esh);
		
		
		
		//graphRecovery();
	}
	
	
	
	private void edgeUpdate(DefaultEdge ne,
			DefaultEdge ce,
			Hashtable<DefaultEdge, Integer> eph, 
			Hashtable<DefaultEdge, EdgeSupport> esh,
			DefaultEdge [] s,
			int [] a){
		
		Integer sne = esh.get(ne).support ;
		Integer sce = esh.get(ce).support;
		
		if(sne <= sce){
			//careful update
			//move the ne next to the position of ce
			//in other words, exchange s[pos[ce]+1] and s[pos[ne]] (ne)
			//be careful that s[pos[ce]+1] could be the s[pos[ne]] (ne)
			
			int currentPofCe = eph.get(ce);
			
				DefaultEdge te = s[currentPofCe+1];
				s[currentPofCe+1] = ne;
				s[eph.get(ne)] = te;
				
				//update position table
				
				
				
				eph.put(te,eph.get(ne));
				eph.put(ne, currentPofCe+1);
				
				a[sne-1]++;
				
			
			
			
				//a[sne-1]++;
			
			
			
		}else{
			//normal update
			
			//here means the supportness of ne is larger than the supportness of ce
			
				int p = a[sne-1];
				
				
					DefaultEdge esw  = s[p+1];
					s[p+1] = ne;
					s[eph.get(ne)] = esw;
					eph.put(esw,eph.get(ne));
					eph.put(ne,p+1);
					a[sne-1]++;
				
				
				
				
			
			
		}
		
		
	}
	
	
	private void efficientTD(DefaultEdge [] s,
			int [] a,
			Hashtable<DefaultEdge, Integer> eph,
			Hashtable<DefaultEdge, EdgeSupport> esh){
		
		int k = 2;
		//NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
		//System.out.println(s.length);
		for(int i=0; i<s.length; i++){
			NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
			
			//all edges, for each of them with supportness of k, have been processed. So k++
			if(i>a[k-2]){ 
				k++;
			}
			DefaultEdge e = s[i];
			
			//move e to processed edges
			/*Veli Veli important*/
			/*
			 * here something is wrong
			 */

			
			
			Vertex se = g.getEdgeSource(e);
			Vertex te = g.getEdgeTarget(e);
			Vertex nlv,nhv;
			if(ni.neighborsOf(se).size()<= ni.neighborsOf(te).size()){
				nlv = se;
				nhv = te;
			}else{
				
				nlv = te;
				nhv = se;
			}
			
			List<Vertex> neighbour = ni.neighborListOf(nlv);
			for(Vertex w:neighbour){
				if(g.containsEdge(w, nhv)){
					/*following two edges need to be processed */
					DefaultEdge e1 = g.getEdge(nlv, w);
					DefaultEdge e2 = g.getEdge(w, nhv);
					//int e1s = esh.get(e1).support;
					//int e2s = esh.get(e2).support;
					
					
					
					/*process the first edge*/
					edgeUpdate(e1,e,eph,esh,s,a);
					esh.get(e1).support--;
					
					/*process the second edge*/
					edgeUpdate(e2,e,eph,esh,s,a);
					esh.get(e2).support--;
				}
				
			}
			
			//remove e
			//printArray(s);
			//System.out.println(s[i]+":"+k);
			et.et.put(s[i], k);
			removedEdges.add(s[i]);
			g.removeEdge(s[i]);
			
			//System.out.println(i);
			
		}
		//System.out.println(esh);
		//printArray(s);
		
	}
	
	
	private void previousEfficientTD(DefaultEdge [] s,
			int [] a,
			Hashtable<DefaultEdge, Integer> eph,
			Hashtable<DefaultEdge, EdgeSupport> esh){
		
		int k = 2;
		//NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
		//System.out.println(s.length);
		for(int i=0; i<s.length; i++){
			NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
			
			//all edges, for each of them with supportness of k, have been processed. So k++
			if(i>a[k-2]){ 
				k++;
			}
			DefaultEdge e = s[i];
			
			//move e to processed edges
			/*Veli Veli important*/
			if(esh.get(e).support>0){
				a[esh.get(e).support-1]++; //this edge has been processed, to avoid this edge be moved
			}
			
			
			Vertex se = g.getEdgeSource(e);
			Vertex te = g.getEdgeTarget(e);
			Vertex nlv,nhv;
			if(ni.neighborsOf(se).size()<= ni.neighborsOf(te).size()){
				nlv = se;
				nhv = te;
			}else{
				
				nlv = te;
				nhv = se;
			}
			
			List<Vertex> neighbour = ni.neighborListOf(nlv);
			for(Vertex w:neighbour){
				if(g.containsEdge(w, nhv)){
					/*following two edges need to be processed */
					DefaultEdge e1 = g.getEdge(nlv, w);
					DefaultEdge e2 = g.getEdge(w, nhv);
					int e1s = esh.get(e1).support;
					int e2s = esh.get(e2).support;
					
					
					
					/*process the first edge*/
					if(e1s>0){
						int p = a[e1s-1];
						
						if(p+1>=s.length){
							//a[e1s-1]++;
							System.out.println("*");
						}else{
							try{
								DefaultEdge esw1  = s[p+1];
								//if(esw1==e){
								//	p++;
								//	esw1  = s[p+1];
								//}
								s[p+1] = e1;
								s[eph.get(e1)] = esw1;
								eph.put(esw1,eph.get(e1));
								eph.put(e1,p+1);
								a[e1s-1]++;
							}catch(ArrayIndexOutOfBoundsException excep){
								System.out.println(e1s);
								System.out.println(p);
								System.out.println(s.length);
							}
						}
						
						
					}else{
						//System.out.println(e);
						//already in position do not need to update postions after reduce
						//may not necessary
					}
					esh.get(e1).support--;
					
					
					/*process the second edge*/
					if(e2s>0){
							int p = a[e2s-1];
							
							
							if(p+1>=s.length){
								//a[e2s-1]++;
								System.out.println("*");
							}else{
								try{
								DefaultEdge esw2  = s[p+1];
								//if(esw2==e){
								//	p++;
								//	esw2  = s[p+1];
								//}
								s[p+1] = e2;
								s[eph.get(e2)] = esw2;
								eph.put(esw2,eph.get(e2));
								eph.put(e2,p+1);
								a[e2s-1]++;
								}catch(ArrayIndexOutOfBoundsException excep){
									System.out.println(e2s);
									System.out.println(p);
									System.out.println(s.length);
								}
							}
							
							//DefaultEdge esw2  = s[p+1];
							//s[p+1] = e2;
							//s[eph.get(e2)] = esw2;
							//eph.put(esw2,eph.get(e2));
							//eph.put(e2,p+1);
							//a[e2s-1]++;
					
//						try{
//							
//							//int p = a[e2s-1];
//							DefaultEdge esw2  = s[p+1];
//							//if(esw2==e){
//							//	p++;
//							//	esw2  = s[p+1];
//							//}
//							s[p+1] = e2;
//							s[eph.get(e2)] = esw2;
//							eph.put(esw2,eph.get(e2));
//							eph.put(e2,p+1);
//							a[e2s-1]++;
//						}catch(ArrayIndexOutOfBoundsException excep){
//							System.out.println(e2s);
//							System.out.println(p);
//							System.out.println(s.length);
//						}
					}	
					else{
						//already in position do not need to update postions after reduce
						//may not necessary
						//System.out.println(e);
					}
					
					esh.get(e2).support--;
				}
				
			}
			
			//remove e
			//printArray(s);
			//System.out.println(s[i]+":"+k);
			et.et.put(s[i], k);
			removedEdges.add(s[i]);
			g.removeEdge(s[i]);
			
			//System.out.println(i);
			
		}
		//System.out.println(esh);
		//printArray(s);
		
	}
	
	
	/**
	 * Only for testing purpose 
	 * @param ea
	 */
	@SuppressWarnings("unused")
	private void printArray(DefaultEdge [] ea){
		for(int i=0; i<ea.length; i++){
			System.out.println(ea[i]);
			
			
		}
	}
	
	
	public int edgesSupport(Hashtable<DefaultEdge, EdgeSupport> esh){
		
		/*data structure initialization*/
		int maxSupport = 0;
		Set<DefaultEdge> edges = g.edgeSet();
		Vertex sv;
		Vertex tv;
		ArrayList<Vertex> svn = new ArrayList<>();
		ArrayList<Vertex> tvn = new ArrayList<>();
		NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
		
		
		for(DefaultEdge e:edges){
			sv = g.getEdgeSource(e);
			tv = g.getEdgeTarget(e);
			svn.addAll(ni.neighborsOf(sv));
			tvn.addAll(ni.neighborsOf(tv)); 
			
			svn.retainAll(tvn);
			int support = svn.size();
			if(support >= maxSupport){
				maxSupport = support;
			}
			EdgeSupport es = new EdgeSupport();
			es.support = support;
			es.e = e;
			esh.put(e, es);
			svn.clear();
			tvn.clear();
		}
		
		return maxSupport;
		
	}
	
	
	/**
	 * 
	 * no bug left, only may have performance issues   (may still have bug)
	 * 
	 */
	public void trussDecomposition(){
		
		Hashtable<DefaultEdge, EdgeSupport> edgeSupport = new Hashtable<>();
		NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
		
		//supportComputation(edgeSupport,ni);
		
		
		/*Iterator<Map.Entry<DefaultEdge, EdgeSupport> > isz = edgeSupport.entrySet().iterator();
		while(isz.hasNext()){
			Map.Entry<DefaultEdge, EdgeSupport> m  = isz.next();
			System.out.println(m.getKey()+"support: "+m.getValue().support);
		}*/
		
		
		int k = 2;
		int j = k-2;
		
		while(g.edgeSet().size()!=0){
			removeUnsupportedEdges(edgeSupport,ni,j);
			k++;
			j=k-2;
		}
		
		
		graphRecovery();
		
		/*Iterator<Map.Entry<DefaultEdge, Integer> > i = et.et.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<DefaultEdge, Integer> m  = i.next();
			System.out.println(m.getKey()+"'s trussness is:"+m.getValue());
		}
		System.out.println(et.et.size());
		System.out.println(g);*/
		
     
	}
	
	
	
	

	//!!!!!!!!!!!  neighbor index must be recalculated during the graph update
	private void removeUnsupportedEdges(Hashtable<DefaultEdge, EdgeSupport> esl,NeighborIndex<Vertex, DefaultEdge> ni,int j){
		//support of each edge has been calculated
		Queue<EdgeSupport> q = new LinkedBlockingQueue<>();

		Set<DefaultEdge> edges = g.edgeSet();
		ArrayList<DefaultEdge> edgeList = new ArrayList<>(edges);
		Vertex sv;
		Vertex tv;
		ArrayList<Vertex> svn = new ArrayList<>();
		ArrayList<Vertex> tvn = new ArrayList<>();
		ni = new NeighborIndex<>(g);
		
		Iterator<DefaultEdge> ide = edgeList.iterator();
		while(ide.hasNext()){
		//for(DefaultEdge e:edges){
			
			DefaultEdge e = ide.next();
			sv = g.getEdgeSource(e);
			tv = g.getEdgeTarget(e);
			svn.addAll(ni.neighborsOf(sv));
			tvn.addAll(ni.neighborsOf(tv)); 
			
			svn.retainAll(tvn);
			int support = svn.size();
			//System.out.println(support);
			if(support<j){
				
				//for graph recover purpose
				removedEdges.add(e);
			
				g.removeEdge(e);
				ni = new NeighborIndex<>(g);
			}else{
				EdgeSupport es = new EdgeSupport();
				es.support = support;
				es.e = e;
				esl.put(e, es);
				
			}
			svn.clear();
			tvn.clear();
		}
		
		
		
		ni = new NeighborIndex<>(g);

		while(q.isEmpty()!=true){
			//ni = new NeighborIndex<>(g);
			EdgeSupport ces = q.poll();
			//get edge source and target
			sv = g.getEdgeSource(ces.e);
			tv = g.getEdgeTarget(ces.e);
			
			svn.addAll(ni.neighborsOf(sv));
			tvn.addAll(ni.neighborsOf(tv));
			svn.retainAll(tvn);
			
			for(Vertex v:svn){
				DefaultEdge  aes = g.getEdge(sv,v);
				if(aes!=null){
					EdgeSupport aess = esl.get(aes);
					aess.support = aess.support - 1;
					if(aess.support<j){
						q.add(aess);
						removedEdges.add(aess.e);
						g.removeEdge(aess.e);
						//ni = new NeighborIndex<>(g);
					}

				}
				DefaultEdge  aet = g.getEdge(tv,v);
				if(aet!=null){
					EdgeSupport aets = esl.get(aet);
					aets.support = aets.support - 1;
					if(aets.support<j){
						q.add(aets);
						removedEdges.add(aets.e);
						g.removeEdge(aets.e);
						//ni = new NeighborIndex<>(g);
					}
				}
			}
			
			svn.clear();
			tvn.clear();
		}
		
		
		//for all edges that currently in the graph, they should all have trussness of j
		edges = g.edgeSet();
		for(DefaultEdge e:edges){
			
			et.et.put(e, j+2);
		}
		
	}
	
	
	private void graphRecovery(){
		
		while(removedEdges.isEmpty()!=true){
			
			DefaultEdge e = removedEdges.poll();
			Vertex sv = g.getEdgeSource(e);
			Vertex tv = g.getEdgeTarget(e);
			g.addEdge(sv, tv,e);
			
			
			
		}
		
	}
	
	/*
	private void supportComputation(Hashtable<DefaultEdge, EdgeSupport> esl,NeighborIndex<Vertex, DefaultEdge> ni){
		Set<DefaultEdge> edges = g.edgeSet();
		Vertex sv;
		Vertex tv;
		ArrayList<Vertex> svn = new ArrayList<>();
		ArrayList<Vertex> tvn = new ArrayList<>();
		for(DefaultEdge e:edges){
			sv = g.getEdgeSource(e);
			tv = g.getEdgeTarget(e);
			svn.addAll(ni.neighborsOf(sv));
			tvn.addAll(ni.neighborsOf(tv)); 
			
			svn.retainAll(tvn);
			int support = svn.size();
			EdgeSupport es = new EdgeSupport();
			es.support = support;
			es.e = e;
			esl.put(e, es);
			svn.clear();
			tvn.clear();
		}
		
	}
	*/
	
	
	public void trussDecomposition_workable(){
		int k = 3;
		Set<DefaultEdge> edges = g.edgeSet();
		NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
		Hashtable<DefaultEdge, EdgeSupport> edgeSupport = new Hashtable<>();
		Queue<EdgeSupport> q = new LinkedBlockingQueue<>();
		
		Vertex sv;
		Vertex tv;
		ArrayList<Vertex> svn = new ArrayList<>();
		ArrayList<Vertex> tvn = new ArrayList<>();
		
		//initial edge support
		for(DefaultEdge e:edges){
			//computes support
			sv = g.getEdgeSource(e);
			tv = g.getEdgeTarget(e);
			svn.addAll(ni.neighborsOf(sv));
			tvn.addAll(ni.neighborsOf(tv)); 
			
			svn.retainAll(tvn);
			int support = svn.size();
			EdgeSupport es = new EdgeSupport();
			es.support = support;
			es.e = e;
			edgeSupport.put(e, es);
			
			svn.clear();
			tvn.clear();
			
		}
		//now edge support are calculated
		
		
		//clean up edges with support of 0
		Iterator<Map.Entry<DefaultEdge, EdgeSupport> > is = edgeSupport.entrySet().iterator();
		while(is.hasNext()){
			Map.Entry<DefaultEdge, EdgeSupport> m  = is.next();
			if(m.getValue().support==0){
				g.removeEdge(m.getValue().e);
			}
		}
		
		
		for(DefaultEdge e:g.edgeSet()){
			EdgeSupport es = edgeSupport.get(e);
			if(es.support <=(k-2)){
				q.add(es);
			}
			
		}
		
		
		Iterator<Map.Entry<DefaultEdge, EdgeSupport> > isz = edgeSupport.entrySet().iterator();
		while(isz.hasNext()){
			Map.Entry<DefaultEdge, EdgeSupport> m  = isz.next();
			System.out.println(m.getKey()+"support: "+m.getValue().support);
		}
		
		
		//compute trussness 
		while(g.edgeSet().size()!=0){
			while(q.isEmpty()!=true){
				EdgeSupport es = q.poll();
				//get edge source and target
				sv = g.getEdgeSource(es.e);
				tv = g.getEdgeTarget(es.e);
				
				svn.addAll(ni.neighborsOf(sv));
				tvn.addAll(ni.neighborsOf(tv));
				svn.retainAll(tvn);
				//get edges 
				
				for(Vertex v:svn){
					DefaultEdge  aes = g.getEdge(sv,v);
					if(aes!=null){
						EdgeSupport aess = edgeSupport.get(aes);
						aess.support = aess.support - 1;
						//do not know why, but it works
						//according to k-truss definition 
						if(aess.support < (k-2)){
							if(q.contains(aess)!=true){
								q.add(aess);
							}
							
						}
					}
					DefaultEdge  aet = g.getEdge(tv, v);
					if(aet!=null){
						EdgeSupport aets = edgeSupport.get(aet);
						aets.support = aets.support - 1;
						//do not know why, but it works
						//according to k-truss definition 
						if(aets.support < (k-2)){
							if(q.contains(aets)!=true){
								q.add(aets);
								
							}
							
						}
					}
					
					
				}
				
				et.et.put(es.e, k);
				g.removeEdge(es.e);
				svn.clear();
				tvn.clear();
			}
			
			
			if(g.edgeSet().size()!=0){
				k++;
				//System.out.println("trussness: "+k);
				for(DefaultEdge e:g.edgeSet()){
					EdgeSupport es = edgeSupport.get(e);
					if(es.support <= (k-2)){
						if(q.contains(es)!=true){
							q.add(es);
						}
						
					}
					
				}
			}	
			
		}
		
		System.out.println(et.et.size());
		Iterator<Map.Entry<DefaultEdge, Integer> > i = et.et.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<DefaultEdge, Integer> m  = i.next();
			System.out.println(m.getKey()+"'s trussness is:"+m.getValue());
		}
		
		
	}
	
	public void trussDecompositionAdv(){
		int k = 2;
		Set<DefaultEdge> edges = g.edgeSet();
		
		NeighborIndex<Vertex, DefaultEdge> ni = new NeighborIndex<>(g);
		Hashtable<DefaultEdge, EdgeSupport> edgeSupport = new Hashtable<>();
		
		//setup a queue to store edges that have number of support less than k  
		//Queue<EdgeSupport> q = new LinkedBlockingQueue<>();

		//reducing creations of variable 
		Vertex sv;
		Vertex tv;
		ArrayList<Vertex> svn = new ArrayList<>();
		ArrayList<Vertex> tvn = new ArrayList<>();
		
		
		//number of edge
		int noe = g.edgeSet().size();
		//number of maximal support
		//int noms = noe-2;
		
		int [] supportSpace = new int [noe-2];
		
		
		for(DefaultEdge e:edges){
			//computes support
			sv = g.getEdgeSource(e);
			tv = g.getEdgeTarget(e);
			
			svn.addAll(ni.neighborsOf(sv));
			tvn.addAll(ni.neighborsOf(tv)); 
			//= ni.neighborsOf(sv);
			//= ni.neighborsOf(tv);
		
			/*	Testing		
 			* System.out.println(svn.size());
			System.out.println(tvn.size());
			System.out.println(ni.neighborsOf(sv));
			System.out.println(ni.neighborsOf(tv));*/
			svn.retainAll(tvn);
			int support = svn.size();
			EdgeSupport es = new EdgeSupport();
			es.support = support;
			es.e = e;
			
			//great increase the space for the current support number to  +1
			supportSpace[support]++;
			
			edgeSupport.put(e, es);
			
			
			/*	 Testing
		 	* System.out.println(e+" support is :"+support);
			System.out.println(svn.size());
			System.out.println(tvn.size());
			System.out.println(ni.neighborsOf(sv));
			System.out.println(ni.neighborsOf(tv));*/
			
			
			/*
			 * Clear up
			 */
			svn.clear();
			tvn.clear();
		}
				
	
		
		
		//good sort!!!!
		//compute start location for each support number
		
		//int l = 0; //initial location
		//key: number of support, value location in the sorted array
		int [] initialLoction = new int [noe-1];
		initialLoction[0] =0;
		for(int i=1; i<supportSpace.length;i++){
			initialLoction[i]=initialLoction[i-1]+supportSpace[i-1];
			
		}
		/*
		for(int i=0; i<supportSpace.length;i++){
			System.out.println(i+":"+initialLoction[i]);
			
		}
		 */
		DefaultEdge [] sortedEdge = new DefaultEdge [noe];
		
		//hash table for sotre location
		
		Hashtable<DefaultEdge, Integer> edgeSortedLocation = new Hashtable<>();
		
		//iterate the edge
		Iterator<Map.Entry<DefaultEdge, EdgeSupport> > isz = edgeSupport.entrySet().iterator();
		while(isz.hasNext()){
			Map.Entry<DefaultEdge, EdgeSupport> m  = isz.next();
			
			//put edge into a array
			sortedEdge[initialLoction[m.getValue().support]] = m.getValue().e;
			edgeSortedLocation.put(m.getValue().e, initialLoction[m.getValue().support]);
			//recorder location information using a hash table
			
			initialLoction[m.getValue().support]++;
			
		}
		/*
		 *tested
		 
		for(int i=0;i<noe;i++){
			
			System.out.println(sortedEdge[i]+" support is: "+ edgeSupport.get(sortedEdge[i]).support);
		}
		*/
		//now the end position of support k should be initialLoction[m.getValue().support]--
		
		for(int i=1; i<supportSpace.length;i++){
			initialLoction[i]--;
		} 
		
		//System.out.println(initialLoction[2]+","+supportSpace[2]);
		//now the initialLocation store the end location
		/*
		System.out.println("*****");
		for(int i=1; i<supportSpace.length;i++){
			System.out.println(i+":"+initialLoction[i]);
			
		}
		*/
		//System.out.println(q.size());
		/*
		 * end of compute support of each edge
		 */
		
		
		
		/*
		 * debug edge support 
		 */
		/*
		Iterator<Map.Entry<DefaultEdge, EdgeSupport> > isz = edgeSupport.entrySet().iterator();
		while(isz.hasNext()){
			Map.Entry<DefaultEdge, EdgeSupport> m  = isz.next();
			System.out.println(m.getKey()+"support: "+m.getValue().support);
		}
		*/
		
		Iterator<Map.Entry<DefaultEdge, EdgeSupport> > is = edgeSupport.entrySet().iterator();
		while(is.hasNext()){
			Map.Entry<DefaultEdge, EdgeSupport> m  = is.next();
			if(m.getValue().support==0){
				g.removeEdge(m.getValue().e);
			}
		}
		
		
		//now the all edges has been sorted according to their support number in sortedEdge
		//the end location of edge with support number of k is in initialLoction[k]
		//the number of edges with support number of k is in supportSpace[k]
		//
		
		while(g.edgeSet().size()!=0){
			for(int i=0;i<noe;i++){
				DefaultEdge lowestSupportE = sortedEdge[i];
				if(lowestSupportE!=null){
					EdgeSupport es = edgeSupport.get(lowestSupportE);
					if(es.support<=k-2){
						
						sv = g.getEdgeSource(es.e);
						tv = g.getEdgeTarget(es.e);
						
						svn.addAll(ni.neighborsOf(sv));
						tvn.addAll(ni.neighborsOf(tv));
						svn.retainAll(tvn);
						System.out.println(es.e);
						if(svn.size()<=tvn.size()){
							for(Vertex v:svn){
								DefaultEdge  aes = g.getEdge(sv,v);
								if(aes!=null){
									EdgeSupport aess = edgeSupport.get(aes);
									aess.support = aess.support - 1;
									//if(aess.support<=(k-2)){
									//	q.add(aess);
									//}
									//reorder
									int s = aess.support;
									System.out.println(s);
									int ps = s+1;
									int startOf = initialLoction[ps-1]+1;
									int currentEdgeLocation = edgeSortedLocation.get(aess.e);
									DefaultEdge te = sortedEdge[startOf];
									sortedEdge[startOf] = aess.e;
									sortedEdge[currentEdgeLocation] = te;	
									i=-1;
									
								}
									
								
								DefaultEdge  aet = g.getEdge(tv, v);
								if(aet!=null){
									EdgeSupport aets = edgeSupport.get(aet);
									aets.support = aets.support - 1;
									//if(aets.support<=(k-2)){
									//	q.add(aets);
									//}
									
									//reorder
									
									int s = aets.support;
									int ps = s+1;
									int startOf = initialLoction[ps-1]+1;
									int currentEdgeLocation = edgeSortedLocation.get(aets.e);
									DefaultEdge te = sortedEdge[startOf];
									sortedEdge[startOf] = aets.e;
									sortedEdge[currentEdgeLocation] = te;
									i=-1;
									
								/*	for(int j=0;j<noe;j++){
										 System.out.println(j+":"+sortedEdge[j]);
									}*/
								}
			
							}
							
							
						}else{
							for(Vertex v:svn){
								
								DefaultEdge  aet = g.getEdge(tv, v);
								if(aet!=null){
									EdgeSupport aets = edgeSupport.get(aet);
									aets.support = aets.support - 1;
									//if(aets.support<=(k-2)){
									//	q.add(aets);
									//}
									
									//reorder
									int s = aets.support;
									int ps = s+1;
									int startOf = initialLoction[ps-1]+1;
									int currentEdgeLocation = edgeSortedLocation.get(aets.e);
									DefaultEdge te = sortedEdge[startOf];
									sortedEdge[startOf] = aets.e;
									sortedEdge[currentEdgeLocation] = te;
									
									
									
									i=-1;
								}
								
								
								DefaultEdge  aes = g.getEdge(sv,v);
								if(aes!=null){
									EdgeSupport aess = edgeSupport.get(aes);
									aess.support = aess.support - 1;
									//if(aess.support<=(k-2)){
									//	q.add(aess);
									//}
									
									//reorder
									int s = aess.support;
									int ps = s+1;
									int startOf = initialLoction[ps-1]+1;
									int currentEdgeLocation = edgeSortedLocation.get(aess.e);
									DefaultEdge te = sortedEdge[startOf];
									sortedEdge[startOf] = aess.e;
									sortedEdge[currentEdgeLocation] = te;
									i=-1;
								}
						
							}
							
						}
						
	
						et.et.put(es.e, k);
						g.removeEdge(es.e);
						System.out.println("edge size"+g.edgeSet().size());
						svn.clear();
						tvn.clear();
						
					}
					
				}
				
			}
			
			if(g.edgeSet().size()!=0){
				k++;
				//System.out.println("trussness: "+k);
			}
			
		}
		
		/*
		//ArrayList<DefaultEdge> el = new ArrayList<>();
		// now q contains some edge with support less than 3
		while(g.edgeSet().size()!=0){ //
			while(q.isEmpty()!=true){
				EdgeSupport es = q.poll();
				
				//get edge source and target
				sv = g.getEdgeSource(es.e);
				tv = g.getEdgeTarget(es.e);
				
				svn.addAll(ni.neighborsOf(sv));
				tvn.addAll(ni.neighborsOf(tv));
				svn.retainAll(tvn);
				//get edges 
				
				for(Vertex v:svn){
					DefaultEdge  aes = g.getEdge(sv,v);
					if(aes!=null){
						EdgeSupport aess = edgeSupport.get(aes);
						aess.support = aess.support - 1;
						//if(aess.support<=(k-2)){
						//	q.add(aess);
						//}
					}
						
					
					DefaultEdge  aet = g.getEdge(tv, v);
					if(aet!=null){
						EdgeSupport aets = edgeSupport.get(aet);
						aets.support = aets.support - 1;
						//if(aets.support<=(k-2)){
						//	q.add(aets);
						//}
					}
					
					
				}
				
					
				et.et.put(es.e, k);
				g.removeEdge(es.e);
				
			
				//now the edge trusness can be stored.
				
				svn.clear();
				tvn.clear();
				//break;
			}
			
			
			
			//update the queue  put all edges, that have support less than k, into the queue
			//current implementation is not efficient, it iterates all remaining edge in the graph
			if(g.edgeSet().size()!=0){
				k++;
			}
			
			for(DefaultEdge e:g.edgeSet()){
				EdgeSupport es = edgeSupport.get(e);
				if(es.support <=(k-2)){
					q.add(es);
				}
				
			}
			
			
		}
		/*
		System.out.println(et.et.size());
		Iterator<Map.Entry<DefaultEdge, Integer> > i = et.et.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<DefaultEdge, Integer> m  = i.next();
			System.out.println(m.getKey()+"'s truness is:"+m.getValue());
		}
		*/
		
	}
	
	
	
		
		
		
	
	
	
	

}
