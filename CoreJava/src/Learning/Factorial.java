package Learning;

import java.util.Scanner;

public class Factorial {
	public static void main(String[] args) {
		
		int fact=1;
		Scanner Sc = new Scanner(System.in);
		int input= Sc.nextInt();
		for(int i=1;i<=input;i++)
		{
			fact=fact*i;
		}
		System.out.println(fact);
		
	}

}
