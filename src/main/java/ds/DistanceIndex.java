package ds;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DistanceIndex {

	
	
	public static LinkedHashMap<String,Point>  coords = new LinkedHashMap<>();
	
	//key: sid+tid
	public static HashMap<String,Double> distIndex = new HashMap<>();
	
	
	public static void coordsToDistance(){
		
		
		ArrayList<String> keys = new ArrayList<>(coords.keySet());
		
		
		for(int i=0; i<keys.size();i++){
			for(int j=i+1; j<keys.size(); j++){
				
				//key for disIndex
				String akey = keys.get(i) + keys.get(j);
				String bkey = keys.get(j) + keys.get(i);
				//calculate dist
				Point sc = coords.get(keys.get(i));
				Point tc = coords.get(keys.get(j));
				Double id = Math.pow(sc.x-tc.x,2)+Math.pow(sc.y-tc.y, 2);
				Double dist = Math.sqrt(id);
				
				distIndex.put(akey,dist);
				distIndex.put(bkey, dist);
				
				//System.out.println(akey+":"+dist);
				//System.out.println(bkey+":"+dist);
				
				
			}
		}
	}

	
}
