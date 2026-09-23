package org.algorithms;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Experiment {
    public static void runExperiments() throws IOException {
        FileWriter csvWriter = new FileWriter("results/results.csv");
        csvWriter.append("Algorithm,InputType,Size,Time(ns),MaxDepth,Comparisons\n");

        int[] sizes = {1000, 10000, 100000};
        String[] types = {"Random", "Sorted", "Reverse", "Duplicates"};

        for (int size : sizes) {
            for (String type : types) {
                int[] data = generateData(size, type);

                // MergeSort
                MergeSorter ms = new MergeSorter();
                int[] msData = data.clone();
                long start = System.nanoTime();
                ms.sort(msData);
                long time = System.nanoTime() - start;
                csvWriter.append(String.format("MergeSort,%s,%d,%d,%d,%d\n", type, size, time, ms.maxDepth, ms.comparisons));

                // QuickSort
                QuickSorter qs = new QuickSorter();
                int[] qsData = data.clone();
                start = System.nanoTime();
                qs.sort(qsData);
                time = System.nanoTime() - start;
                csvWriter.append(String.format("QuickSort,%s,%d,%d,%d,%d\n", type, size, time, qs.maxDepth, qs.comparisons));
            }
        }
        csvWriter.flush();
        csvWriter.close();
        System.out.println("Experiments completed. Results saved to results.csv");
    }

    private static int[] generateData(int size, String type) {
        int[] arr = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            if (type.equals("Random")) arr[i] = rand.nextInt();
            else if (type.equals("Sorted")) arr[i] = i;
            else if (type.equals("Reverse")) arr[i] = size - i;
            else if (type.equals("Duplicates")) arr[i] = rand.nextInt(10);
        }
        return arr;
    }
}