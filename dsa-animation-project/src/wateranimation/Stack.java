package wateranimation;

import java.util.*;

public class Stack {
    private final List<Integer> data = new ArrayList<>();

    public void push(int value) { data.add(value); }

    public int pop() {
        if (data.isEmpty()) throw new NoSuchElementException("Stack is empty");
        return data.remove(data.size() - 1);
    }

    public int peek() {
        if (data.isEmpty()) throw new NoSuchElementException("Stack is empty");
        return data.get(data.size() - 1);
    }

    public boolean isEmpty() { return data.isEmpty(); }
    public int size() { return data.size(); }
    public java.util.List<Integer> values() { return java.util.List.copyOf(data); }
}
