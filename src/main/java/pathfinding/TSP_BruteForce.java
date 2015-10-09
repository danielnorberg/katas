package pathfinding;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class TSP_BruteForce {

  public static void main(String[] args) {

    // Brute force, takes a while to complete...
    final int n = 12;
    final ThreadLocalRandom r = ThreadLocalRandom.current();
    final Node[] nodes = IntStream.range(0, n)
        .mapToObj(i -> new Node(
            String.valueOf(i), r.nextInt(800), r.nextInt(800)))
        .toArray(Node[]::new);

    final Route route = bruteForceTSP(nodes);

    RouteDrawer.draw(route);
  }

  static Route bruteForceTSP(final Node[] nodes) {
    final boolean visited[] = new boolean[nodes.length];
    final Node[] tmp = new Node[nodes.length];
    final Node[] route = new Node[nodes.length];
    visited[0] = true;
    tmp[0] = nodes[0];
    final float length2 = bruteForceTSP(nodes, nodes[0], 1, visited, 0, Float.MAX_VALUE, tmp, route);
    final float length = (float) Math.sqrt(length2);
    return new Route(asList(route), length);
  }

  private static float bruteForceTSP(final Node[] nodes, final Node node, final int i, final boolean[] visited,
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
      final float best = bruteForceTSP(nodes, next, i + 1, visited, length + d2, localBestLength, route, bestRoute);
      if (best < localBestLength) {
        localBestLength = best;
      }
      visited[j] = false;
    }
    return localBestLength;
  }
}
