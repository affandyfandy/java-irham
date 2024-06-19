import java.util.Arrays;

public class ArraySort implements Runnable {
    private int[] array;
    private int left;
    private int right;

    public ArraySort(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        sort(array, left, right);
    }

    public void sort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            Thread leftSorter = new Thread(new ArraySort(array, left, middle));
            Thread rightSorter = new Thread(new ArraySort(array, middle + 1, right));

            leftSorter.start();
            rightSorter.start();

            try {
                leftSorter.join();
                rightSorter.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            merge(array, left, middle, right);
        }
    }

    public void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, middle + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        int[] array = {38, 27, 43, 3, 9, 82, 10};

        System.out.println("Original array: " + Arrays.toString(array));

        ArraySort sorter = new ArraySort(array, 0, array.length - 1);
        sorter.sort(array, 0, array.length - 1);

        System.out.println("Sorted array: " + Arrays.toString(array));
    }
}
