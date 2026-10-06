package waterdistribution;

public class Pipe {
    private final String from;
    private final String to;
    private final int capacity;
    private boolean active = true;

    public Pipe(String from, String to, int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Pipe capacity must be positive");
        this.from = from;
        this.to = to;
        this.capacity = capacity;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public int getCapacity() { return capacity; }
    public boolean isActive() { return active; }
    public void breakPipe() { active = false; }
    public void restorePipe() { active = true; }
}
