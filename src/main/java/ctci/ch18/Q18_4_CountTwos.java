package ctci.ch18;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.MAX_VALUE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q18_4_CountTwos {

  public static void main(String[] args) throws RunnerException {
    assertThat(countTwosBruteForce(25), is(9));
    assertThat(countTwosByDigit(25), is(9));
    assertThat(countTwosByDigit(4711), is(2441));

    // Fuzz
    for (int i = 0; i < 100; i++) {
      final int x = ThreadLocalRandom.current().nextInt(100_000);
      final int expected = countTwosBruteForce(x);
      final int twos = countTwosByDigit(x);
      assertThat(twos, is(expected));
    }

    // Benchmark
    Options opt = new OptionsBuilder()
        .include(".*" + Q18_4_CountTwos.class.getSimpleName() + ".*")
        .forks(1)
        .warmupIterations(5)
        .measurementIterations(5)
        .build();
    new Runner(opt).run();
  }

  @Benchmark
  public int benchmarkCountTwosByDigit() {
    final int x = ThreadLocalRandom.current().nextInt(MAX_VALUE);
    return countTwosByDigit(x);
  }


  private static int countTwosBruteForce(final int n) {
    int count = 0;
    for (int i = 0; i <= n; i++) {
      int x = i;
      while (x > 0) {
        if (x % 10 == 2) {
          count++;
        }
        x /= 10;
      }
    }
    return count;
  }

  /**
   * Count twos per digit.
   *
   * O(log n)
   */
  private static int countTwosByDigit(final int n) {
    int twos = 0;
    int x = n;
    int z = 0;
    int i = 0;
    int pow10ip = 0;
    int pow10i = 1;
    while (x > 0) {
      final int a = x / 10;
      final int b = x - 10 * a;
      final int c = z + b * pow10i;

      twos += b * i * pow10ip;
      if (b == 2) {
        twos += 1 + z;
      } else if (b > 2) {
        twos += pow10i;
      }

      x = a;
      z = c;

      i++;

      pow10ip = pow10i;
      pow10i *= 10;
    }
    return twos;
  }
}
