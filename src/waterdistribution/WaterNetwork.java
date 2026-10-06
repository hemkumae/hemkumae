package waterdistribution;

import java.util.*;

public class WaterNetwork {
    private final Map<String, House> houses = new LinkedHashMap<>();
    private final Map<String, WaterResource> resources = new LinkedHashMap<>();
    private final Map<String, List<Pipe>> graph = new LinkedHashMap<>();
    private final Map<String, Map<String, Pipe>> pipes = new HashMap<>();

    public void addHouse(House house) {
        houses.put(house.getId(), house);
        graph.putIfAbsent(house.getId(), new ArrayList<>());
    }

    public void addResource(WaterResource resource) {
        resources.put(resource.getId(), resource);
        graph.putIfAbsent(resource.getId(), new ArrayList<>());
    }

    public void addPipe(Pipe pipe) {
        if (!graph.containsKey(pipe.getFrom()) || !graph.containsKey(pipe.getTo()))
            throw new IllegalArgumentException("Both pipe endpoints must exist");
        graph.get(pipe.getFrom()).add(pipe);
        Pipe reverse = new Pipe(pipe.getTo(), pipe.getFrom(), pipe.getCapacity()) {
            @Override public boolean isActive() { return pipe.isActive(); }
            @Override public void breakPipe() { pipe.breakPipe(); }
            @Override public void restorePipe() { pipe.restorePipe(); }
        };
        graph.get(pipe.getTo()).add(reverse);
        pipes.computeIfAbsent(pipe.getFrom(), k -> new HashMap<>()).put(pipe.getTo(), pipe);
    }

    public void breakPipe(String from, String to) {
        Pipe pipe = pipes.getOrDefault(from, Map.of()).get(to);
        if (pipe == null) throw new IllegalArgumentException("Pipe not found");
        pipe.breakPipe();
    }

    public void restorePipe(String from, String to) {
        Pipe pipe = pipes.getOrDefault(from, Map.of()).get(to);
        if (pipe == null) throw new IllegalArgumentException("Pipe not found");
        pipe.restorePipe();
    }

    public List<String> findRouteDFS(String source, String target) {
        List<String> path = new ArrayList<>();
        if (dfs(source, target, new HashSet<>(), path)) return path;
        return List.of();
    }

    private boolean dfs(String current, String target, Set<String> visited, List<String> path) {
        visited.add(current);
        path.add(current);
        if (current.equals(target)) return true;
        for (Pipe pipe : graph.getOrDefault(current, List.of())) {
            if (pipe.isActive() && !visited.contains(pipe.getTo()) && dfs(pipe.getTo(), target, visited, path)) return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    public boolean isReachableFromResource(String houseId) {
        for (String resourceId : resources.keySet())
            if (!findRouteDFS(resourceId, houseId).isEmpty()) return true;
        return false;
    }

    public Map<String, House> getHouses() { return Collections.unmodifiableMap(houses); }
    public Map<String, WaterResource> getResources() { return Collections.unmodifiableMap(resources); }
}
