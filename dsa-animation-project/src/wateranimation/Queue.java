package wateranimation;

import java.util.*;

public class Queue {
    private final java.util.Queue<Integer> data = new ArrayDeque<>();

    public void enqueue(int value) { data.offer(value); }

    public int dequeue() {
        Integer value = data.poll();
        if (value == null) throw new NoSuchElementException("Queue is empty");
        return value;
    }

    public int front() {
        Integer value = data.peek();
        if (value == null) throw new NoSuchElementException("Queue is empty");
        return value;
    }

    public boolean isEmpty() { return data.isEmpty(); }
    public int size() { return data.size(); }
}
