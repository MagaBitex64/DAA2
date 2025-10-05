package cli;

import algorithms.InsertionSort;
import metrics.PerformanceTracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class BenchmarkRunner {
    private static final Random RAND = new Random(12345);

    public static void main(String[] args) throws Exception {
        int[] sizes = {100, 1000, 10000, 100000};
        int trials = 5;
        for (String a : args) {
            if (a.startsWith("--trials=")) trials = Integer.parseInt(a.split("=")[1]);
        }

        Path outDir = Path.of("docs", "performance-data");
        Files.createDirectories(outDir);

        runAll(sizes, trials, outDir.toFile());
    }

    public static void runAll(int[] sizes, int trials, File outDir) throws IOException {
        for (int size : sizes) {
            runForSize(size, trials, outDir);
        }
    }

    private static void runForSize(int n, int trials, File outDir) throws IOException {
        File csv = new File(outDir, "insertion_n_" + n + ".csv");
        try (BufferedWriter w = new BufferedWriter(new FileWriter(csv))) {
            w.write("inputType,trial,n,timeNs,comparisons,swaps,arrayAccesses,allocations\n");

            for (InputKind kind : InputKind.values()) {
                for (int t = 0; t < trials; t++) {
                    int[] arr = generateArray(n, kind);
                    PerformanceTracker tracker = new PerformanceTracker();
                    InsertionSort.Config cfg = new InsertionSort.Config();
                    cfg.useBinarySearch = false; 
                    InsertionSort sorter = new InsertionSort(cfg);

                    long start = System.nanoTime();
                    sorter.sort(arr, tracker);
                    long end = System.nanoTime();

                    if (!isSorted(arr)) throw new IllegalStateException("Sort failed for " + kind);

                    w.write(String.format("%s,%d,%d,%d,%d,%d,%d,%d\n",
                            kind.name(), t + 1, n, (end - start), tracker.getComparisons(), tracker.getSwaps(), tracker.getArrayAccesses(), tracker.getAllocations()));
                }
            }
        }
    }

    enum InputKind { RANDOM, SORTED, REVERSED, NEARLY_SORTED }

    private static int[] generateArray(int n, InputKind kind) {
        switch (kind) {
            case RANDOM:
                return RAND.ints(n, 0, Math.max(10, n)).toArray();
            case SORTED:
                return IntStream.range(0, n).toArray();
            case REVERSED:
                return IntStream.iterate(n, i -> i - 1).limit(n).map(i -> i - 1).toArray();
            case NEARLY_SORTED:
                int[] a = IntStream.range(0, n).toArray();
                int swaps = Math.max(1, n / 100);
                for (int i = 0; i < swaps; i++) {
                    int x = RAND.nextInt(n);
                    int y = RAND.nextInt(n);
                    int tmp = a[x]; a[x] = a[y]; a[y] = tmp;
                }
                return a;
            default:
                throw new IllegalArgumentException("Unknown kind");
        }
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }
}
