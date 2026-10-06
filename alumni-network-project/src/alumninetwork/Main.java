package alumninetwork;

public class Main {
    public static void main(String[] args) {
        AlumniNetwork network = new AlumniNetwork();

        network.addPerson(new Person("P1", "Arjun", 2020, "CSE", "Bangalore"));
        network.addPerson(new Person("P2", "Bhavana", 2021, "ECE", "Hyderabad"));
        network.addPerson(new Person("P3", "Charan", 2020, "CSE", "Chennai"));
        network.addPerson(new Person("P4", "Divya", 2022, "IT", "Bangalore"));
        network.addPerson(new Person("P5", "Eshan", 2021, "CSE", "Pune"));

        network.sendRequest("P1", "P2");
        network.acceptRequest("P2", "P1");

        network.sendRequest("P2", "P3");
        network.acceptRequest("P3", "P2");

        network.sendRequest("P2", "P4");
        network.acceptRequest("P4", "P2");

        network.sendRequest("P3", "P5");
        network.acceptRequest("P5", "P3");

        System.out.println("P1 FORWARD CONNECTIONS");
        for (Person person : network.getPerson("P1").getConnections()) {
            System.out.println(person.getName());
        }

        System.out.println("P1 REVERSE TRAVERSAL");
        for (Person person : network.getPerson("P1").getReverseConnections()) {
            System.out.println(person.getName());
        }

        System.out.println("P1 RECOMMENDATIONS");
        for (Recommendation recommendation : network.getRecommendations("P1")) {
            System.out.println(recommendation.person().getName() + " | Mutual connections: " + recommendation.mutualConnections());
        }
    }
}
