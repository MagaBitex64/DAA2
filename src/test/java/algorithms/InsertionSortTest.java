package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InsertionSortTest {

    @Test
    public void testEmpty() {
        int[] a = new int[0];
        InsertionSort sorter = new InsertionSort();
        PerformanceTracker t = new PerformanceTracker();
        sorter.sort(a, t);
        assertEquals(0, a.length);
    }

    @Test
    public void testSingle() {
        int[] a = new int[]{5};
        InsertionSort sorter = new InsertionSort();
        PerformanceTracker t = new PerformanceTracker();
        sorter.sort(a, t);
        assertArrayEquals(new int[]{5}, a);
    }

    @Test
    public void testSorted() {
        int[] a = new int[]{1,2,3,4,5};
        InsertionSort sorter = new InsertionSort();
        PerformanceTracker t = new PerformanceTracker();
        sorter.sort(a, t);
        assertArrayEquals(new int[]{1,2,3,4,5}, a);
    }

    @Test
    public void testReverse() {
        int[] a = new int[]{5,4,3,2,1};
        InsertionSort sorter = new InsertionSort();
        PerformanceTracker t = new PerformanceTracker();
        sorter.sort(a, t);
        assertArrayEquals(new int[]{1,2,3,4,5}, a);
    }

    @Test
    public void testWithDuplicates() {
        int[] a = new int[]{3,1,2,3,1,2};
        InsertionSort sorter = new InsertionSort();
        PerformanceTracker t = new PerformanceTracker();
        sorter.sort(a, t);
        assertArrayEquals(new int[]{1,1,2,2,3,3}, a);
    }
}
