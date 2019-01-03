package dailyprogram;

import java.util.Scanner;

public class Code_04_1 {

	public static void main(String[] args) {
		int startNumb,endNumb;
		Scanner getinput=new Scanner(System.in);
		System.out.println("Enter the starting number");
		startNumb=getinput.nextInt();
		System.out.println("Enter the ending number");
		endNumb=getinput.nextInt();
		for (int i = startNumb; i <= endNumb; i++) {
            if (i % 15 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(String.valueOf(i));
            }
        }
		getinput.close();
	}

}
