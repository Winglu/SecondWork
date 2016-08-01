package aocp.alg.sort;

import java.util.Hashtable;
import java.util.Set;

public class SortCounting {

	
	
	
	public static void sort(int [] a){
		
		int loa = a.length;
		
		Hashtable<Integer,Integer> at = new Hashtable<>();
		
		//ini the counting table
		for(int i=0; i<a.length; i++){
			at.put(a[i], 0);
		}
		
		for(int i=1;i<a.length; i++){
			for(int j=0; j<i;j++){
				if(a[i]>=a[j]){
					Integer c = at.get(a[i]);
					c++;
					at.put(a[i],c);
					
				}else{
					Integer c = at.get(a[j]);
					c++;
					at.put(a[j],c);
				}
			}
		}
		
		
		Set<Integer> ks = at.keySet();
		
	    for(Integer i:ks){
	    	a[at.get(i)] = i;
	    	
	    }
		
		
	}
	
	
	public static void main(String [] args){
		int a[] = new int[5];
		a[0]=1;
		a[1]=4;
		a[2]=6;
		a[3]=1;
		a[4]=2;
	
		SortCounting.sort(a);
		
		for(int i=0;i<a.length; i++){
	    	System.out.println(a[i]);
	    }
	}
	
}
