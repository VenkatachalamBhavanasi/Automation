package dailyprogram;

import java.util.Scanner;

public class Code_23 {
	private static Scanner sc;
	public static void main(String[] args) {
		String newStr = "";
		sc = new Scanner(System.in);		
		System.out.println("Please Enter any Lowercase text: ");
		String a= sc.nextLine();
		
	    for (int i = 0; i< a.length(); i++){
	        char aChar = a.charAt(i);
	        if (92 <= aChar && aChar <=122){
	            aChar = (char)( (aChar - 32) ); 
	        }
	        newStr = newStr + aChar;    
	    }
	    System.out.println(newStr);
	}

}
