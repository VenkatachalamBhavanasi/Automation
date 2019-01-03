package dailyprogram;

import java.util.Scanner;

public class Code_33 {

	public static void main(String[] args) {
		/* 	   1
	    	1  2  3
  		1   2  3  4  5
	    	1  2  3
	    	   1	
Note : If input is even, terminate the program else continue to print the above start pattern.*/
		Scanner input = new Scanner(System.in);  
		System.out.print("Please enter a Number to print pattern: ");  
		int numb = input.nextInt();  
		input.close();
		if (numb%2 != 0) {
			for (int topRow = 1; topRow <= numb; topRow++) {
				if (topRow%2!=0) {
					for (int i = topRow; i <= numb; i++) {
						System.out.print(" ");
					}
					for (int i = 1; i <= topRow; i++) {
						System.out.print(i +" ");
					}
					System.out.println();
				}
			}
			for (int bottomRow = numb-2; bottomRow >= 1; bottomRow--) {
				if (bottomRow%2!=0) {
					for (int i = bottomRow; i <= numb; i++) {
						System.out.print(" ");
					}
					for (int i = 1; i <= bottomRow; i++) {
						System.out.print(i +" ");
					}
					System.out.println();
				}
			}
		} else {
			System.out.println("Enter an Odd number to print the pattern...");
		}
		
	}
}
