package dailyprogram;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class Code_35 {

	public static void main(String[] args) {
		/*Write a simple Java program to find the duplicate numbers in an int array and store in to a separate array.
		Ex - int a[] = {1,2,4,2,3,4,5,6,7,5};
		Output - Duplicated numbers are [2, 4, 5]*/
		int[] a = {1,2,4,2,3,4,5,6,7,5};
		int dup[] = new int[0] ;
		int nondup[] = new int[0];
		System.out.println("The numbers in the array are -->"+Arrays.toString(a));
		System.out.println("The Duplicated numbers in the array are -->"+a);// It will not print the array contents
		for (int i = 0; i < a.length; i++) {
			for (int j = i+1; j < a.length; j++) {
				if(a[i]==a[j] && !ArrayUtils.contains(dup, a[i])){
					dup = add(dup,a[i]);				
				}
			}
			if(!ArrayUtils.contains(dup, a[i])){
				nondup = add(nondup, a[i]);
			}
		}
		System.out.println("The Duplicated numbers in the array are -->"+Arrays.toString(dup));
		System.out.println("The Non-Duplicated numbers in the array are -->"+Arrays.toString(nondup));
		System.out.println();
	}
	
	private static int[] add(int[] b, int a) {
		int c[] = Arrays.copyOf(b, b.length +1);
	    c[b.length] = a;
		return c;
		
	}
}
