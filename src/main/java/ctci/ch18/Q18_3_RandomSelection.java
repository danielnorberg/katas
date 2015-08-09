package ctci.ch18;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Q18_3_RandomSelection {

  public static int[] recursiveRandomSelect(final int[] values, final int n, final int m) {
    if (n < m) {
      throw new IllegalArgumentException();
    }
    if (n == m) {
      return Arrays.copyOf(values, n);
    }
    final int[] selected = recursiveRandomSelect(values, n - 1, m);
    final int k = ThreadLocalRandom.current().nextInt(n);
    if (k < m) {
      selected[k] = values[n - 1];
    }
    return selected;
  }

  public static void main(String[] args) {
    final int n = 10;
    final int m = 5;
    for (int i = 0; i < 20; i++) {
      final int[] input = IntStream.range(0, n).toArray();
      final int[] selected = recursiveRandomSelect(input, n, m);
      System.out.println(Arrays.toString(selected));
    }
  }
}
