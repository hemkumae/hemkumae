package alumninetwork;

import java.util.*;

public class AlumniNetwork {
    private final Map<String, Person> people = new LinkedHashMap<>();

    public void addPerson(Person person) {
        people.put(person.getId(), person);
    }

    public Person getPerson(String id) {
        Person person = people.get(id);
        if (person == null) throw new IllegalArgumentException("Person not found: " + id);
        return person;
    }

    public void sendRequest(String senderId, String receiverId) {
        Person sender = getPerson(senderId);
        Person receiver = getPerson(receiverId);

        if (senderId.equals(receiverId)) throw new IllegalArgumentException("A person cannot request themselves");
        if (sender.isConnectedTo(receiverId)) throw new IllegalStateException("Already connected");
        if (sender.getOutgoingRequests().containsKey(receiverId)) throw new IllegalStateException("Request already exists");

        ConnectionRequest request = new ConnectionRequest(sender, receiver);
        sender.addOutgoingRequest(request);
        receiver.addIncomingRequest(request);
    }

    public void acceptRequest(String receiverId, String senderId) {
        Person receiver = getPerson(receiverId);
        Person sender = getPerson(senderId);

        ConnectionRequest request = receiver.getIncomingRequests().get(senderId);

        if (request == null) throw new IllegalStateException("No pending request");

        receiver.removeIncomingRequest(senderId);
        sender.removeOutgoingRequest(receiverId);

        sender.addConnection(receiver);
        receiver.addConnection(sender);
    }

    public void rejectRequest(String receiverId, String senderId) {
        Person receiver = getPerson(receiverId);
        Person sender = getPerson(senderId);

        if (!receiver.getIncomingRequests().containsKey(senderId))
            throw new IllegalStateException("No pending request");

        receiver.removeIncomingRequest(senderId);
        sender.removeOutgoingRequest(receiverId);
    }

    public List<Recommendation> getRecommendations(String personId) {
        Person person = getPerson(personId);
        Map<String, Integer> mutualCounts = new HashMap<>();
        Set<String> directConnections = new HashSet<>();

        for (Person connection : person.getConnections()) {
            directConnections.add(connection.getId());
        }

        for (Person connection : person.getConnections()) {
            for (Person candidate : connection.getConnections()) {
                if (candidate.getId().equals(personId)) continue;
                if (directConnections.contains(candidate.getId())) continue;
                if (hasPendingRequest(person, candidate)) continue;
                mutualCounts.merge(candidate.getId(), 1, Integer::sum);
            }
        }

        PriorityQueue<Recommendation> ranking = new PriorityQueue<>(
                Comparator.comparingInt(Recommendation::mutualConnections).reversed()
                        .thenComparing(r -> r.person().getName())
        );

        for (Map.Entry<String, Integer> entry : mutualCounts.entrySet()) {
            ranking.offer(new Recommendation(getPerson(entry.getKey()), entry.getValue()));
        }

        List<Recommendation> result = new ArrayList<>();
        while (!ranking.isEmpty()) result.add(ranking.poll());
        return result;
    }

    private boolean hasPendingRequest(Person person, Person candidate) {
        return person.getOutgoingRequests().containsKey(candidate.getId())
                || person.getIncomingRequests().containsKey(candidate.getId());
    }

    public Map<String, Person> getPeople() {
        return Collections.unmodifiableMap(people);
    }
}
