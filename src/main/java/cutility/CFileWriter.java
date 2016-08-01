package cutility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import jgraphtResearch.Graph;

import org.jgrapht.graph.DefaultEdge;

import ds.EdgeTrussness;

public class CFileWriter {

	public static void trussToFile(EdgeTrussness et){
		
		Set<DefaultEdge> es = et.et.keySet();
		try{
			FileWriter fw = new FileWriter("truss_update_index.txt", true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
			for(DefaultEdge e: es){
				
				Integer t = et.et.get(e);
				
				//write to file
				
					
				
				    out.println(Graph.uGraph.getEdgeSource(e)+"\t"+Graph.uGraph.getEdgeTarget(e)+"\t"+t);
				    //more code
				    //out.println("more text");
				    //more code
			}
			out.close();
		} catch (IOException except) {
				    //exception handling left as an exercise for the reader
		}
			
		
		
		
	}
}
