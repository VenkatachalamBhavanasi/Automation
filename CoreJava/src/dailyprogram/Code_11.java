package dailyprogram;

import java.util.Scanner;

public class Code_11 {

	public static void main(String[] args) {
		/* Write a java program to find the percentage of 
		 * uppercase letters, lowercase letters, digits and other special characters(including space) 
		 * in the given string.
		 * For Ex- “Tiger Runs @ The Speed Of 100 km/hour.”,
		 * Number of uppercase letters is 5. So percentage is 13.16%
		 * Number of lowercase letters is 20. So percentage is 52.63%
		 * Number of digits is 3. So percentage is 7.89%
		 * Number of other characters is 10. So percentage is 26.32%*/
		Scanner input = new Scanner(System.in);  
		System.out.print("Please enter a Password: ");  
		String password1 = input.nextLine();  
		input.close();
		int n=password1.length();
		char c;
		int up,lo,num,spc;
		up=lo=num=spc=0;
		for(int i=0;i<n;i++){
			c = password1.charAt(i); 
			if(Character.isUpperCase(c))
				up++;
			else if(Character.isLowerCase(c))
				lo++;
			else if(Character.isDigit(c))
				num++;
			else
				spc++;
		}
		float f1,f2,f3,f4;
		f1=((up*100)/n);
		f2=((lo*100)/n);
		f3=((num*100)/n);
		f4=((spc*100)/n);
		System.out.println("Number of uppercase letters is "+up+". So percentage is "+f1+"%");
		System.out.println("Number of lowercase letters is "+lo+". So percentage is "+f2+"%");
		System.out.println("Number of digits is "+num+". So percentage is "+f3+"%");
		System.out.println("Number of other characters is "+spc+". So percentage is "+f4+"%");
	}

}
