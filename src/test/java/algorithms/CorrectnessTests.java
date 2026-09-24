package algorithms;

import org.algorithms.*;
import java.util.Arrays;
import java.util.Random;

public class CorrectnessTests {
    private static final Random rand = new Random();

    public static void main(String[] args) {
        System.out.println("=== Starting Correctness Tests ===");
        testSorting();
        testDeterministicSelect();
        testClosestPair();
        System.out.println("=== All tests completed! ===");
    }

    private static void testSorting() {
        System.out.println("\n--- Testing Sorts ---");
        int[][] testCases = {
                generateArray(1000, "Random"),
                generateArray(1000, "Sorted"),
                generateArray(1000, "Reverse"),
                generateArray(1000, "Duplicates"),
                {},
                {42}
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] original = testCases[i];
            int[] expected = original.clone();
            Arrays.sort(expected);

            int[] msData = original.clone();
            new MergeSorter().sort(msData);
            boolean msPass = Arrays.equals(expected, msData);

            int[] qsData = original.clone();
            new QuickSorter().sort(qsData);
            boolean qsPass = Arrays.equals(expected, qsData);

            System.out.println("Test case " + (i + 1) + " (length " + original.length + "): MergeSort=" + msPass + " | QuickSort=" + qsPass);
        }
    }

    private static void testDeterministicSelect() {
        System.out.println("\n--- Testing Deterministic Select (100 runs) ---");
        int passed = 0;
        DeterministicSelector selector = new DeterministicSelector();

        for (int i = 0; i < 100; i++) {
            int[] arr = generateArray(500, "Random");
            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            int k = rand.nextInt(arr.length);
            int result = selector.select(arr.clone(), k);

            if (result == sorted[k]) passed++;
        }
        System.out.println("Select Correct: " + passed + "/100 passed");
    }

    private static void testClosestPair() {
        System.out.println("\n--- Testing Closest Pair ---");
        int n = 1500; // Small dataset (n <= 2000)
        Point[] pts = new Point[n];
        for(int i=0; i<n; i++) {
            pts[i] = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }

        ClosestPairSolver solver = new ClosestPairSolver();
        double bruteForceResult = solver.bruteForce(pts, 0, pts.length - 1);
        double divideConquerResult = solver.findClosest(pts);

        boolean isCorrect = Math.abs(bruteForceResult - divideConquerResult) < 1e-6;
        System.out.println("Closest Pair (n=" + n + ") Correct: " + isCorrect);
    }

    private static int[] generateArray(int size, String type) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            if (type.equals("Random")) arr[i] = rand.nextInt(10000);
            else if (type.equals("Sorted")) arr[i] = i;
            else if (type.equals("Reverse")) arr[i] = size - i;
            else if (type.equals("Duplicates")) arr[i] = rand.nextInt(5);
        }
        return arr;
    }
}