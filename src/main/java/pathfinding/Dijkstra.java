package pathfinding;

import com.google.common.collect.ImmutableList;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Dijkstra {

  public static void main(String[] args) {

    // a - 1 - b - 2 - c
    // |       |
    // 1       2
    // |       |
    // d - 1 - e

    final Node a = new Node("a");
    final Node b = new Node("b");
    final Node c = new Node("c");
    final Node d = new Node("d");
    final Node e = new Node("e");

    a.connect(b, 1);
    a.connect(d, 1);
    b.connect(c, 2);
    b.connect(e, 2);
    d.connect(e, 1);

    final Node[] nodes = {a, b, c, d, e};

    // a -> e
    reset(nodes);
    assertThat(search(a, e), is(new Path(2, a, d, e)));

    // a -> c
    reset(nodes);
    assertThat(search(a, c), is(new Path(3, a, b, c)));

    // a -> b
    reset(nodes);
    assertThat(search(a, b), is(new Path(1, a, b)));
  }

  private static void reset(final Node... nodes) {
    Stream.of(nodes).forEach(Node::reset);
  }

  /**
   * O(|E| + |V| log |V|) - Due to use of priority queue. Original Dijkstra is O(|V|^2).
   */
  private static Path search(final Node origin, final Node destination) {
    final PriorityQueue<Node> queue = new PriorityQueue<>();
    queue.add(origin);

    while (!queue.isEmpty()) {
      final Node node = queue.poll();

      // Mark this node as visited
      node.visited = true;

      // Are we there yet?
      if (node == destination) {
        return path(destination);
      }

      // Update costs for all unvisited neighbors and add them to queue
      node.neighbors.forEach((neighbor, distance) -> {
        if (neighbor.visited) {
          return;
        }
        final int newCost = node.cost + distance;
        if (neighbor.prev == null || neighbor.cost > newCost) {
          neighbor.prev = node;
          neighbor.cost = newCost;
        }
        queue.add(neighbor);
      });
    }

    // No path found. Graph is partitioned.
    return null;
  }

  private static Path path(final Node destination) {
    final Deque<Node> path = new ArrayDeque<>();
    Node n = destination;
    while (n != null) {
      path.addFirst(n);
      n = n.prev;
    }
    return new Path(destination.cost, path);
  }

  private static class Cost {

    private final Node prev;
    private final int cost;

    public Cost(final Node prev, final int cost) {
      this.prev = prev;
      this.cost = cost;
    }
  }

  private static class Path {

    private final int cost;
    private final List<Node> path;

    public Path(final int cost, final Collection<Node> path) {
      this.cost = cost;
      this.path = ImmutableList.copyOf(path);
    }

    public Path(final int cost, final Node... path) {
      this.cost = cost;
      this.path = ImmutableList.copyOf(path);
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }
      final Path path1 = (Path) o;
      return cost == path1.cost &&
             Objects.equals(path, path1.path);
    }

    @Override
    public int hashCode() {
      return Objects.hash(cost, path);
    }

    @Override
    public String toString() {
      return "Path{" +
             "cost=" + cost +
             ", path=" + path +
             '}';
    }
  }

  private static class Node implements Comparable<Node> {

    private final String name;
    private final Map<Node, Integer> neighbors = new HashMap<>();

    // Traversal state
    private Node prev;
    private int cost;
    private boolean visited;

    public Node(final String name) {
      this.name = name;
    }

    public void connect(final Node node, final int cost) {
      neighbors.put(node, cost);
    }

    @Override
    public int compareTo(final Node o) {
      return Integer.compare(cost, o.cost);
    }

    @Override
    public String toString() {
      return "Node{" + name + ", " +
             "neighbors = (" + neighbors.entrySet().stream()
                 .map(e -> e.getValue() + ":" + e.getKey().name)
                 .collect(Collectors.joining(", ")) +
             ")}";
    }

    public void reset() {
      cost = 0;
      prev = null;
      visited = false;
    }
  }
}
