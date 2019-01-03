package dailyprogram;

import java.util.Scanner;

public class Code_02 {

	public static void main(String[] args) {
		//Palindrome Number
		System.out.println("Enter the number for Palindrome:");
		Scanner scan=new Scanner(System.in);
		int numb=scan.nextInt();
		int rem,sum=0,origNumb;
		origNumb=numb;    
		while(numb>0){    
			rem=numb%10; 
			sum=(sum*10)+rem;    
			numb=numb/10;    
		}    
		if(origNumb==sum)    
			System.out.println(origNumb +" is a Palindrome Number.");    
		else    
			System.out.println(origNumb +" is a Not Palindrome.");  
		scan.close();
	}

}
