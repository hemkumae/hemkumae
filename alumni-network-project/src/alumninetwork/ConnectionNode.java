package alumninetwork;

public class ConnectionNode {
    private final Person person;
    private ConnectionNode previous;
    private ConnectionNode next;

    public ConnectionNode(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public ConnectionNode getPrevious() {
        return previous;
    }

    public ConnectionNode getNext() {
        return next;
    }

    public void setPrevious(ConnectionNode previous) {
        this.previous = previous;
    }

    public void setNext(ConnectionNode next) {
        this.next = next;
    }
}
