package aocp.alg.sort;

public class BubbleSort {

	
	public static void sort(int [] a){
		
		int len = a.length;
		
		int ub = len-1;

		//int j=0;
		while(true){
			int t = 0;
			for(int j=0; j<=ub-1; j++){
				if(a[j]>=a[j+1]){
					int ti = a[j];
					a[j] = a[j+1];
					a[j+1] = ti;
					t = j;
				}
			}
			if(t == 0){
				break;
			}else{
				
				ub = t;
			}
			
		}
		

	}
	
	
	public static void main(String [] args){
		int a[] = new int[5];
		a[0]=3;
		a[1]=7;
		a[2]=6;
		a[3]=1;
		a[4]=2;
	
		BubbleSort.sort(a);
		
		for(int i=0;i<a.length; i++){
	    	System.out.println(a[i]);
	    }
	}	
}
