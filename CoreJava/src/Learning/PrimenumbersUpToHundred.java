package Learning;

public class PrimenumbersUpToHundred {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Prime numbers up to 100 are:");
		boolean isPrime = true;
		
		for(int i=1;i<=100;i++)
		{
			int count = 0;
			for(int j=2;j<=i/2;j++)
			{
				if(i%j==0)
				{
				count=count+1;
					isPrime = false;
                    break;
				}
				
			}
			if(count==0 && i!=1)
				System.out.println("prime numbers are" +i);
		}

	}

}
