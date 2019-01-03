package com.testleaf.nov5;

import java.util.Scanner;

public class SwapStrings {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Enter two strings");
		Scanner input = new Scanner(System.in);
		String inputValue1 = input.next();
		String inputValue2 = input.next();

		inputValue1 = inputValue1 + inputValue2;
		System.out.println(inputValue1);
		System.out.println(inputValue1.length());
		System.out.println(inputValue2.length());
		System.out.println(inputValue1.length() - inputValue2.length());

		inputValue2 = inputValue1.substring(0, inputValue1.length() - inputValue2.length());
		System.out.println(inputValue2);

		inputValue1 = inputValue1.substring(inputValue2.length());
		System.out.println(inputValue1+"     "+inputValue2);

	}

}
