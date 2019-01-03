package dailyprogram;

public class Code_08 {

	public static void main(String[] args) {
		/*Write a Java program to find the second maximum number in an integer array.
		int[] intArray = {20, 340, 21, 879, 92, 21,474,83647,-200};
		The second largest number is 879.
		Your code should work even when all the integers of the array is negative numbers.*/
		int a[]={13,67,88,65,13,95,67,65,87,95,99,99};
		int n=a.length;
		int big = Integer.MIN_VALUE;
		int secondbig = Integer.MIN_VALUE;
		for(int i=0;i<n;i++){
			if(big<a[i]){
				secondbig = big;
				big = a[i];
			} 
			else if (a[i] != big && secondbig < a[i]){
				secondbig = a[i];
			}
		}
		System.out.println(big+" is the first largest number.");
		System.out.println(secondbig+" is the second largest number.");
	}
}
