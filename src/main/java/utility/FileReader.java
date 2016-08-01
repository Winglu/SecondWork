package utility;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import org.jgrapht.graph.DefaultEdge;

import ds.DistanceIndex;
import ds.EdgeSupport;
import ds.EdgeTrussness;
import ds.Point;
import jgraphtResearch.Graph;
import jgraphtResearch.Vertex;

public class FileReader {

	public static  EdgeTrussness et = new EdgeTrussness(); 
	public static Hashtable<DefaultEdge, EdgeSupport> esh = new Hashtable<>();
	public static int maxs;
	/**
	 * read coordinates
	 */
	
	public static void readCoords(){
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("aix.txt");
		String str = "";
		String[] sstr;
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			if(is!=null){
				//read adjacentList line by line
				while((str = reader.readLine())!=null){
					//System.out.println(str);
					sstr = str.split(":");
					
					String vId = sstr[0];
					String[] coordS = sstr[1].split(",");
					Point ap = new Point(Double.parseDouble(coordS[0]),Double.parseDouble(coordS[1]));
					DistanceIndex.coords.put(vId, ap);
					//System.out.println(sstr[0]+":"+ap);

				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * read supportness
	 * 
	 * 
	 */
	
	public static void readSupportnessFromFile(){
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("support_index.txt");
		maxs = 0;
		String str = "";
		String[] sstr;
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			if(is!=null){
				//read adjacentList line by line
				while((str = reader.readLine())!=null){
					//System.out.println(str);
					sstr = str.split("\t");
					
					Vertex vs = new Vertex(sstr[0]);
					Vertex vt = new Vertex(sstr[1]);
				
					Graph.addEdgeToGraph(vs, vt);
					EdgeSupport es = new EdgeSupport();
					es.e = Graph.uGraph.getEdge(vs, vt);
					es.support = Integer.parseInt(sstr[2]);
					if(es.support>=maxs){
						
						maxs=es.support;
					}
					esh.put(Graph.uGraph.getEdge(vs, vt),es);
				}
				
			}
		} catch (IOException e) {
			
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
}
	
	/**
	 * 
	 * read trussness
	 * 
	 * A graph is stored as edge list, in which earch edge is associated with a trussness
	 * 
	 */
	
	public static void readTrussesFromFile(){
			
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream("truss_index.txt");
			
			String str = "";
			String[] sstr;
			try{
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				if(is!=null){
					//read adjacentList line by line
					while((str = reader.readLine())!=null){
						//System.out.println(str);
						sstr = str.split("\t");
						
						Vertex vs = new Vertex(sstr[0]);
						Vertex vt = new Vertex(sstr[1]);
					
						Graph.addEdgeToGraph(vs, vt);
						et.et.put(Graph.uGraph.getEdge(vs, vt),Integer.parseInt(sstr[2]));
					}
					
				}
			} catch (IOException e) {
				
			}finally{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	}
	
	
	
	
	
	
	/**
	 * read edges
	 * 
	 * A graph is stored as edge list.
	 * 
	 * 
	 */
	public static void readEdgesFromFile(){
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("rd_edges.txt");
		
		String str = "";
		String[] sstr;
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			if(is!=null){
				//read adjacentList line by line
				while((str = reader.readLine())!=null){
					//System.out.println(str);
					sstr = str.split("\t");
					Graph.addEdgeToGraph(new Vertex(sstr[0]), new Vertex(sstr[1]));
					
				}
				
			}
		} catch (IOException e) {
			
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	/**
	 * read adjacent list
	 */
	public static void readAdjacentList(){
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("sampleGraph.txt");
		String str = "";
		String[] sstr;
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			if(is!=null){
				//read adjacentList line by line
				while((str = reader.readLine())!=null){
					//System.out.println(str);
					sstr = str.split(",");
					for(int i=1; i<sstr.length; i++){
						//test okay 
						//System.out.println(sstr[0]+","+sstr[i]);
						Graph.addEdgeToGraph(new Vertex(sstr[0]), new Vertex(sstr[i]));
					}
					
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
