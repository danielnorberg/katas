package quicksort;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static heap.HeapSort.heapSort;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class QuickSort {

  private static final int N = 10_000;
  private static final int DEFAULT_CUTOFF = 64;

  @State(Scope.Benchmark)
  public static class BenchmarkBase {

    private final Supplier<int[]> supplier;

    private int[] input;
    protected int[] x;

    public BenchmarkBase(final Supplier<int[]> supplier) {
      this.supplier = supplier;
    }

    @Setup(Level.Iteration)
    public void iterationSetup() {
      input = supplier.get();
      x = input.clone();
    }

    @Setup(Level.Invocation)
    public void invocationSetup() {
      System.arraycopy(input, 0, x, 0, input.length);
    }
  }

  public static class RandomInputBenchmark extends BenchmarkBase {

    public RandomInputBenchmark() {
      super(() -> ThreadLocalRandom.current().ints(N).toArray());
    }
  }

  @State(Scope.Benchmark)
  public static class CutoffBenchmark extends RandomInputBenchmark {

    @Param({"0", "8", "12", "16", "20", "24", "28", "32", "40", "48", "56", "64", "92", "128"})
    private int cutoff;

    @Benchmark
    public int[] quickInsertionSortBenchmark() {
      return quickInsertionSort(x, cutoff);
    }

    @Benchmark
    public int[] quickSortAndInsertionSortBenchmark() {
      return quickSortAndInsertionSort(x, cutoff);
    }
  }

  @State(Scope.Benchmark)
  public static class ArraysSortBenchmark extends RandomInputBenchmark {

    @Benchmark
    public int[] run() {
      Arrays.sort(x);
      return x;
    }
  }

  public static class AntiQuickSortInputBenchmark extends BenchmarkBase {

    public AntiQuickSortInputBenchmark() {
      super(() -> Util.ANTI_QSORT_1M);
    }

    @Benchmark
    public int[] quickInsertionSortBenchmark() {
      return quickInsertionSort(x);
    }

    @Benchmark
    public int[] quickSortAndInsertionSortBenchmark() {
      return quickSortAndInsertionSort(x);
    }

    @Benchmark
    public int[] quickInsertionSortWithNoCutoffBenchmark() {
      return quickInsertionSort(x, 0);
    }

    @Benchmark
    public int[] quickSortAndInsertionSortWithNoCutoffBenchmark() {
      return quickSortAndInsertionSort(x, 0);
    }

    @Benchmark
    public int[] introSortBenchmark() {
      return introSort(x);
    }

    @Benchmark
    public int[] introInsertionSortBenchmark() {
      return introInsertionSort(x);
    }

    @Benchmark
    public int[] arraysSortBenchmark() {
      Arrays.sort(x);
      return x;
    }
  }

  public static class IdenticalInputBenchmark extends BenchmarkBase {

    public IdenticalInputBenchmark() {
      super(() -> Util.IDENTICAL_17_1K);
    }

    @Benchmark
    public int[] arraysSortBenchmark() {
      Arrays.sort(x);
      return x;
    }

    @Benchmark
    public int[] quickInsertionSortBenchmark() {
      return quickInsertionSort(x);
    }

    @Benchmark
    public int[] introSortBenchmark() {
      return introSort(x);
    }

    @Benchmark
    public int[] introInsertionSortBenchmark() {
      return introInsertionSort(x);
    }
  }

  public static class MostlyIdenticalInputBenchmark extends BenchmarkBase {

    public MostlyIdenticalInputBenchmark() {
      super(() -> Util.MOSTLY_IDENTICAL_17_1K);
    }

    @Benchmark
    public int[] arraysSortBenchmark() {
      Arrays.sort(x);
      return x;
    }

    @Benchmark
    public int[] quickInsertionSortBenchmark() {
      return quickInsertionSort(x);
    }

    @Benchmark
    public int[] introSortBenchmark() {
      return introSort(x);
    }

    @Benchmark
    public int[] introInsertionSortBenchmark() {
      return introInsertionSort(x);
    }
  }

  public static void main(final String... args) throws RunnerException {
    // Sanity checks
    final int[] input = {9, 7, 5, 0, 6, 3, 8, 4, 2, 1};
    final int[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    assertThat(insertionSort(input.clone()), is(expected));
    fuzz(10, 1000, (x, cutoff) -> insertionSort(x));
    for (int i = 0; i < input.length + 1; i++) {
      assertThat(quickInsertionSort(input.clone(), i), is(expected));
      assertThat(quickSortAndInsertionSort(input.clone(), i), is(expected));
    }
    assertThat(introSort(input.clone()), is(expected));
    assertThat(introInsertionSort(input.clone()), is(expected));
    fuzz(10, 1000, QuickSort::quickInsertionSort);
    fuzz(10, 1000, QuickSort::quickSortAndInsertionSort);
    fuzz(10, 1000, (x, cutoff) -> introSort(x));
    fuzz(10, 1000, (x, cutoff) -> introInsertionSort(x));

    // Benchmark
    Options opt = new OptionsBuilder()
        .include(".*" + QuickSort.class.getSimpleName() + ".*")
        .forks(1)
        .warmupIterations(3)
        .measurementIterations(2)
        .build();
    new Runner(opt).run();
  }

  private static int[] introInsertionSort(final int[] x) {
    introInsertionSort(x, 0, x.length);
    return x;
  }

  private static void introInsertionSort(final int[] x, final int l, final int u) {
    final int maxdepth = log2(u - l) * 2;
    introInsertionSort0(x, l, u, maxdepth);
    insertionSort(x, l, u);
  }

  private static void introInsertionSort0(final int[] x, final int l, final int u, final int maxdepth) {
    if (l >= u) {
      return;
    }
    if (maxdepth == 0) {
      return;
    }
    final int pi = partition(x, l, u);
    introInsertionSort0(x, l, pi, maxdepth - 1);
    introInsertionSort0(x, pi, u, maxdepth - 1);
  }

  private static int[] introSort(final int[] x) {
    final int maxdepth = log2(x.length) * 2;
    introSort0(x, 0, x.length, maxdepth);
    return x;
  }

  private static void introSort0(final int[] x, final int l, final int u, final int maxdepth) {
    if (l >= u) {
      return;
    }
    if (maxdepth == 0) {
      heapSort(x, l, u);
      return;
    }
    final int pi = partition(x, l, u);
    introSort0(x, l, pi, maxdepth - 1);
    introSort0(x, pi, u, maxdepth - 1);
  }

  private static void fuzz(final int n, final int m, final BiFunction<int[], Integer, int[]> sort) {
    for (int i = 0; i < n; i++) {
      final int cutoff = ThreadLocalRandom.current().nextInt(128);
      final int[] x = ThreadLocalRandom.current().ints(m).toArray();
      final int[] expected = x.clone();
      Arrays.sort(expected);
      final int[] sorted = sort.apply(x, cutoff);
      assertThat(sorted, is(expected));
    }
  }

  public static int[] quickSortAndInsertionSort(final int[] x) {
    return quickSortAndInsertionSort(x, DEFAULT_CUTOFF);
  }

  public static int[] quickSortAndInsertionSort(final int[] x, final int cutoff) {
    quickSort(x, cutoff);
    insertionSort(x);
    return x;
  }

  public static int[] insertionSort(final int[] x) {
    insertionSort(x, 0, x.length);
    return x;
  }

  private static void insertionSort(final int[] x, final int l, final int u) {
    for (int i = l; i < u - 1; i++) {
      final int xi = x[i + 1];
      int j = i;
      for (; j >= l; j--) {
        if (x[j] <= xi) {
          break;
        }
        x[j + 1] = x[j];
      }
      x[j + 1] = xi;
    }
  }

  public static int[] quickInsertionSort(final int[] x) {
    quickInsertionSort(x, 0, x.length, DEFAULT_CUTOFF);
    return x;
  }

  public static int[] quickInsertionSort(final int[] x, final int cutoff) {
    quickInsertionSort(x, 0, x.length, cutoff);
    return x;
  }

  private static void quickInsertionSort(final int[] x, final int l, final int u, final int cutoff) {
    final int n = u - l;
    if (n == 0) {
      return;
    }
    if (n < cutoff) {
      insertionSort(x, l, u);
      return;
    }
    final int pi = partition(x, l, u);
    quickInsertionSort(x, l, pi, cutoff);
    quickInsertionSort(x, pi + 1, u, cutoff);
  }

  public static int[] quickSort(final int[] x) {
    return quickSort(x, 0);
  }

  public static int[] quickSort(final int[] x, final int cutoff) {
    quickSort(x, 0, x.length, cutoff);
    return x;
  }

  private static void quickSort(final int[] x, final int l, final int u, final int cutoff) {
    final int n = u - l;
    if (n == 0) {
      return;
    }
    if (n < cutoff) {
      return;
    }
    final int pi = partition(x, l, u);
    quickSort(x, l, pi, cutoff);
    quickSort(x, pi + 1, u, cutoff);
  }

  private static int partition(final int[] x, final int l, final int u) {
    final int pi = pivot(x, l, u);
    final int p = x[pi];
    swap(x, pi, u - 1);
    int j = l - 1;
    for (int i = l; i < u; i++) {
      if (x[i] <= p) {
        j++;
        swap(x, i, j);
      }
    }
    return j;
  }

  private static int pivot(final int[] x, final int l, final int u) {
    return l + (u - l >> 1);
  }

  private static void swap(final int[] x, final int a, final int b) {
    final int t = x[a];
    x[a] = x[b];
    x[b] = t;
  }

  private static int log2(final int x) {
    return 32 - Integer.numberOfLeadingZeros(x);
  }
}
