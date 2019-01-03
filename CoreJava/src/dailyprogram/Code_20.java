package dailyprogram;

import java.util.Scanner;

//import org.apache.commons.lang3.ArrayUtils;

public class Code_20 {
	private static Scanner sc;
	public static void main(String[] args) {
		/*Write a simple Java program to display the digits of the number in ascending order. 

		Ex.  Input = 186553. 
			Output = 13568.*/
		//getting input
		int number, digit, temp, index = 0; 
		sc = new Scanner(System.in);		
		System.out.println("\n Please Enter any Number: ");
		String s = sc.next();
		int a[] = new int[s.length()];
		number = Integer.parseInt(s);
		
		//Dividing the number in to an array
		while(number>0){
			digit = number%10;
			a[index] = digit;
			number = number/10;
			index++;
		}
		//Sorting the array
		for (int i = 0; i < index; i++) {
            for (int j = i + 1; j < index; j++) {
                if (a[i] > a[j]) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
		//print sorted Array
	//	System.out.println(ArrayUtils.toString(a));
		//Print array without duplicates
		System.out.print(a[0]);
		for (int i = 1; i < index; i++) {
        	if (a[i-1] != a[i]) {
        		System.out.print(a[i]);
        	}
		}
            
	}

}
