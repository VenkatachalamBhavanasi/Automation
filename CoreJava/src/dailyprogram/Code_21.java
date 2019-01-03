package dailyprogram;

public class Code_21 {

	public static void main(String[] args) {
		String text = "Welcome to Test Leaf";
		int number = -123489;
		System.out.println(recursiveReverse(text));
		if (number > 0) {
			recursiveReverse(number);
		} else if (number < 0) {
			System.out.print("-");
			recursiveReverse(number*-1);
		} else {
			System.out.print(0);
		}
	}

	private static void recursiveReverse(int number) {
		if (number != 0){
			System.out.print(number%10);
			recursiveReverse(number/10);
		}
	}

	private static String recursiveReverse(String text) {
		if (text != null && text.length() == 1){
			return text;
		} else {
			return recursiveReverse(text.substring(1))+text.charAt(0);
		}
	}
}
