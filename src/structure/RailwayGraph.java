package structure;

import model.Station;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** Represents railway stations and their two-way route connections. */
public class RailwayGraph {
    private final Map<String, Station> stations = new LinkedHashMap<>();
    private final Map<String, ArrayList<String>> adjacencyList = new LinkedHashMap<>();

    public boolean addStation(Station station) {
        if (station == null || station.getStationId().isBlank()) {
            return false;
        }

        String stationId = station.getStationId().trim().toUpperCase();
        if (stations.containsKey(stationId)) {
            return false;
        }

        stations.put(stationId, station);
        adjacencyList.put(stationId, new ArrayList<>());
        return true;
    }

    public boolean addRoute(String firstStationId, String secondStationId) {
        String firstId = normaliseId(firstStationId);
        String secondId = normaliseId(secondStationId);

        if (!stations.containsKey(firstId) || !stations.containsKey(secondId)
                || firstId.equals(secondId)) {
            return false;
        }

        boolean routeAdded = false;
        if (!adjacencyList.get(firstId).contains(secondId)) {
            adjacencyList.get(firstId).add(secondId);
            routeAdded = true;
        }
        if (!adjacencyList.get(secondId).contains(firstId)) {
            adjacencyList.get(secondId).add(firstId);
        }
        return routeAdded;
    }

    public void displayGraph() {
        System.out.println("\n===== Railway Network =====");
        for (String stationId : adjacencyList.keySet()) {
            ArrayList<String> neighbours = adjacencyList.get(stationId);
            ArrayList<String> neighbourNames = new ArrayList<>();
            for (String neighbourId : neighbours) {
                neighbourNames.add(stations.get(neighbourId).getStationName());
            }
            System.out.println(stations.get(stationId).getStationName() + " -> "
                    + String.join(", ", neighbourNames));
        }
    }

    public ArrayList<String> bfs(String startStationId) {
        String startId = normaliseId(startStationId);
        ArrayList<String> traversal = new ArrayList<>();
        if (!stations.containsKey(startId)) {
            return traversal;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(startId);
        visited.add(startId);

        while (!queue.isEmpty()) {
            String currentId = queue.poll();
            traversal.add(currentId);
            for (String neighbourId : adjacencyList.get(currentId)) {
                if (visited.add(neighbourId)) {
                    queue.offer(neighbourId);
                }
            }
        }
        return traversal;
    }

    public ArrayList<String> dfs(String startStationId) {
        ArrayList<String> traversal = new ArrayList<>();
        String startId = normaliseId(startStationId);
        if (stations.containsKey(startId)) {
            dfsRecursive(startId, new HashSet<>(), traversal);
        }
        return traversal;
    }

    private void dfsRecursive(String currentId, Set<String> visited,
                              ArrayList<String> traversal) {
        visited.add(currentId);
        traversal.add(currentId);

        for (String neighbourId : adjacencyList.get(currentId)) {
            if (!visited.contains(neighbourId)) {
                dfsRecursive(neighbourId, visited, traversal);
            }
        }
    }

    private String normaliseId(String stationId) {
        return stationId == null ? "" : stationId.trim().toUpperCase();
    }
}
