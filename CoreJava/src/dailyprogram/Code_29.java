package dailyprogram;

import java.util.regex.Pattern;

public class Code_29 {

	public static void main(String[] args) {
		String s ="12345";
		String s1="123456";	
		String s2="123.45";
		String p="[\\d]{5}";
		System.out.println("Input: "+s+"\tOutput : "+Pattern.matches(p, s));
		System.out.println("Input: "+s1+"\tOutput : "+Pattern.matches(p, s1));	
		System.out.println("Input: "+s2+"\tOutput : "+Pattern.matches(p, s2));
	}

}
