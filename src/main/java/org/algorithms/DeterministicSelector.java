package org.algorithms;
import java.util.Arrays;

public class DeterministicSelector {
    public int maxDepth = 0;
    public long comparisons = 0;

    public int select(int[] arr, int k) {
        maxDepth = 0; comparisons = 0;
        if (arr == null || k < 0 || k >= arr.length) throw new IllegalArgumentException();
        return select(arr, 0, arr.length - 1, k, 1);
    }

    private int select(int[] arr, int low, int high, int k, int depth) {
        maxDepth = Math.max(maxDepth, depth);
        if (low == high) return arr[low];

        int pivotIndex = medianOfMedians(arr, low, high);
        pivotIndex = partition(arr, low, high, pivotIndex);

        if (k == pivotIndex) return arr[k];
        else if (k < pivotIndex) return select(arr, low, pivotIndex - 1, k, depth + 1);
        else return select(arr, pivotIndex + 1, high, k, depth + 1);
    }

    private int medianOfMedians(int[] arr, int low, int high) {
        int n = high - low + 1;
        if (n <= 5) {
            Arrays.sort(arr, low, high + 1);
            return low + n / 2;
        }
        int numGroups = (int) Math.ceil(n / 5.0);
        int[] medians = new int[numGroups];
        for (int i = 0; i < numGroups; i++) {
            int groupLow = low + i * 5;
            int groupHigh = Math.min(low + i * 5 + 4, high);
            Arrays.sort(arr, groupLow, groupHigh + 1);
            medians[i] = arr[groupLow + (groupHigh - groupLow) / 2];
        }
        DeterministicSelector auxSelector = new DeterministicSelector();
        int medianOfMediansValue = auxSelector.select(medians, numGroups / 2);
        this.comparisons += auxSelector.comparisons;

        for (int i = low; i <= high; i++) {
            if (arr[i] == medianOfMediansValue) return i;
        }
        return low;
    }

    private int partition(int[] arr, int low, int high, int pivotIndex) {
        int pivot = arr[pivotIndex];
        swap(arr, pivotIndex, high);
        int i = low;
        for (int j = low; j < high; j++) {
            comparisons++;
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
    }
}