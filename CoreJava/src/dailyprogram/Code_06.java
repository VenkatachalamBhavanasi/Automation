package dailyprogram;

import java.util.Arrays;

public class Code_06 {

	public static void main(String[] args) {
		/*Find repeated numbers in an Array List of numbers.
		Declare an integer array like 
		int[] a = {13,15,67,88,65,13,99,67,65,87,13}. 
		The result should be – Duplicated numbers are 13 67 65*/
		int a[]={13,15,67,88,65,13,67,99,65,87,13,99,199};
		int n=a.length;
		//to print the duplicated numbers only once, we need to use Array.sort, Set concept or String's inbuilt functions
		//Option # 1
		for(int i=0;i<n;i++) {
			for(int j=i+1;j<n;j++) {
				if(a[j]==a[i]) {
					System.out.print(a[i]+" ");
					break;
				}
			}
		}
		System.out.println();
		//Using Arrays sort and to print duplicated values only once
		//Option # 2
		Arrays.sort(a);
		String repeatedNum = "";
		for (int i = 0; i < a.length -1 ; i++) {
			if(a[i] == a[i+1]){
				if (!repeatedNum.equals(a[i]+" ")) {
					repeatedNum = a[i] +" ";
					System.out.print(repeatedNum);
				}
			}
		}
	}

}
