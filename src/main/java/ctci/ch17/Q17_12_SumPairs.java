package ctci.ch17;

import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class Q17_12_SumPairs {

  public static void main(String[] args) {
    assertThat(sumPairsQuadratic(3, asList(1, 2)), contains(pair(1, 2)));
    assertThat(sumPairsHash(3, asList(1, 2)), contains(pair(1, 2)));
    assertThat(sumPairsHash(10, asList(1, 2, 3, 4, 5, 5, 6, 7, 8, 9)),
               is(ImmutableSet.of(pair(1, 9), pair(2, 8), pair(3, 7), pair(4, 6), pair(5, 5))));
    assertThat(sumPairsOneOffHash(10, asList(1, 2, 3, 4, 5, 5, 6, 7, 8, 9)),
               contains(pair(5, 5), pair(4, 6), pair(3, 7), pair(2, 8), pair(1, 9)));
  }

  private static Pair pair(final int a, final int b) {
    return new Pair(a, b);
  }

  /**
   * O(n^2)
   */
  private static List<Pair> sumPairsQuadratic(final int x, final List<Integer> values) {
    final List<Pair> pairs = new ArrayList<>();
    for (int i = 0; i < values.size(); i++) {
      final int a = values.get(i);
      for (int j = i + 1; j < values.size(); j++) {
        if (i == j) {
          continue;
        }
        final int b = values.get(j);
        if (a + b == x) {
          pairs.add(pair(a, b));
        }
      }
    }
    return pairs;
  }

  /**
   * O(n)
   */
  private static Set<Pair> sumPairsHash(final int x, final List<Integer> values) {
    final Map<Integer, Integer> index = index(values);
    return sumPairsHash(x, index);
  }

  private static Set<Pair> sumPairsHash(final int x, final Map<Integer, Integer> index) {
    final Set<Pair> pairs = new HashSet<>();
    for (final int a : index.keySet()) {
      final int b = x - a;
      final int count = index.getOrDefault(b, 0);
      if ((a == b && count > 1) || (a != b && count > 0)) {
        pairs.add(pair(a, b));
      }
    }
    return pairs;
  }

  private static List<Pair> sumPairsOneOffHash(final int x, final List<Integer> values) {
    final List<Pair> pairs = new ArrayList<>();
    final Map<Integer, Void> index = new HashMap<>();
    for (final Integer a : values) {
      final int b = x - a;
      if (index.containsKey(b)) {
        pairs.add(pair(a, b));
      }
      index.put(a, null);
    }
    return pairs;
  }


  private static Map<Integer, Integer> index(final List<Integer> values) {
    final Map<Integer, Integer> index = new HashMap<>();
    for (final int value : values) {
      index.compute(value, (v, count) -> count == null ? 1 : count + 1);
    }
    return index;
  }

  private static class Pair {

    private final int a;
    private final int b;

    public Pair(final int a, final int b) {
      this.a = Math.min(a, b);
      this.b = Math.max(a, b);
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }

      final Pair pair = (Pair) o;

      if (a != pair.a) { return false; }
      if (b != pair.b) { return false; }

      return true;
    }

    @Override
    public int hashCode() {
      int result = a;
      result = 31 * result + b;
      return result;
    }

    @Override
    public String toString() {
      return "(" + a + ',' + b + ')';
    }
  }
}
