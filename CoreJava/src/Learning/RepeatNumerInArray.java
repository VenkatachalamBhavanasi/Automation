package Learning;

public class RepeatNumerInArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a[] = {5,6,44,10,5,6};
		
		for(int i=0;i<a.length;i++){
			for(int j=i+1;j<a.length;j++){
				if(a[j]==a[i])
				{
					System.out.println(a[i]);
				break;
				}
			}
		}

	}

}
