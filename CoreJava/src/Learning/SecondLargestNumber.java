package Learning;

import java.util.Scanner;

public class SecondLargestNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enter range of array");
		int largestNumber =Integer.MIN_VALUE;
		int secondLargest = Integer.MAX_VALUE;
		
		Scanner sc= new Scanner(System.in);
		int n = sc.nextInt();
		int a[] =new int [n];
		
		for(int i=0;i<n;i++)
		{
			a[i]=sc.nextInt();
		}
		
		for(int j=0; j<n; j++ )
		{
			if(largestNumber < a[j])
			{
				secondLargest=largestNumber;
				largestNumber=a[j];
			}
			else if (secondLargest<a[j]) {
				secondLargest=a[j];
			}
			
			
		}
		for(int k=0;k<n;k++)
		{
			System.out.println(a[k]);
		}
		System.out.println("Largest Number"+largestNumber);
		System.out.println("Seco"+ secondLargest);
		
		

	}

}
