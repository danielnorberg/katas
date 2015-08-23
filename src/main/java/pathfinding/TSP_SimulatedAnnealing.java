package pathfinding;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class TSP_SimulatedAnnealing {

  public static void main(String[] args) {

    final int n = 100;
    final Node[] nodes = IntStream.range(0, n)
        .mapToObj(i -> new Node(
            String.valueOf(i), ThreadLocalRandom.current().nextInt(n), ThreadLocalRandom.current().nextInt(n)))
        .toArray(Node[]::new);

    final Node[] route = randomRoute(nodes);
    float minLength2 = length2(route);

    final int k = 100;
    int d = Integer.MAX_VALUE;
    int i = 1;

    while (d > 0) {
      final float t = (float) Math.pow(Math.E, -i / 5000.0f);
      d = (int) (route.length * t) - 1;
      i++;

      for (int j = 0; j < k; j++) {

        // Random modification
        final int a = ThreadLocalRandom.current().nextInt(route.length - d - 1);
        final int b = a + d;
        swap(route, a, b);

        // Calculate "fitness"
        final float length2 = length2(route);

        // Survival of the "fittest"
        if (length2 < minLength2) {
          // Keep modification
          minLength2 = length2;
        } else {
          // Revert
          swap(route, b, a);
        }
      }
    }

    final float bestLength = (float) Math.sqrt(minLength2);
    System.out.println(new Route(asList(route), bestLength));

    // TODO: print map
  }

  private static float length2(final Node[] route) {
    float length2 = 0;
    Node prev = route[0];
    for (int i = 1; i < route.length; i++) {
      final Node node = route[i];
      length2 += Node.distance2(prev, node);
      prev = node;
    }
    return length2;
  }

  private static void swap(final Node[] route, final int a, final int b) {
    final Node tmp = route[a];
    route[a] = route[b];
    route[b] = tmp;
  }

  private static Node[] randomRoute(final Node[] nodes) {
    final Node[] route = nodes.clone();
    Collections.shuffle(asList(route));
    return route;
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
             "route=" + route.stream().map(n -> n.name + "(" + n.x + "," + n.y + ")").collect(Collectors.joining(", "))
             +
             ", length=" + length +
             '}';
    }
  }
}
