package dailyprogram;

public class Code_05 {

	public static void main(String[] args) {
		/*Write a Java Program to print the Armstrong number from 100 to 1000.
		Note : Armstrong number is a number that is equal to the sum of cubes of its digits. 
		For example 153 is an Armstrong number since 1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153.*/
		for(int i=100;i<1000;i++)
		{
			int n=i,sum=0;
			int numLen = String.valueOf(i).length();
			while(n>0)
			{
				int r=n%10;
				sum=sum + r*r*r; //(int)(Math.pow(r, numLen))
				n=n/10;
			}
			if (i==sum)
			{
				System.out.println(i+" is Amstrong number");
			}
		}
	}

}
