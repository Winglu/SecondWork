package com.swin.luchen.ssc;

//import java.util.ArrayList;

//import org.jgrapht.graph.DefaultEdge;



//import java.util.Hashtable;

//import java.util.List;

//import org.jgrapht.alg.DijkstraShortestPath;
//import java.util.List;
//import java.util.Set;

//import org.jgrapht.alg.NeighborIndex;
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.traverse.BreadthFirstIterator;

//import alg.TCPIndexing;
import alg.TrussDecomposition;
import cutility.CFileWriter;
import cutility.FileReader;
import jgraphtResearch.Graph;

//import jgraphtResearch.Vertex;

//import jgraphtResearch.Graph;
//import jgraphtResearch.Vertex;





public class App 
{
    public static void main(String[] args )
    {
    	
    	/***********************current stage*************************************/
    	//FileReader.readEdgesFromFile();
    	//FileReader.readSupportnessFromFile();
    	FileReader.readAdjacentList();
    	TrussDecomposition td = new TrussDecomposition(Graph.uGraph);
    	//System.out.println(Graph.uGraph.edgeSet().size());
    	//td.esh = FileReader.esh;
    	td.sortBasedTD();
    	//System.out.println("**********************Finish********************************");
    	
    	System.out.println(td.et.et);
    	//System.out.println("start writing");
    	//CFileWriter.trussToFile(td.et);
    	//Vertex vs = new Vertex("0");
    	//Vertex vt = new Vertex("1");
    	
    	//DefaultEdge e1 = Graph.uGraph.getEdge(vs, vt);
    	//DefaultEdge e2 = Graph.uGraph.getEdge(vt, vs);
    	
    	//System.out.println(FileReader.esh.get(e1));
    	//System.out.println(FileReader.esh.get(e2));
    	//Hashtable<DefaultEdge, EdgeSupport> esh = new Hashtable<>();
    	//td.edgesSupport(esh);
    	
    	
//    	try{
//			FileWriter fw = new FileWriter("support_index.txt", true);
//		    BufferedWriter bw = new BufferedWriter(fw);
//		    PrintWriter out = new PrintWriter(bw);
//		    
//		    Set<DefaultEdge> es = esh.keySet();
//		    
//		    for(DefaultEdge e:es){
//	        	
//		    	EdgeSupport s = esh.get(e);
//	        	
//	        	out.println(Graph.uGraph.getEdgeSource(e)+"\t"+Graph.uGraph.getEdgeTarget(e)+"\t"+s.support);
//	        }
//	        
//
//
//				
//				//write to file
//				
//					
//				
//				    
//				    //more code
//				    //out.println("more text");
//				    //more code
//
//			out.close();
//		} catch (IOException except) {
//				    //exception handling left as an exercise for the reader
//		}
//    	
    	//FileReader.readAdjacentList();
    	//FileReader.readTrussesFromFile();
    	//TrussDecomposition td = new TrussDecomposition(Graph.uGraph);
    	//td.trussDecomposition();
    	//td.sortBasedTD();
    	
    	//CFileWriter.trussToFile(td.et);
    	//System.out.println(FileReader.et.et);
    	
    	//CommunityDetector cd = new CommunityDetector();
    	//cd.et = td.et;
        //cd.detectCommunityOnGraph(Graph.uGraph, 4);
        //System.out.println(cd.comList.size());
        
        
        //Community c = cd.comList.get(1);
        //for(Community c:cd.comList){
        //	System.out.println(c.com.vertexSet().size());
        	
        //}
        
       
       // System.out.println(c);
        
    	//System.out.println(Graph.uGraph);
    	
    	/***********************old stage****************************/
    	/*Vertex v1 = new Vertex("1");
    	Vertex v2 = new Vertex("2");
    	Graph.uGraph.addVertex(v1);
    	Graph.uGraph.addVertex(v2);
    	Graph.uGraph.addEdge(v1, v2);
    	
    	DefaultEdge e1 = Graph.uGraph.getEdge(v1, v2);
    	DefaultEdge e2 = Graph.uGraph.getEdge(v1, v2);
    	//Graph.uGraph.removeEdge(e1);	
    	//Graph.uGraph.addEdge(v1, v2);
    	//DefaultEdge e2 = Graph.uGraph.getEdge(v1, v2);
    	
    	System.out.println(e1.equals(e2));*/
    	
    	
    	//FileReader.readAdjacentList();
    	
        //TrussDecomposition td = new TrussDecomposition(Graph.uGraph);
        //td.trussDecomposition();
        //td.sortBasedTD();
        
        //System.out.println(td.et);
        //initialize spatial index
        //FileReader.readCoords();
        //DistanceIndex.coordsToDistance();
        
        
        //CommunityDetector cd = new CommunityDetector();
        //cd.et = td.et;
        //cd.detectCommunityOnGraph(Graph.uGraph, 4);
        
        //Community c = cd.comList.get(1);
        //SpSoComQuerier ssco = new SpSoComQuerier();
        //System.out.println(c);
        //ssco.test(c,2.9,4);
        
        /*********************************************************/
        
        //TCPIndexing idex = new TCPIndexing(Graph.uGraph,td.et);
        //idex.TCPIndexConstruction();
        //FileReader.readAdjacentList();
        //CommunityQuerier cq = new CommunityQuerier(td.et,Graph.uGraph);
        //cq.TCPIndex = idex.TCPindex;
    	//cq.queryComminityByVertexTCP(new Vertex("q"), 4);
    	//cq.queryCommunityByVertex(new Vertex("q"), 4);
        //FileReader.readCoords();

        //DistanceIndex.coordsToDistance();

    }
}
