package mergesort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.primitives.Ints.asList;

public class MergeSort {

  static <T extends Comparable<T>> List<T> mergeSort(final List<T> input) {
    if (input.size() <= 1) {
      return input;
    }
    final int pivot = input.size() / 2;
    final List<T> a = input.subList(0, pivot);
    final List<T> b = input.subList(pivot, input.size());
    return merge(mergeSort(a), mergeSort(b));
  }

  private static <T extends Comparable<T>> List<T> merge(final List<T> a, final List<T> b) {
    final int n = a.size() + b.size();
    final List<T> merged = new ArrayList<>(n);
    int ai = 0;
    int bi = 0;
    for (int i = 0; i < n; i++) {
      final T av = ai < a.size() ? a.get(ai) : null;
      final T bv = bi < b.size() ? b.get(bi) : null;
      if (bv == null || (av != null && av.compareTo(bv) <= 0)) {
        ai++;
        merged.add(av);
      } else {
        bi++;
        merged.add(bv);
      }
    }
    return merged;
  }

  public static void main(final String... args) {
    final int n = 17;
    final List<Integer> input = asList(ThreadLocalRandom.current().ints(n).toArray());
    final List<Integer> output = mergeSort(input);
  }
}
