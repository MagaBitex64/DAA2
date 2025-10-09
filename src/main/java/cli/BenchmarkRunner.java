package cli;

import algorithms.InsertionSort;
import metrics.PerformanceTracker;

import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class BenchmarkRunner {

    private static int[] generateInput(int n, String type) {
        int[] arr = new int[n];
        Random rand = new Random(42);

        switch (type) {
            case "sorted":
                for (int i = 0; i < n; i++) arr[i] = i;
                break;
            case "reverse":
                for (int i = 0; i < n; i++) arr[i] = n - i;
                break;
            case "nearly":
                for (int i = 0; i < n; i++) arr[i] = i;
                for (int i = 0; i < n / 100; i++) {
                    int a = rand.nextInt(n);
                    int b = rand.nextInt(n);
                    int tmp = arr[a]; arr[a] = arr[b]; arr[b] = tmp;
                }
                break;
            default:
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt(n * 10);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};
        String[] types = {"sorted", "reverse", "nearly", "random"};

        System.out.printf("%-12s %-8s %-12s %-12s %-12s %-12s%n",
                "Type", "n", "Comparisons", "Swaps", "Accesses", "Time(ms)");

        try (FileWriter fw = new FileWriter("benchmark_results.csv")) {
            fw.write("Type,n,Comparisons,Swaps,Accesses,Time(ms)\n");

            for (String type : types) {
                for (int n : sizes) {
                    int[] arr = generateInput(n, type);
                    PerformanceTracker tracker = new PerformanceTracker();
                    InsertionSort sorter = new InsertionSort();

                    tracker.startTimer();
                    sorter.sort(Arrays.copyOf(arr, arr.length), tracker);
                    tracker.stopTimer();


                    System.out.printf("%-12s %-8d %-12d %-12d %-12d %-12d%n",
                            type, n,
                            tracker.getComparisons(),
                            tracker.getSwaps(),
                            tracker.getArrayAccesses(),
                            tracker.getElapsedTimeMillis());


                    fw.write(String.format("%s,%d,%d,%d,%d,%d\n",
                            type, n,
                            tracker.getComparisons(),
                            tracker.getSwaps(),
                            tracker.getArrayAccesses(),
                            tracker.getElapsedTimeMillis()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
