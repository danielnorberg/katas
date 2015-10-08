package quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Collections.swap;

public class GenericQuickSort {

  static <T extends Comparable<T>> void quickSort(final List<T> values) {
    quickSort(values, 0, values.size());
  }

  static <T extends Comparable<T>> List<T> quickSorted(final List<T> values) {
    final List<T> sorted = new ArrayList<>(values);
    quickSort(sorted);
    return sorted;
  }

  private static <T extends Comparable<T>> void quickSort(final List<T> values, final int a, final int b) {
    if (b - a <= 1) {
      return;
    }
    final int p = partition(values, a, b);
    quickSort(values, a, p);
    quickSort(values, p, b);
  }

  private static <T extends Comparable<T>> int partition(final List<T> values, final int a, final int b) {
    final int n = b - a;
    final int p = a + ThreadLocalRandom.current().nextInt(n);
    final T pv = values.get(p);
    int si = a;
    swap(values, b - 1, p);
    for (int i = a; i < b - 1; i++) {
      final T v = values.get(i);
      if (v.compareTo(pv) < 0) {
        swap(values, i, si);
        si++;
      }
    }
    swap(values, si, b - 1);
    return si;
  }
}
