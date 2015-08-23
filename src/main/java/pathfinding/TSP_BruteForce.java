package pathfinding;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class TSP_BruteForce {

  public static void main(String[] args) {

    // Brute force, takes a while to complete...
    final int n = 12;
    final Node[] nodes = IntStream.range(0, n)
        .mapToObj(i -> new Node(
            String.valueOf(i), ThreadLocalRandom.current().nextInt(n), ThreadLocalRandom.current().nextInt(n)))
        .toArray(Node[]::new);

    final Route route = bruteForce(nodes);
    System.out.println(route);
  }

  private static Route bruteForce(final Node[] nodes) {
    final boolean visited[] = new boolean[nodes.length];
    final Node[] tmp = new Node[nodes.length];
    final Node[] route = new Node[nodes.length];
    visited[0] = true;
    tmp[0] = nodes[0];
    final float length2 = bruteForce(nodes, nodes[0], 1, visited, 0, Float.MAX_VALUE, tmp, route);
    final float length = (float) Math.sqrt(length2);
    return new Route(asList(route), length);
  }

  private static float bruteForce(final Node[] nodes, final Node node, final int i, final boolean[] visited,
                                  final float length, final float bestLength,
                                  final Node[] route, final Node[] bestRoute) {
    if (i == nodes.length) {
      if (length < bestLength) {
        System.arraycopy(route, 0, bestRoute, 0, route.length);
        return length;
      } else {
        return bestLength;
      }
    }
    float localBestLength = bestLength;
    for (int j = 0; j < nodes.length; j++) {
      if (visited[j]) {
        continue;
      }
      visited[j] = true;
      final Node next = nodes[j];
      final float d2 = Node.distance2(node, next);
      route[i] = next;
      final float best = bruteForce(nodes, next, i + 1, visited, length + d2, localBestLength, route, bestRoute);
      if (best < localBestLength) {
        localBestLength = best;
      }
      visited[j] = false;
    }
    return localBestLength;
  }

  private static class Node {

    private final String name;
    private final float x;
    private final float y;

    public Node(final String name, final int x, final int y) {
      this.name = name;
      this.x = x;
      this.y = y;
    }

    @Override
    public String toString() {
      return "Node{" +
             "name='" + name + '\'' +
             ", x=" + x +
             ", y=" + y +
             '}';
    }

    /**
     * |a-b|^2
     */
    private static float distance2(final Node a, final Node b) {
      final float dx = b.x - a.x;
      final float dy = b.y - a.y;
      return dx * dx + dy * dy;
    }
  }

  private static class Route {

    private final List<Node> route;
    private final float length;

    public Route(final List<Node> route, final float length) {
      this.route = route;
      this.length = length;
    }

    @Override
    public String toString() {
      return "Route{" +
             "route=" + route.stream().map(n -> n.name + "(" + n.x + "," + n.y + ")").collect(Collectors.joining(", ")) +
             ", length=" + length +
             '}';
    }
  }
}
