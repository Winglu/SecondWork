package aocp.alg.sort;

public class SortInsert {

	public static void sort(int [] a){
		
		int s[] = new int[a.length];
		
		
		s[0] = a [0];
		for(int ai=1; ai<a.length; ai++){
			int flg = 0;
			for(int si=0; si<ai; si++){  //currently , in s there are only ai element
				if(s[si]>=a[ai]){
					//adjust s
					adjust(s,si,ai-1);
					s[si]=a[ai];
					flg = 1;
					break;
				}
			}
			if(flg == 0){
				s[ai] =a[ai];
			}
		}
		
		for(int ai=0; ai<a.length; ai++){
			
			System.out.println(s[ai]);
		}
		
	}
	
	
	public static void adjust(int [] s, int sp, int ep){
		
		// 1 find end pos
		for(int i = ep; i>=sp;i--){
			s[i+1] = s[i];
		}
		
		
	}
	
	
	public static void main(String [] args){
		int a[] = new int[5];
		a[0]=7;
		a[1]=4;
		a[2]=6;
		a[3]=1;
		a[4]=2;
		
		SortInsert.sort(a);
	}
}
