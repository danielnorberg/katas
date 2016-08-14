package countfriends;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static com.google.common.base.CharMatcher.WHITESPACE;

public class CountFriends {

  private static final Splitter WHITESPACE_SPLITTER = Splitter.on(WHITESPACE);

  public static void main(final String... args) {

    // Count friends: I.e., nodes connected by edges in both directions.

    //  a -> b
    //  4    57
    // 65    12
    // ...

    // n == number of edges
    // m == number of nodes
    // TIME: O(n)
    // SPACE: O(m + n)

    assert countFriends(ImmutableList.of("1 2", "1 3")) == 0L;
    assert countFriends(ImmutableList.of("1 2")) == 0L;
    assert countFriends(ImmutableList.of("1 1")) == 1L;
    assert countFriends(ImmutableList.of("1 1", "1 1")) == 1L;
    assert countFriends(ImmutableList.of("1 2", "1 3", "2 1")) == 1L;
    assert countFriends(ImmutableList.of("1 2", "1 3", "2 1", "3 1")) == 2L;

    final int N = 1_000_000;
    final int M = 10_000;

    final ThreadLocalRandom r = ThreadLocalRandom.current();

    final long n = countFriends(
        IntStream.range(0, N)
            .mapToObj(i -> Edge.of(r.nextInt(M), r.nextInt(M)))
            .iterator());

    System.out.println(n);

  }

  private static long countFriends(final Iterable<String> edgeLines) {
    final Iterator<Edge> edges = StreamSupport.stream(edgeLines.spliterator(), false)
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .map(WHITESPACE_SPLITTER::splitToList)
        .map(Edge::of)
        .iterator();
    return countFriends(edges);
  }

  private static long countFriends(final Iterator<Edge> edges) {
    long n = 0;
    final Map<Long, Set<Long>> nodes = new HashMap<>();
    while (edges.hasNext()) {
      final Edge edge = edges.next();
      final Set<Long> aEdges = nodes.computeIfAbsent(edge.a, k -> new HashSet<>());
      if (aEdges.contains(edge.b)) {
        continue;
      }
      aEdges.add(edge.b);
      final Set<Long> bEdges = nodes.get(edge.b);
      if (bEdges != null && bEdges.contains(edge.a)) {
        n++;
      }
    }
    return n;
  }

  private static class Edge {

    long a;
    long b;

    Edge(final long a, final long b) {
      this.a = a;
      this.b = b;
    }

    public static Edge of(final List<String> tuple) {
      if (tuple.size() != 2) {
        throw new IllegalArgumentException();
      }
      return new Edge(Long.parseUnsignedLong(tuple.get(0)), Long.parseUnsignedLong(tuple.get(1)));
    }

    public static Edge of(final int a, final int b) {
      return new Edge(a, b);

    }
  }
}
