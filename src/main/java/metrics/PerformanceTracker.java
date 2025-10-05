package metrics;

public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long arrayAccesses = 0;
    private long allocations = 0; 

    public void addComparisons(long n) { comparisons += n; }
    public void addSwaps(long n) { swaps += n; }
    public void addArrayAccesses(long n) { arrayAccesses += n; }
    public void addAllocations(long n) { allocations += n; }

    public void incComparison() { comparisons++; }
    public void incSwap() { swaps++; }
    public void incArrayAccess() { arrayAccesses++; }
    public void incAllocation() { allocations++; }

    public void reset() {
        comparisons = swaps = arrayAccesses = allocations = 0;
    }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getArrayAccesses() { return arrayAccesses; }
    public long getAllocations() { return allocations; }

    public String report() {
        return String.format("comparisons=%d, swaps=%d, arrayAccesses=%d, allocations=%d",
                comparisons, swaps, arrayAccesses, allocations);
    }
}
