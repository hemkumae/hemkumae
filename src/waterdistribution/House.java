package waterdistribution;

public class House {
    private final String id;
    private final int people;
    private final int waterDemand;

    public House(String id, int people, int waterPerPerson) {
        if (people <= 0 || waterPerPerson <= 0) throw new IllegalArgumentException("People and water per person must be positive");
        this.id = id;
        this.people = people;
        this.waterDemand = people * waterPerPerson;
    }

    public String getId() { return id; }
    public int getPeople() { return people; }
    public int getWaterDemand() { return waterDemand; }
}
