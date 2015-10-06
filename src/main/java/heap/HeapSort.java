package heap;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.primitives.Ints.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@State(Scope.Benchmark)
public class HeapSort {

  private int[] input;

  @Setup
  public void setup() {
    input = ThreadLocalRandom.current().ints(10_000).toArray();
  }

  public static void main(final String... args) throws RunnerException {
    // Sanity checks
    assertThat(asList(heapSort(0, 9, 1, 8, 2, 7, 3, 6, 4, 5)), contains(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    final int[] vs = ThreadLocalRandom.current().ints(10_000).toArray();
    final int[] vsSorted = vs.clone();
    Arrays.sort(vsSorted);
    assertThat(asList(heapSort(vs)), is(asList(vsSorted)));

    // Benchmark
    Options opt = new OptionsBuilder()
        .include(".*" + HeapSort.class.getSimpleName() + ".*")
        .forks(1)
        .warmupIterations(3)
        .measurementIterations(2)
        .build();
    new Runner(opt).run();
  }

  @Benchmark
  public int[] benchHeapSort() {
    return heapSort(input);
  }

  @Benchmark
  public int[] benchArraysSort() {
    Arrays.sort(input);
    return input;
  }

  /**
   * In-place heap sort.
   *
   * Step 1. Transform the (unsorted) input into a max-heap in-place, iterating from left to right.
   * Step 2. Transform the heap into a sorted list, from right to left, by extracting the greatest element and
   * prepending it to the list, in-place.
   */
  private static int[] heapSort(final int... vs) {
    heapify(vs);
    for (int i = 0; i < vs.length; i++) {
      final int n = vs.length - i;
      vs[n - 1] = extract(vs, n);
    }
    return vs;
  }

  private static int extract(final int[] heap, final int n) {
    final int value = heap[0];
    heap[0] = heap[n - 1];

    // n -> n * 2 + 1
    //      n * 2 + 2

    int i = 0;
    while (i < n) {
      final int cl = i * 2 + 1;
      if (cl >= n) {
        break;
      }
      final int cr = i * 2 + 2;
      final int c;
      if (cr >= n || heap[cl] > heap[cr]) {
        c = cl;
      } else {
        c = cr;
      }
      if (heap[c] < heap[i]) {
        break;
      }
      swap(heap, i, c);
      i = c;
      assert valid(heap, i + 1);
    }

    return value;
  }

  private static void heapify(final int[] vs) {
    for (int i = 0; i < vs.length; i++) {
      insert(vs, i);
      assert valid(vs, i + 1);
    }
  }

  private static boolean valid(final int[] vs, final int n) {
    for (int i = 1; i < n; i++) {
      final int p = (i - 1) / 2;
      if (vs[p] < vs[i]) {
        return false;
      }
    }
    return true;
  }

  private static void insert(final int[] heap, int i) {
    // n -> (n - 1) / 2
    while (i > 0) {
      final int parent = (i - 1) / 2;
      if (heap[parent] >= heap[i]) {
        break;
      }
      swap(heap, parent, i);
      i = parent;
    }
  }

  private static void swap(final int[] heap, final int a, final int b) {
    final int t = heap[a];
    heap[a] = heap[b];
    heap[b] = t;
  }
}
