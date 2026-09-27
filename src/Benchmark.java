import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Benchmark {
    private static final int[] N_VALUES = {100, 1000, 10000, 100000};
    private static final int REPETITIONS = 5;
    private static final long SEED = 42;

    public static void main(String[] args) {
        System.out.println("Starting Benchmarks...");
        runWorkload1();
        runWorkload2();
        runWorkload3();
        runWorkload4();
        System.out.println("Benchmarks finished. Export results to CSV for plotting.");
    }

    private static void runWorkload1() {
        System.out.println("\n--- Workload 1: Random Access ---");
        for (int n : N_VALUES) {
            long totalTimeDA = 0;
            long totalTimeLL = 0;

            for (int r = 0; r < REPETITIONS; r++) {
                Random rand = new Random(SEED + r);
                DynamicArray da = new DynamicArray();
                LinkedList ll = new LinkedList();

                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt();
                    da.add(val);
                    ll.add(val);
                }

                int[] indices = new int[10000];
                for (int i = 0; i < 10000; i++) {
                    indices[i] = rand.nextInt(n);
                }

                // Dynamic Array Timing
                long start = System.nanoTime();
                for (int idx : indices) {
                    da.get(idx);
                }
                totalTimeDA += (System.nanoTime() - start);

                // Linked List Timing
                start = System.nanoTime();
                for (int idx : indices) {
                    ll.get(idx);
                }
                totalTimeLL += (System.nanoTime() - start);
            }

            System.out.printf("n=%d | DA Avg Time: %.2f ms | LL Avg Time: %.2f ms%n",
                    n, (totalTimeDA / (double) REPETITIONS) / 1e6, (totalTimeLL / (double) REPETITIONS) / 1e6);
        }
    }

    private static void runWorkload2() {
        System.out.println("\n--- Workload 2: Search ---");
        for (int n : N_VALUES) {
            long totalTimeDA = 0;
            long totalTimeLL = 0;

            for (int r = 0; r < REPETITIONS; r++) {
                Random rand = new Random(SEED + r);
                DynamicArray da = new DynamicArray();
                LinkedList ll = new LinkedList();

                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt();
                    da.add(val);
                    ll.add(val);
                }

                int[] searchVals = new int[1000];
                for (int i = 0; i < 1000; i++) {
                    searchVals[i] = rand.nextInt();
                }

                long start = System.nanoTime();
                for (int val : searchVals) {
                    da.contains(val);
                }
                totalTimeDA += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int val : searchVals) {
                    ll.contains(val);
                }
                totalTimeLL += (System.nanoTime() - start);
            }

            System.out.printf("n=%d | DA Search Avg: %.2f ms | LL Search Avg: %.2f ms%n",
                    n, (totalTimeDA / (double) REPETITIONS) / 1e6, (totalTimeLL / (double) REPETITIONS) / 1e6);
        }
    }

    private static void runWorkload3() {
        System.out.println("\n--- Workload 3: Insert & Remove (Index 0 vs n/2) ---");
        for (int n : N_VALUES) {
            long timeDA_Insert0 = 0, timeLL_Insert0 = 0;
            long timeDA_InsertMid = 0, timeLL_InsertMid = 0;

            for (int r = 0; r < REPETITIONS; r++) {
                Random rand = new Random(SEED + r);
                DynamicArray da = new DynamicArray();
                LinkedList ll = new LinkedList();
                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt();
                    da.add(val);
                    ll.add(val);
                }

                // Insert at 0
                long start = System.nanoTime();
                for (int i = 0; i < 1000; i++) da.add(0, 99);
                timeDA_Insert0 += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < 1000; i++) ll.add(0, 99);
                timeLL_Insert0 += (System.nanoTime() - start);
            }

            System.out.printf("n=%d | DA Insert(0): %.2f ms | LL Insert(0): %.2f ms%n",
                    n, (timeDA_Insert0 / (double) REPETITIONS) / 1e6, (timeLL_Insert0 / (double) REPETITIONS) / 1e6);
        }
    }

    private static void runWorkload4() {
        System.out.println("\n--- Workload 4: Priority Processing (Min-Heap) ---");
        for (int n : N_VALUES) {
            long totalInsertTime = 0;
            long totalExtractTime = 0;

            for (int r = 0; r < REPETITIONS; r++) {
                Random rand = new Random(SEED + r);
                MinHeap heap = new MinHeap();
                int[] data = new int[n];
                for (int i = 0; i < n; i++) data[i] = rand.nextInt();

                long start = System.nanoTime();
                for (int val : data) heap.insert(val);
                totalInsertTime += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < n; i++) heap.extractMin();
                totalExtractTime += (System.nanoTime() - start);
            }

            System.out.printf("n=%d | Heap Insert Total: %.2f ms | Heap Extract Total: %.2f ms%n",
                    n, (totalInsertTime / (double) REPETITIONS) / 1e6, (totalExtractTime / (double) REPETITIONS) / 1e6);
        }
    }
}