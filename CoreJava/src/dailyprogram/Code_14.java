package dailyprogram;

public class Code_14 {

	public static void main(String[] args) {
		int sum = 0;
		for (int i = 3; i < 100 ; i++) {
			if (i%3 == 0 || i%5 == 0) {
				sum += i;
			}
		}
		System.out.println("Sum of Multiples of 3 or 5 less than 100 is " + sum);
	}
}
