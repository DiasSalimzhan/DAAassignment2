import java.util.Arrays;
import java.util.NoSuchElementException;

public class MinHeap {
    private int[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MinHeap() {
        this.heap = new int[DEFAULT_CAPACITY];
        this.size = 0;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }

    public void insert(int x) {
        ensureCapacity();
        heap[size] = x;
        size++;
        heapifyUp(size - 1);
    }

    public int peekMin() {
        if (size == 0) throw new NoSuchElementException("Heap is empty");
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) throw new NoSuchElementException("Heap is empty");
        int minVal = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return minVal;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index] < heap[parentIndex]) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        while (2 * index + 1 < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = leftChild;

            if (rightChild < size && heap[rightChild] < heap[leftChild]) {
                smallest = rightChild;
            }

            if (heap[index] > heap[smallest]) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public int size() {
        return size;
    }
}