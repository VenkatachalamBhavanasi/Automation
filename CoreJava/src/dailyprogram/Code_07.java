package dailyprogram;

import java.util.Scanner;

public class Code_07 {

	public static void main(String[] args) {
		// Leap Year or Not
		System.out.println("Enter the Year to check Leap year or not:");
		Scanner var1=new Scanner(System.in);
		int n=var1.nextInt();
		var1.close();
		if(((n%4==0)&&(n%100!=0)) || n%400 == 0){
			System.out.println("Given year is leap year");
		}else{
			System.out.println("Given year is not leap year");
		}
	}

}
