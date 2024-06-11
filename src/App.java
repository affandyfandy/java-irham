import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("===== Assignment 3 Section =====");
        Assignment3 assignment3 = new Assignment3();
        int[] input3 = {1,4,3,-6,5,4};
        
        System.out.println("INPUT   : " + Arrays.toString(input3));
        System.out.println("OUTPUT  : " + assignment3.findSecondLargestIndex(input3));


        System.out.println();

        System.out.println("===== Assignment 4 Section =====");
        Assignment4 assignment4 = new Assignment4();
        int[] input4 = {1,2,3,-6,5,4,0};

        System.out.println("INPUT   : " + Arrays.toString(input4));
        System.out.println("OUTPUT  : " + Arrays.toString(assignment4.findZeroFromSubArray(input4)));
    }
}
