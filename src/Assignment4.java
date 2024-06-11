/* 
    Write a Java function to get the index of the first number and the last number of a subarray where the sum of numbers is zero from a given array of integers.
    Input: [1, 2, 3, -6, 5, 4, 0]
    Output: [0, 3]
*/ 

public class Assignment4 {
    int[] findZeroFromSubArray(int[] input) {
        int length = input.length;
        int start = -1;
        int end = -1;

        for (int i = 0; i < length; i++) {
            int sum = 0;

            for (int j = i; j < length; j++) {
                sum += input[j];
                if (sum == 0) {
                    start = i;
                    end = j;
                    break;
                }
            }

            if (start != -1) break;
        }

        return new int[]{start, end};
    }
}
