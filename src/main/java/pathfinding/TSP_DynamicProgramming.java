package pathfinding;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pathfinding.Node.distance2;

/**
 * Solving TSP by dynamic programming. Slightly less infeasible than brute force, but not a lot.
 */
public class TSP_DynamicProgramming {

  public static void main(String[] args) {

    final int n = 16;
    final ThreadLocalRandom r = ThreadLocalRandom.current();
    final List<Node> nodes = IntStream.range(0, n)
        .mapToObj(i -> new Node(
            String.valueOf(i), r.nextInt(800), r.nextInt(800)))
        .collect(Collectors.toList());

    final Route route = dynamicProgrammingTSP(nodes);

    RouteDrawer.draw(route);
  }

  private static final Map<List<Node>, Route> routes = new HashMap<>();

  private static Route dynamicProgrammingTSP(final List<Node> nodes) {
    // Already calculated?
    final Route cached = routes.get(nodes);
    if (cached != null) {
      return cached;
    }

    // Base cases
    if (nodes.size() <= 1) {
      final List<Node> n = ImmutableList.copyOf(nodes);
      final Route route = new Route(n, 0);
      routes.put(nodes, route);
      return route;
    }
    if (nodes.size() <= 2) {
      final List<Node> n = ImmutableList.copyOf(nodes);
      final Route route = new Route(n, distance2(n.get(0), n.get(1)));
      routes.put(nodes, route);
      return route;
    }

    // Pluck a node from the list and recurse
    float bestCost = Float.MAX_VALUE;
    Route bestRoute = null;
    for (final Node node : nodes) {
      final List<Node> subset = nodes.stream()
          .filter(n -> n != node)
          .collect(Collectors.toList());
      final Route subRoute = dynamicProgrammingTSP(subset);
      for (int i = 0; i < subRoute.route.size(); i++) {
        final Route route = subRoute.with(node, i);
        if (route.length < bestCost) {
          bestCost = route.length;
          bestRoute = route;
        }
      }
    }

    // Profit!
    assert bestRoute != null;
    assert bestCost == bestRoute.length;
    routes.put(nodes, bestRoute);
    return bestRoute;
  }
}
