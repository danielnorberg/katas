package quicksort;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.lang.Integer.min;

@State(Scope.Benchmark)
public class ThreeWayPartitionQuickSort {

  private static final int N = 200000;

  private static final int P = ThreadLocalRandom.current().nextInt(N);
  private static final int[] V = IntStream.range(0, N).map(
      i -> (ThreadLocalRandom.current().nextInt() & 0b111) == 0b111 ?
           ThreadLocalRandom.current().nextInt(N) :
           P)
      .toArray();

  public static void main(final String... args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(".*" + ThreeWayPartitionQuickSort.class.getSimpleName() + ".*")
        .forks(1)
        .warmupIterations(5)
        .measurementIterations(5)
        .build();
    new Runner(opt).run();
  }

  @Benchmark
  public int[] benchmarkQuickSort() {
    final int[] v = V.clone();
    quickSort(v);
    return v;
  }

  @Benchmark
  public int[] benchmarkArraysSort() {
    final int[] v = V.clone();
    Arrays.sort(v);
    return v;
  }

  public static void quickSort(int[] v) {
    quickSort(v, 0, v.length);
  }

  private static void quickSort(final int[] v, final int a, final int b) {
    if (b - a <= 1) {
      return;
    }

    final int pi = rand(a, b);
    final int p = v[pi];

    swap(v, a, pi);

    int i = a + 1;
    int k = a + 1;
    int j = b - 1;
    int l = b - 1;

    //    a    i       k        l       j     b
    // ------------------------------------------
    //   |pppp|   <p   |        |   >p   |pppp|

    while (k <= l) {

      // Seek a value greater than p and move p's to left
      while (k <= l && v[k] <= p) {
        if (v[k] == p) {
          if (i != k) {
            swap(v, i, k);
          }
          i++;
        }
        k++;
      }

      // Seek a value smaller than p and move p's to right
      while (k <= l && v[l] >= p) {
        if (v[l] == p) {
          if (j != l) {
            swap(v, j, l);
          }
          j--;
        }
        l--;
      }

      if (k >= l) {
        break;
      }

      swap(v, k, l);

      k++;
      l--;
    }

    assert k >= l;
    final int q = k;
    final int r = q - (i - a);
    final int s = q + (b - j - 1);

    swap(v, a, q, min(i - a, q - i));
    swap(v, q, b, min((j + 1) - k, b - (j + 1)));

    //    a            r        s             b
    // ------------------------------------------
    //    |     <p     |pppppppp|      >p     |

    quickSort(v, a, r);
    quickSort(v, s, b);
  }

  private static void swap(final int[] v, final int a, final int b, final int n) {
    final int k = a + n;
    for (int i = a, j = b - n; i < k; i++, j++) {
      swap(v, i, j);
    }
  }

  private static int rand(final int origin, final int bound) {
    return ThreadLocalRandom.current().nextInt(origin, bound);
  }

  private static void swap(final int[] v, final int a, final int p) {
    final int tmp = v[a];
    v[a] = v[p];
    v[p] = tmp;
  }
}
