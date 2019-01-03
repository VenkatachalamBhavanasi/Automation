	package dailyprogram;

public class Code_16 {

	public static void main(String[] args) {
		int a[][] = {   {1,  2,  3,  4,},
						{5,  6,  7,  8,}, 
						{9,  10, 11, 12},
						{13, 14, 15, 16} };
		int m=4, n=4, i, k = 0, l = 0;
		System.out.println("Given Matrix for spiral form:");
		for (int j = 0; j < m; j++) {
			for (int j2 = 0; j2 < n; j2++) {
				System.out.print(a[j][j2]+" ");
			}
			System.out.println();
		}

		/*  k - starting row index
	        m - ending row index
	        l - starting column index
	        n - ending column index
	        i - iterator
		 */
		System.out.println("Sprial form content");
		while (k < m && l < n) {
			/* Print the first row from the remaining rows */
			for (i = l; i < n; ++i) {
				System.out.print(a[k][i] +", ");
			}
			k++;//increase row index

			/* Print the last column from the remaining columns */
			for (i = k; i < m; ++i) {
				System.out.print(a[i][n-1] +", ");
			}
			n--;//decrease column index

			/* Print the last row from the remaining rows */
			if ( k < m) {
				for (i = n-1; i >= l; --i) {
					System.out.print(a[m-1][i] +", ");
				}
				m--;
			}

			/* Print the first column from the remaining columns */
			if (l < n) {
				for (i = m-1; i >= k; --i) {
					System.out.print(a[i][l] +", ");
				}
				l++;    
			}        
		}
	}
}


