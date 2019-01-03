package dailyprogram;

public class Code_24 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int []arr={10,9,8,-7,6,5,4,2,-3,-2,1};
		int maxSequenceStartIndex = 0;
        int maxSequenceLength = 0;
        int currentSequenceStartIndex = 0;
        int currentSequenceLength = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] < 0) {
                if(currentSequenceLength > maxSequenceLength) {
                    maxSequenceLength = currentSequenceLength;
                    maxSequenceStartIndex = currentSequenceStartIndex;
                }
                currentSequenceStartIndex = i + 1;
                currentSequenceLength = 0;
            }
            else {
                currentSequenceLength++;
            }
        }

        if(currentSequenceLength > maxSequenceLength) {
            maxSequenceStartIndex = currentSequenceStartIndex;
            maxSequenceLength = currentSequenceLength;
        }
		for(int i=maxSequenceStartIndex;i<maxSequenceStartIndex+maxSequenceLength;i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
		System.out.println("Length is "+maxSequenceLength);
	}

}
