package dailyprogram;

import java.util.Scanner;

import org.apache.commons.lang3.ArrayUtils;

public class Code_17 {

	public static void main(String[] args) {
		/*Write a simple Java program to print the numbers which has the greatest sum across the diagonal. 
		 * Code must be generic for NxN matrix.
		Ex -  1      2     3     4
			  5      6     7     8
			  9      10    11    12
			  13     14    15    16
		The diagonal is 4,7,10,13.  
		The numbers across the diagonal are 1,2,3,5,6,9 and 8,11,12,14,15,16. 
		Their sum is 22 and 76. So the output should be 8,11,12,14,15,16.*/
		System.out.println("Enter the Number:");
		Scanner scan = new Scanner(System.in);
		int rowCount = scan.nextInt();
		int matrix[][] = new int[rowCount][rowCount];
		int arrSize = ((rowCount*rowCount)-rowCount)/2;
		int upperDiag[] = new int[arrSize];
		int lowerDiag[] = new int[arrSize];
		int upperDiagSum = 0,lowerDiagSum = 0,pos1=0,pos2=rowCount-1,k=0,l=0;
		for(int i=0;i<rowCount;i++){
			for(int j=0;j<rowCount;j++){
				System.out.print("Enter the matrix value for Row-"+(i+1)+" Column-"+(j+1)+":");
				matrix[i][j]=scan.nextInt();
			}
		}
		scan.close();
		for(int i=0;i<rowCount;i++){
			for(int j=0;j<rowCount;j++){
				if(matrix[i][j]!=matrix[pos1][pos2]){
					if(j<pos2){
						upperDiagSum=upperDiagSum+matrix[i][j];
						upperDiag[k]=matrix[i][j];
						k++;
					}
					if(j>pos2){
						lowerDiagSum=lowerDiagSum+matrix[i][j];
						lowerDiag[l]=matrix[i][j];
						l++;
					}
				}
			}
			pos1=pos1+1;
			pos2=pos2-1;
		}
		System.out.println("sum of upper half:"+upperDiagSum);
		System.out.println("sum of lower half:"+lowerDiagSum);
		System.out.print("half which is greater is ");
		if(upperDiagSum>lowerDiagSum){
			System.out.print(ArrayUtils.toString(upperDiag));
		}else{
			System.out.print(ArrayUtils.toString(lowerDiag));
		}
	}
}

