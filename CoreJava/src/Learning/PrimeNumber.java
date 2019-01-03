package Learning;

import java.util.Scanner;

public class PrimeNumber {

	public static void main(String[] args) {
	boolean isPrime =true;
	System.out.println("Enter the digit");
	Scanner sc = new Scanner (System.in);
	int inputNumber= sc.nextInt();
	for(int i=2;i<inputNumber;i++)
	{
		if(inputNumber%i==0)
		{
			isPrime=false;
			System.out.println("Not a Prime number"+"inputNumber");
			break;
			
		}
	}
	if(isPrime)
	System.out.println("PrimeNumber ="+ inputNumber);
	}

}
