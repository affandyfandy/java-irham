/* 
    Write a Java function to get the index of the first number and the last number of a subarray where the sum of numbers is zero from a given array of integers.
    Input: [1, 2, 3, -6, 5, 4, 0]
    Output: [0, 3]
*/

import java.util.HashMap;
import java.util.Map;

public class Assignment4 {
    int[] findZeroFromSubArray(int[] input) {
        Map<Integer, Integer> prefixSumMap = new HashMap<>();
        int sum = 0;
        prefixSumMap.put(0, -1); // Handle subarray starting from index 0
        
        for (int i = 0; i < input.length; i++) {
            sum += input[i];
            
            if (prefixSumMap.containsKey(sum)) {
                return new int[]{prefixSumMap.get(sum) + 1, i};
            }
            
            prefixSumMap.put(sum, i);
        }
        
        return new int[]{-1, -1}; // Return -1, -1 if no such subarray is found
    }
}
