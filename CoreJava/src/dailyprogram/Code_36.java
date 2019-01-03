package dailyprogram;

import java.util.Arrays;

public class Code_36 {

	public static void main(String[] args) {
		/*Write a simple Java program to rearrange a given number in ascending order of their digits.
			Ex   –   Input - 19673
					 Output - 13679*/
		int origDigit = 19673;
		int digit = origDigit;
		String digitStr = origDigit+"";
		int[] arr = new int[digitStr.length()];
		int index = 0, tmp, val;
		while(digit>0){
			val = digit%10;
			digit = digit/10;
			arr[index] = val;
			index++;
		}
		// Arrays.sort(arr) can also be used for below
		for (int j = 0; j < arr.length; j++) {
			for (int j2 = j; j2 < arr.length; j2++) {
				if(arr[j]>arr[j2]){
					tmp = arr[j2];
					arr[j2]=arr[j];
					arr[j]=tmp;
				}
			}
		}
		System.out.print("The ascending order of number "+origDigit+" is ");
		System.out.println(Arrays.toString(arr).replace(", ", "").replace("[", "").replace("]", ""));
		System.out.print("The ascending order of number "+origDigit+" is ");
		System.out.println(Arrays.toString(arr).replaceAll("[\\W]", ""));
		System.out.print("The ascending order of number "+origDigit+" is ");
		for (int j : arr) {
			System.out.print(j);
		}
	}
	
}
