package dailyprogram;

public class Pyramid {

	public static void main(String[] args) {
		//  To print the numbers in a specific required pyramid format
		
		int iNUm=8;
		int iCount=3;
		int jCount=1;
		boolean isUpper = true;
		for (int i=1; i<=iNUm;i++){
			System.out.println("");
			
			for (int j=1;j<=iCount;j++){
				System.out.print(" ");
			}
			
			for (int k=1;k<=jCount;k++){
				System.out.print(k);
			}
			
			if (isUpper) {
				jCount=jCount+2;
				iCount=iCount-1;
			} else {
				jCount = jCount - 2;
				iCount = iCount + 1;
			}
			if (iCount == 0) {
				isUpper = false;
			}
			
		}
		
		
	}

}
