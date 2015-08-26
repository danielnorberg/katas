package quicksort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertArrayEquals;

public class ProgrammingPearlQuickSort {

  public static void main(final String... args) {
    final int[] v = ThreadLocalRandom.current().ints(10000).toArray();
    final int[] expected = v.clone();
    Arrays.sort(expected);
    quickSort(v);
    assertArrayEquals(expected, v);
  }

  public static void quickSort(int[] v) {
    quickSort(v, 0, v.length);
  }

  private static void quickSort(final int[] v, final int a, final int b) {
    if (b - a <= 1) {
      return;
    }
    final int p = partition(v, a, b);
    quickSort(v, a, p);
    quickSort(v, p + 1, b);
  }

  private static int partition(final int[] v, final int a, final int b) {
    final int pi = ThreadLocalRandom.current().nextInt(a, b);
    swap(v, a, pi);
    final int pivot = v[a];
    int i = a;
    for (int j = a + 1; j < b; j++) {
      if (v[j] < pivot) {
        i++;
        swap(v, i, j);
      }
    }
    swap(v, a, i);
    return i;
  }

  private static void swap(final int[] v, final int i, final int j) {
    final int tmp = v[i];
    v[i] = v[j];
    v[j] = tmp;
  }
}
