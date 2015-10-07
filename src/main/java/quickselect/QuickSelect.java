package quickselect;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@State(Scope.Benchmark)
public class QuickSelect {

  private static final int N = 10_000;

  private static int[] INPUT;

  private int[] input;
  private int k;

  @Setup(Level.Iteration)
  public void iterationSetup() {
    INPUT = ThreadLocalRandom.current().ints(N).toArray();
  }

  @Setup(Level.Invocation)
  public void invocationSetup() {
    input = INPUT.clone();
    k = ThreadLocalRandom.current().nextInt(N);
  }

  public static void main(final String... args) throws RunnerException {
    // Sanity checks
    assertThat(select(3, 9, 7, 5, 0, 6, 3, 8, 4, 2, 1), is(3));
    fuzz(1000);

    // Benchmark
    Options opt = new OptionsBuilder()
        .include(".*" + QuickSelect.class.getSimpleName() + ".*")
        .forks(1)
        .warmupIterations(3)
        .measurementIterations(5)
        .build();
    new Runner(opt).run();
  }

  @Benchmark
  public int benchQuickSelect() {
    return select(k, input);
  }

  @Benchmark
  public int benchArraysSortAndIndex() {
    Arrays.sort(input);
    return input[k];
  }


  private static void fuzz(final int n) {
    final int N = 10000;
    for (int i = 0; i < n; i++) {
      final int k = ThreadLocalRandom.current().nextInt(0, N);
      final int[] x = ThreadLocalRandom.current().ints(N, 0, N).toArray();
      final int[] sorted = x.clone();
      Arrays.sort(sorted);
      final int expected = sorted[k];
      final int selected = select(k, x);
      assertThat(selected, is(expected));
    }
  }

  private static int select(int k, final int... x) {
    int l = 0;
    int u = x.length;
    while (true) {
      final int pi = pivot(x, l, u);
      final int p = x[pi];
      swap(x, u - 1, pi);
      int j = l - 1;
      for (int i = l; i < u; i++) {
        if (x[i] <= p) {
          j++;
          swap(x, i, j);
        }
      }
      if (j == k) {
        return p;
      }
      if (j < k) {
        l = j + 1;
      } else {
        u = j;
      }
    }
  }

  private static int pivot(final int[] x, final int l, final int u) {
    return l + (u - l >> 1);
  }

  private static void swap(final int[] x, final int a, final int b) {
    final int t = x[a];
    x[a] = x[b];
    x[b] = t;
  }
}
