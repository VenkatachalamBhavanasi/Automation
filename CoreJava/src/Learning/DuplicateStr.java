package Learning;

public class DuplicateStr {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input ="goodBaah";
		int count =0;
		
		char  b[] = input.toCharArray();
		
		for(int i=0; i<input.length();i++)
		{
			for (int j = i+1; j !='\0'; j++) {
				if(b[i]==b[j])
				{
					//count++;
					//System.out.println(b[j]);
					//break;
					for (int k = j; k !='\0'; k++) {
						
						b[k]=b[k+1];
						
					}
				}
				
			}
			System.out.println(b[i]);
		}
	

	}

}
