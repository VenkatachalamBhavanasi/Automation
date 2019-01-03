package Learning;

import java.util.Scanner;

public class PalinromeString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc= new Scanner(System.in);
		String orginal,reverse ="";
		orginal= sc.next();
		int n= orginal.length();
		for(int i=n-1; i>=0;i--)
		{
			reverse=reverse+orginal.charAt(i);
		}
		if(orginal.equalsIgnoreCase(reverse))
		{
			System.out.println("Palindrome");
		}
		else{
			System.out.println("NOT a Palindrome");
		}
		
		

	}

}
