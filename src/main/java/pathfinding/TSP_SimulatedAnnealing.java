package pathfinding;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static pathfinding.Node.distance2;

/**
 *
 */
public class TSP_SimulatedAnnealing {

  public static void main(String[] args) {

    final int n = 50;
    final ThreadLocalRandom r = ThreadLocalRandom.current();
    final List<Node> nodes = IntStream.range(0, n)
        .mapToObj(i -> new Node(
            String.valueOf(i), r.nextInt(800), r.nextInt(800)))
        .collect(Collectors.toList());

    final Route route = simulatedAnnealingTSP(nodes);

    RouteDrawer.draw(route);
  }

  static Route simulatedAnnealingTSP(final List<Node> nodes) {
    final Node[] route = randomRoute(nodes);
    float minLength2 = length2(route);

    final ThreadLocalRandom r = ThreadLocalRandom.current();

    // TODO: tweak knobs to get better results

    int i = 1;

    while (true) {
      // Temperature
      final float t = (float) Math.pow(Math.E, -i / 500000.0f);
      final int d = (int) (route.length * t);
      if (d == 0) {
        break;
      }
      i++;

      // Random modification
      final int a = r.nextInt(route.length);
      final int b = r.nextInt(route.length);
      swap(route, a, b);

      // Calculate "fitness"
      final float length2 = length2(route);

      // Survival of the "fittest"
      boolean keep = false;
      if (length2 < minLength2) {
        // Always keep better moves
        keep = true;
      } else if (pow2(t) > r.nextFloat()) {
        // With a probability depending on the current temperature, keep worse moves as well.
        keep = true;
      }

      if (keep) {
        // Keep modification
        minLength2 = length2;
      } else {
        // Revert
        swap(route, b, a);
      }
    }

    System.out.println("i = " + i);

    final float bestLength = (float) Math.sqrt(minLength2);
    return new Route(asList(route), bestLength);
  }

  private static float pow2(final float v) {
    return v * v;
  }

  private static float length2(final Node[] route) {
    float length2 = 0;
    Node prev = route[0];
    for (int i = 1; i < route.length; i++) {
      final Node node = route[i];
      length2 += distance2(prev, node);
      prev = node;
    }
    return length2;
  }

  private static void swap(final Node[] route, final int a, final int b) {
    final Node tmp = route[a];
    route[a] = route[b];
    route[b] = tmp;
  }

  private static Node[] randomRoute(final List<Node> nodes) {
    final Node[] route = nodes.toArray(new Node[nodes.size()]);
    Collections.shuffle(asList(route));
    return route;
  }
}
