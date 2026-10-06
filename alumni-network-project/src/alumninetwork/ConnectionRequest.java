package alumninetwork;

public class ConnectionRequest {
    private final Person sender;
    private final Person receiver;

    public ConnectionRequest(Person sender, Person receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public Person getSender() {
        return sender;
    }

    public Person getReceiver() {
        return receiver;
    }
}
