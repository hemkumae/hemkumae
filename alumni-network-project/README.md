# Alumni Network Project

## Hackathon Problem

Build a Java-based alumni networking system where every person is represented as a node containing personal details. The network must support connection requests, accepted connections, and recommendations generated from people connected to a user's existing network.

A pending connection request is represented as a one-way relationship from the sender to the receiver. When the receiver accepts the request, the relationship becomes bidirectional, creating a connection in both directions.

The system must recommend people who are not already directly connected but are connected through the user's existing network.

## Core Logic

Person:

- Represents one alumni member.
- Stores id, name, graduation year, department, and location.
- Maintains outgoing requests.
- Maintains incoming requests.
- Maintains accepted connections.

Connection request:

- Sender -> Receiver.
- One-way relationship.
- Exists until accepted or rejected.

Accepted connection:

- Sender <-> Receiver.
- Both person nodes contain the other person.
- The relationship can be traversed in both directions.

Recommendation:

1. Start from the requested person's direct connections.
2. Visit the connections of those people.
3. Ignore the original person.
4. Ignore already connected people.
5. Ignore pending request relationships.
6. Count mutual connections.
7. Rank candidates by mutual-connection count.

## Data Structures

- HashMap for O(1) average person lookup.
- Doubly linked list for accepted relationships.
- HashSet for visited people and duplicate prevention.
- Queue for breadth-first network traversal.
- PriorityQueue for recommendation ranking.

## Example

If:

A <-> B
B <-> C
B <-> D
C <-> E

A can receive recommendations for C and D because they are connected through B.

E can become a recommendation through C when traversing further through the network.

## Project Structure

- Person.java
- ConnectionNode.java
- ConnectionRequest.java
- AlumniNetwork.java
- Recommendation.java
- Main.java

The Java implementation contains no source-code comments.
