package Learning;

import java.util.Scanner;

public class Palindrome {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int rem,sum=0;
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		while(input>0)
		{
			rem=input%10;
			input=input/10;
			sum=sum*10+rem;
		}
		System.out.println(sum);
				

	}

}
