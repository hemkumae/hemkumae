# Water Distribution System

## Hackathon Problem

A city has a fixed number of water resources and houses connected through a pipe network. Each house contains a different number of people, so its water requirement depends on its population.

The system must distribute available water to every reachable house while respecting available supply. Houses are graph nodes and pipes are graph edges.

During an emergency, one or more pipes may fail. When a pipe breaks, the system must detect the failed connection and find an alternate route to the affected house using Depth First Search (DFS). If no alternate route exists, the house must be reported as unreachable.

## Requirements

- Model houses as graph nodes.
- Model water resources as source nodes.
- Model pipes as graph edges.
- Calculate demand from the number of people in each house.
- Distribute water from available resources.
- Use DFS to find a route from a resource to a house.
- Ignore broken pipes during route discovery.
- Find alternate routes after pipe failure.
- Report unreachable houses.
- Report water shortage when available supply cannot satisfy demand.

## Example Network

TANK-1 -> H1 -> H2 -> H3 -> H4
             |
             -> H5 -> H4

If H2 -> H3 fails, DFS searches the remaining active network for an alternate route.

## Algorithm

1. Create graph nodes for resources and houses.
2. Add pipes as graph edges.
3. Calculate each house's demand from its population.
4. Start DFS from available water resources.
5. Follow only active pipes.
6. Allocate water to reachable houses.
7. When a pipe fails, mark it inactive.
8. Run DFS again to discover an alternate route.
9. Report unreachable houses and water shortages.

## Technologies

- Java
- Object-Oriented Programming
- Graph
- Adjacency List
- Depth First Search
- Collections Framework

## Project Structure

- House.java
- WaterResource.java
- Pipe.java
- WaterNetwork.java
- WaterDistributionService.java
- Main.java
