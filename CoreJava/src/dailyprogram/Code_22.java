package dailyprogram;

public class Code_22 {

	public static void main(String[] args) {
		// Declare two strings
        String a = "Hello123";
        String b = "World";
         
        // Print String before swapping
        System.out.println("Strings before swap: a = " + a + " and b = "+b);
         
        //Initial length of a
        int aLen = a.length();
        // append 2nd string to 1st
        a = a + b;
         
        // store intial string a in string b
        b = a.substring(0,aLen);
         
        // store initial string b in string a
        a = a.substring(b.length());
         
        // print String after swapping
        System.out.println("Strings after swap: a = " + a + " and b = " + b);  
	}

}
