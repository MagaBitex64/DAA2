package algorithms;

import metrics.PerformanceTracker;
import java.util.Objects;

public class InsertionSort {

    public static class Config {
        public boolean useBinarySearch = false; 
        public boolean countShiftsAsSwaps = true; 
    }

    private final Config config;

    public InsertionSort() { this(new Config()); }
    public InsertionSort(Config config) { this.config = Objects.requireNonNull(config); }

    public void sort(int[] a, PerformanceTracker tracker) {
        if (a == null) throw new IllegalArgumentException("Input array is null");
        if (tracker == null) throw new IllegalArgumentException("PerformanceTracker is null");

        int n = a.length;
        tracker.addAllocations(1); 

        for (int i = 1; i < n; i++) {
            tracker.incArrayAccess(); 
            int key = a[i];
            int j = i - 1;

            tracker.incComparison(); 
            if (j >= 0 && key >= a[j]) {
                continue;
            }

            // find insertion position
            if (config.useBinarySearch) {
                int pos = binarySearchInsertPosition(a, 0, i - 1, key, tracker);

                for (int k = i - 1; k >= pos; k--) {
                    tracker.incArrayAccess(); 
                    a[k + 1] = a[k];
                    tracker.incArrayAccess(); 
                    if (config.countShiftsAsSwaps) tracker.incSwap();
                }
                a[pos] = key;
                tracker.addArrayAccesses(1);
                if (config.countShiftsAsSwaps) tracker.incSwap();
            } else {
                while (j >= 0) {
                    tracker.incArrayAccess();
                    tracker.incComparison();
                    if (a[j] > key) {
                        a[j + 1] = a[j];
                        tracker.incArrayAccess();
                        if (config.countShiftsAsSwaps) tracker.incSwap();
                        j--;
                    } else {
                        break;
                    }
                }
                a[j + 1] = key;
                tracker.incArrayAccess();
            }
        }
    }


    private int binarySearchInsertPosition(int[] a, int lo, int hi, int key, PerformanceTracker tracker) {
        while (lo <= hi) {
            int mid = lo + ((hi - lo) >> 1);
            tracker.incArrayAccess();
            tracker.incComparison();
            if (a[mid] == key) {
                return mid + 1;
            } else if (a[mid] < key) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }
}
