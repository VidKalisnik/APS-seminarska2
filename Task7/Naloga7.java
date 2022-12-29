import java.io.*;
import java.util.*;

public class Naloga7 {

  public static void main(String[] args) throws IOException {
    // Read input data from file
    String inputFilePath = args[0];
    BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
    int n = Integer.parseInt(reader.readLine());
    List<List<Integer>> routes = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      String[] routeStr = reader.readLine().split(",");
      List<Integer> route = new ArrayList<>();
      for (String stopStr : routeStr) {
        route.add(Integer.parseInt(stopStr));
      }
      routes.add(route);
    }
    String[] aAndB = reader.readLine().split(",");
    int a = Integer.parseInt(aAndB[0]);
    int b = Integer.parseInt(aAndB[1]);
    reader.close();

    // Build graph representation of public transport system
    Map<Integer, Map<Integer, List<Integer>>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) {
      List<Integer> route = routes.get(i);
      for (int j = 0; j < route.size() - 1; j++) {
        int node = route.get(j);
        int nextNode = route.get(j + 1);
        if (!graph.containsKey(node)) {
          graph.put(node, new HashMap<>());
        }
        if (!graph.get(node).containsKey(nextNode)) {
          graph.get(node).put(nextNode, new ArrayList<>());
        }
        graph.get(node).get(nextNode).add(i);
      }
    }

    // Perform breadth-first search to find minimum number of transfers
    Queue<int[]> queue = new LinkedList<>();
    queue.add(new int[] { a, -1, 0 });
    Set<Integer> visited = new HashSet<>();
    int minTransfers = -1;
    while (!queue.isEmpty()) {
      int[] nodeInfo = queue.poll();
      int node = nodeInfo[0];
      int prevLine = nodeInfo[1];
      int numTransfers = nodeInfo[2];
      if (node == b) {
        minTransfers = numTransfers;
        break;
      }
      if (visited.contains(node)) {
        continue;
      }
      visited.add(node);
      Map<Integer, List<Integer>> neighbors = graph.get(node);
      if (neighbors == null) {
        continue;
      }
      for (int nextNode : neighbors.keySet()) {
        for (int line : neighbors.get(nextNode)) {
          int updatedTransfers = numTransfers;
          if (line != prevLine) {
            updatedTransfers++;
          }
          queue.add(new int[] { nextNode, line, updatedTransfers });
        }
      }
    }

    // Perform breadth-first search to find minimum number of stops
    queue.add(new int[] { a, 0 });
    visited.clear();
    int minStops = -1;
    while (!queue.isEmpty()) {
      int[] nodeInfo = queue.poll();
      int node = nodeInfo[0];
      int numStops = nodeInfo[1];
      if (node == b) {
        minStops = numStops;
        break;
      }
      if (visited.contains(node)) {
        continue;
      }
      visited.add(node);
      Map<Integer, List<Integer>> neighbors = graph.get(node);
      if (neighbors == null) {
        continue;
      }
      for (int nextNode : neighbors.keySet()) {
        queue.add(new int[] { nextNode, numStops + 1 });
      }
    }

    // Determine if there is a route that is simultaneously optimal according to both criteria
    int optimalRoute = minTransfers == minStops ? 1 : 0;

    // Write results to output file
    String outputFilePath = args[1];
    PrintWriter writer = new PrintWriter(outputFilePath);
    writer.println(minTransfers);
    writer.println(minStops);
    writer.println(optimalRoute);
    writer.close();
  }
}
