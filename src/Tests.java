import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Tests {

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("RUNNING ALL CORRECTNESS AND BOUNDARY TESTS");
        System.out.println("==========================================");

        testDynamicArray();
        testLinkedList();
        testMinHeap();

        System.out.println("\nALL TESTS PASSED SUCCESSFULLY!");
    }

    // ==========================================
    // 1. DYNAMIC ARRAY TESTS
    // ==========================================
    private static void testDynamicArray() {
        System.out.println("\n--- Testing DynamicArray ---");
        DynamicArray da = new DynamicArray();
        ArrayList<Integer> expected = new ArrayList<>();

        // 1. Edge Case: Empty structure
        assert da.size() == 0 : "DA size should be 0 initially";
        assert !da.contains(10) : "DA should not contain element when empty";

        // 2. Edge Case: Single element
        da.add(42);
        expected.add(42);
        assert da.size() == 1 : "DA size should be 1";
        assert da.get(0) == 42 : "DA element at 0 should be 42";

        // 3. Multiple elements & Duplicates
        da.add(10);
        da.add(42); // Duplicate
        da.add(5);
        expected.add(10);
        expected.add(42);
        expected.add(5);

        assert da.size() == 4 : "DA size should be 4";
        assert da.contains(42) : "DA should contain 42";
        assert da.contains(5) : "DA should contain 5";

        // 4. Boundary Indices & Insertion at index
        da.add(0, 99); // Insert at beginning
        expected.add(0, 99);
        assert da.get(0) == 99 : "DA index 0 should be 99";

        da.add(da.size(), 88); // Insert at end (boundary index)
        expected.add(88);
        assert da.get(da.size() - 1) == 88 : "DA last index should be 88";

        // 5. Removal & Boundary checks
        int removed = da.remove(0); // Remove from start
        int expectedRemoved = expected.remove(0);
        assert removed == expectedRemoved : "Removed element mismatch";

        // Compare with standard ArrayList
        for (int i = 0; i < da.size(); i++) {
            assert da.get(i) == expected.get(i) : "DA mismatch with Java ArrayList at index " + i;
        }

        // 6. Invalid Indices Exception handling
        try {
            da.get(-1);
            assert false : "Expected IndexOutOfBoundsException for index -1";
        } catch (IndexOutOfBoundsException e) {
            // Success
        }

        try {
            da.get(100);
            assert false : "Expected IndexOutOfBoundsException for index 100";
        } catch (IndexOutOfBoundsException e) {
            // Success
        }

        System.out.println("✔ DynamicArray tests passed!");
    }

    // ==========================================
    // 2. LINKED LIST TESTS
    // ==========================================
    private static void testLinkedList() {
        System.out.println("\n--- Testing LinkedList ---");
        LinkedList ll = new LinkedList();
        ArrayList<Integer> expected = new ArrayList<>();

        // 1. Edge Case: Empty structure
        assert ll.size() == 0 : "LL size should be 0 initially";

        // 2. Edge Case: Single element
        ll.add(100);
        expected.add(100);
        assert ll.size() == 1 : "LL size should be 1";
        assert ll.get(0) == 100 : "LL element should be 100";

        // 3. Multiple elements & Duplicates
        ll.add(200);
        ll.add(100); // Duplicate
        ll.add(300);
        expected.add(200);
        expected.add(100);
        expected.add(300);

        assert ll.size() == 4 : "LL size should be 4";
        assert ll.contains(300) : "LL should contain 300";

        // 4. Boundary Index Operations
        ll.add(0, 50); // Insert at head
        expected.add(0, 50);
        assert ll.get(0) == 50 : "LL head should be 50";

        // Remove from head
        int removedHead = ll.remove(0);
        int expectedHead = expected.remove(0);
        assert removedHead == expectedHead : "LL head removal mismatch";

        // Compare with standard Java list
        for (int i = 0; i < ll.size(); i++) {
            assert ll.get(i) == expected.get(i) : "LL mismatch with standard list at index " + i;
        }

        // 5. Invalid Indices Exception handling
        try {
            ll.remove(-1);
            assert false : "Expected IndexOutOfBoundsException for negative index";
        } catch (IndexOutOfBoundsException e) {
            // Success
        }

        try {
            ll.get(ll.size());
            assert false : "Expected IndexOutOfBoundsException for out of bounds index";
        } catch (IndexOutOfBoundsException e) {
            // Success
        }

        System.out.println("✔ LinkedList tests passed!");
    }

    // ==========================================
    // 3. MIN-HEAP TESTS
    // ==========================================
    private static void testMinHeap() {
        System.out.println("\n--- Testing MinHeap ---");
        MinHeap heap = new MinHeap();
        PriorityQueue<Integer> javaHeap = new PriorityQueue<>();

        // 1. Empty Heap Check
        assert heap.size() == 0 : "Heap size should be 0 initially";

        try {
            heap.peekMin();
            assert false : "Expected NoSuchElementException for empty peekMin";
        } catch (Exception e) {
            // Success
        }

        // 2. Insertions with Duplicates and Unordered Inputs
        int[] testValues = {45, 12, 89, 12, 3, 7, 1, 100, 0};
        for (int val : testValues) {
            heap.insert(val);
            javaHeap.add(val);
            // Verify Heap property after each insertion (Min element at top)
            assert heap.peekMin() == javaHeap.peek() : "Heap property violated after inserting " + val;
        }

        assert heap.size() == testValues.length : "Heap size mismatch";

        // 3. ExtractMin Verification (Must return in non-decreasing order)
        ArrayList<Integer> customExtracted = new ArrayList<>();
        ArrayList<Integer> expectedSorted = new ArrayList<>();

        while (heap.size() > 0) {
            int customMin = heap.extractMin();
            int javaMin = javaHeap.poll();

            assert customMin == javaMin : "Extracted min mismatch: " + customMin + " vs " + javaMin;

            customExtracted.add(customMin);
            expectedSorted.add(javaMin);
        }

        // Verify sorted order (non-decreasing)
        for (int i = 0; i < customExtracted.size() - 1; i++) {
            assert customExtracted.get(i) <= customExtracted.get(i + 1)
                    : "Extracted elements are not in non-decreasing order!";
        }

        System.out.println("✔ MinHeap tests passed!");
    }
}