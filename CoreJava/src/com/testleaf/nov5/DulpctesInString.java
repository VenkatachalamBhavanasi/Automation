package com.testleaf.nov5;

import java.util.Scanner;

public class DulpctesInString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Enter any String");
		Boolean  found = false;
		Scanner sc = new Scanner(System.in);
		String value= sc.nextLine();
		String op="";
		for(int i=0; i<value.length();i++)
			
		{
			for (int j = 0; j < op.length(); j++) {
				if (value.charAt(i) == op.charAt(j)) {
                    found = true;
                    break; //don't need to iterate further
                }
				
				
			}
			 if (found == false) {
	                op = op.concat(String.valueOf(value.charAt(i)));
	            }
		}
		
		System.out.println(op);
		
		
		

	}

}
