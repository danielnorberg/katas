package permutation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;

public class Permutations {

  public static void main(final String... args) {
    {
      final int[] v = {0, 1, 2};
      final int[][] ops = {
          {1, 2}, // v' = 0, 2, 1
          {0, 1}  // v' = 2, 0, 1
      };
      final int N = 2;
      permute(v, ops, N);
      // v'(1) = 2, 0, 1
      // v'(2) = 1, 2, 0
      final int[] expected = {1, 2, 0};
      assertArrayEquals(expected, v);
    }

    {
      final int[] v = {0, 1, 2, 3, 4, 5, 6, 7};
      final int[][] ops = {{3, 5}, {7, 3}, {2, 4}, {6, 0}, {1, 5}, {7, 2}, {0, 5}, {0, 3}, {1, 7}};
      final long N = 3141592653589793238L;
      permute(v, ops, N);
    }

    {
      final int K = 100;
      final int[] v = IntStream.range(0, K).toArray();
      final int[][] ops = IntStream.range(0, K)
          .mapToObj(i -> new int[]{
              ThreadLocalRandom.current().nextInt(K),
              ThreadLocalRandom.current().nextInt(K)
          })
          .toArray(int[][]::new);
      final long N = 3141592653589793238L;
      permute(v, ops, N);
    }
  }

  /**
   * Permute an array of K integers by applying a sequence of M swaps N times.
   *
   * This implementation uses dynamic programming to allow for large values of M and N.
   *
   * Time complexity: O(M + K log N)
   *
   * @param v     An array of integers.
   * @param swaps An array of tuples with indexes into the array of integers to swap.
   * @param n     The number of times to apply {@code ops} to {@code v}.
   */
  private static void permute(final int[] v, final int[][] swaps, final long n) {
    final int[] tmp = new int[v.length];
    final int[] permutation = permutation(v.length, swaps, n, tmp);
    permute(v, permutation, tmp);
  }

  private static int[] permutation(final int k, final int[][] ops, final long n, final int[] tmp) {
    final Map<Long, int[]> permutations = new HashMap<>();
    permutations.put(1L, permutation(k, ops));
    permutation(n, permutations, tmp);
    return permutations.get(n);
  }

  private static int[] permutation(final int k, final int[][] ops) {
    final int[] permutation = new int[k];
    for (int i = 0; i < permutation.length; i++) {
      permutation[i] = i;
    }
    for (final int[] op : ops) {
      swap(permutation, op[0], op[1]);
    }
    return permutation;
  }

  private static void swap(final int[] v, final int a, final int b) {
    final int tmp = v[a];
    v[a] = v[b];
    v[b] = tmp;
  }

  private static void permutation(final long n, final Map<Long, int[]> permutations, final int[] tmp) {
    int[] permutation = permutations.get(n);
    if (permutation != null) {
      return;
    }
    final long a = n / 2;
    final long b = n - a;
    permutation(a, permutations, tmp);
    permutation(b, permutations, tmp);
    permutation = permutations.get(a).clone();
    permute(permutation, permutations.get(b), tmp);
    permutations.put(n, permutation);
  }

  private static void permute(final int[] v, final int[] permutation, final int[] tmp) {
    for (int i = 0; i < v.length; i++) {
      tmp[i] = v[permutation[i]];
    }
    System.arraycopy(tmp, 0, v, 0, v.length);
  }
}
