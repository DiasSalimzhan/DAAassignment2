public class LinkedList {
    private static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    private Node head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(int x) {
        if (head == null) {
            head = new Node(x);
        } else {
            Node curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = new Node(x);
        }
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            Node newNode = new Node(x);
            newNode.next = head;
            head = newNode;
        } else {
            Node curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.next;
            }
            Node newNode = new Node(x);
            newNode.next = curr.next;
            curr.next = newNode;
        }
        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int removedVal;
        if (index == 0) {
            removedVal = head.data;
            head = head.next;
        } else {
            Node curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.next;
            }
            removedVal = curr.next.data;
            curr.next = curr.next.next;
        }
        size--;
        return removedVal;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.data;
    }

    public boolean contains(int x) {
        Node curr = head;
        while (curr != null) {
            if (curr.data == x) return true;
            curr = curr.next;
        }
        return false;
    }

    public int size() {
        return size;
    }
}