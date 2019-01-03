package dailyprogram;


public class Code_28 {

	public static void main(String[] args) {
		String s =" aa  bbbbb   cc   d   ";
		System.out.println(s.replaceAll("\\s+", " ").replaceAll("\\s+$", ""));
		//("\\s+", " ") replaces all spaces(1 or more) with only one space
		//("\\s+$", "") removes the trailing spaces.
	}

}
