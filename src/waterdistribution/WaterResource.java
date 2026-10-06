package waterdistribution;

public class WaterResource {
    private final String id;
    private final int capacity;
    private int availableWater;

    public WaterResource(String id, int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        this.id = id;
        this.capacity = capacity;
        this.availableWater = capacity;
    }

    public String getId() { return id; }
    public int getCapacity() { return capacity; }
    public int getAvailableWater() { return availableWater; }

    public int supply(int amount) {
        int supplied = Math.min(amount, availableWater);
        availableWater -= supplied;
        return supplied;
    }
}
