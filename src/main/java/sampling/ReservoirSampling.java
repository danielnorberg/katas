package sampling;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ReservoirSampling {

  public static void main(final String... args) {
    final int n = 20;
    final int k = 5;
    final int[] hist = new int[n];
    final int[] source = IntStream.range(0, n).toArray();
    for (int i = 0; i < 10_000; i++) {
      final int[] sample = sample(source, k);
      for (final int v : sample) {
        hist[v]++;
      }
    }
    System.out.println(Arrays.toString(hist));
  }

  private static int[] sample(final int[] s, final int k) {
    if (s.length < k) {
      throw new IllegalArgumentException();
    }
    final int[] r = IntStream.of(s).limit(k).toArray();
    final ThreadLocalRandom rand = ThreadLocalRandom.current();
    for (int i = k; i < s.length; i++) {
      final int j = rand.nextInt(0, i + 1);
      if (j < k) {
        r[j] = s[i];
      }
    }
    return r;
  }
}
