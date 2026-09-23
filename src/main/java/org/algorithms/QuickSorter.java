package org.algorithms;
import java.util.Random;

public class QuickSorter {
    public int maxDepth = 0;
    public long comparisons = 0;
    private final Random random = new Random();

    public void sort(int[] arr) {
        maxDepth = 0; comparisons = 0;
        if (arr != null && arr.length > 1) {
            quickSort(arr, 0, arr.length - 1, 1);
        }
    }

    private void quickSort(int[] arr, int low, int high, int depth) {
        while (low < high) {
            maxDepth = Math.max(maxDepth, depth);
            int pivotIndex = partition(arr, low, high);
            if (pivotIndex - low < high - pivotIndex) {
                quickSort(arr, low, pivotIndex - 1, depth + 1);
                low = pivotIndex + 1;
            } else {
                quickSort(arr, pivotIndex + 1, high, depth + 1);
                high = pivotIndex - 1;
            }
        }
    }

    private int partition(int[] arr, int low, int high) {
        int p = low + random.nextInt(high - low + 1);
        swap(arr, p, high);
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            comparisons++;
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
    }
}