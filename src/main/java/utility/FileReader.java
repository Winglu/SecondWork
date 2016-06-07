package utility;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jgraphtResearch.Graph;
import jgraphtResearch.Vertex;

public class FileReader {

	
	
	/**
	 * read edges
	 */
	public static void readEdgesFromFile(){
		
		
		
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
