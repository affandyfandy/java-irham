
import java.util.ArrayList;
import java.util.List;

/* 
    Write a Java function to get index of the second biggest number from given array of integers
    Input: [1, 4, 3, -6, 5, 4]
    Output: [1, 5]
*/

public class Assignment3 {
    List<Integer> findSecondLargestIndex(int[] input) {
        if (input.length == 0) return new ArrayList<Integer>(){};

        List<Integer> result = new ArrayList<Integer>(){};
        int largest = input[0];
        int second = input[0];

        for (int number : input) {
            if (number > largest) {
                second = largest;
                largest = number;
            } else if (number > second && number < largest) {
                second = number;
            }
        }

        for (int i = 0; i < input.length; i++) {
            if (input[i] == second) {
                result.add(i);
            }
        }

        return result;
    }
}