package dailyprogram;

import java.util.Scanner;

public class Code_01 {

	public static void main(String[] args) {
		//Factorial 4!=24
		System.out.println("Enter the number for Factor:");
		Scanner scan=new Scanner(System.in);
		int numb=scan.nextInt();
		int i,fact=1;
		for(i=2;i<=numb;i++){    
			fact=fact*i;    
		}    
		System.out.println("Factorial of "+numb+" is: "+fact);   
		scan.close();
	}
//Factorial of 14 is: 1278945280 -- need to use long for finding factorial of numbers 
//Factorial of 13 is: 1932053504
//Factorial of 12 is: 479001600
}
