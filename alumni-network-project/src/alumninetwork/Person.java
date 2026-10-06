package alumninetwork;

import java.util.*;

public class Person {
    private final String id;
    private final String name;
    private final int graduationYear;
    private final String department;
    private final String location;
    private final Map<String, ConnectionRequest> outgoingRequests = new HashMap<>();
    private final Map<String, ConnectionRequest> incomingRequests = new HashMap<>();
    private final Map<String, ConnectionNode> connections = new LinkedHashMap<>();
    private ConnectionNode head;
    private ConnectionNode tail;

    public Person(String id, String name, int graduationYear, String department, String location) {
        this.id = id;
        this.name = name;
        this.graduationYear = graduationYear;
        this.department = department;
        this.location = location;
    }

    public void addOutgoingRequest(ConnectionRequest request) {
        outgoingRequests.put(request.getReceiver().getId(), request);
    }

    public void addIncomingRequest(ConnectionRequest request) {
        incomingRequests.put(request.getSender().getId(), request);
    }

    public void removeOutgoingRequest(String personId) {
        outgoingRequests.remove(personId);
    }

    public void removeIncomingRequest(String personId) {
        incomingRequests.remove(personId);
    }

    public void addConnection(Person person) {
        if (connections.containsKey(person.id)) return;

        ConnectionNode node = new ConnectionNode(person);

        if (head == null) {
            head = tail = node;
        } else {
            tail.setNext(node);
            node.setPrevious(tail);
            tail = node;
        }

        connections.put(person.id, node);
    }

    public boolean isConnectedTo(String personId) {
        return connections.containsKey(personId);
    }

    public List<Person> getConnections() {
        List<Person> result = new ArrayList<>();
        ConnectionNode current = head;

        while (current != null) {
            result.add(current.getPerson());
            current = current.getNext();
        }

        return result;
    }

    public List<Person> getReverseConnections() {
        List<Person> result = new ArrayList<>();
        ConnectionNode current = tail;

        while (current != null) {
            result.add(current.getPerson());
            current = current.getPrevious();
        }

        return result;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getGraduationYear() { return graduationYear; }
    public String getDepartment() { return department; }
    public String getLocation() { return location; }
    public Map<String, ConnectionRequest> getOutgoingRequests() { return Collections.unmodifiableMap(outgoingRequests); }
    public Map<String, ConnectionRequest> getIncomingRequests() { return Collections.unmodifiableMap(incomingRequests); }
}
