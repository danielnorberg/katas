package ctci.ch4;

import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import java.util.Queue;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q4_2_Route {


  public static void main(final String... args) {

    // a -> b -> c
    //      ^
    //      |
    //      d

    final Node a = new Node();
    final Node b = new Node();
    final Node c = new Node();
    final Node d = new Node();

    a.addNeighbor(b);
    b.addNeighbor(c);
    d.addNeighbor(b);

    assertThat(a.hasRouteTo(c), is(true));
    assertThat(d.hasRouteTo(c), is(true));
    assertThat(d.hasRouteTo(a), is(false));
    assertThat(c.hasRouteTo(a), is(false));
  }

  private static class Node {

    private Set<Node> neighbors = Sets.newHashSet();

    public void addNeighbor(final Node n) {
      neighbors.add(n);
    }

    public boolean hasRouteTo(final Node target) {
      final Set<Node> visited = Sets.newHashSet();
      final Queue<Node> queue = Queues.newArrayDeque();
      queue.add(this);
      while (!queue.isEmpty()) {
        final Node node = queue.poll();
        for (final Node neighbor : node.neighbors) {
          if (neighbor == target) {
            return true;
          }
          if (visited.contains(neighbor)) {
            continue;
          }
          visited.add(neighbor);
          queue.add(neighbor);
        }
      }
      return false;
    }
  }
}
