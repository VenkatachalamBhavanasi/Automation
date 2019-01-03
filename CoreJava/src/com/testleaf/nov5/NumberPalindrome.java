package com.testleaf.nov5;

import java.util.Scanner;

public class NumberPalindrome {
	//single digit and exit program //stop execution

	public static void main(String[] args) {
		int reverseNumber = 0;
		System.out.println("Enter input value");
		Scanner in = new Scanner(System.in);
		int inputNumber = in.nextInt();
		int temp = inputNumber;
		if (inputNumber < 0) {
			inputNumber = inputNumber * -1;
		}
		while (inputNumber != 0) {
			int reminder = inputNumber % 10;
			int quotient = inputNumber / 10;
			reverseNumber = reverseNumber * 10 + reminder;
			inputNumber = quotient;
			//reduce space input value reuse.
		}
		if (temp == reverseNumber) {
			System.out.println("given Input is Plaindrome \n" + reverseNumber);
		} else {
			System.out.println("given number is not palindrome \n" + temp);
		}

	}

}
