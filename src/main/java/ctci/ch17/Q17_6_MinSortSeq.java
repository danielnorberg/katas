package ctci.ch17;

import com.google.common.collect.Range;

public class Q17_6_MinSortSeq {

  public static void main(final String... args) {
    System.out.println(minSortSeq(1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19));
  }

  private static Range minSortSeq(final int... ns) {

    // Find last integer that is smaller than the greatest preceding integer
    int max = Integer.MIN_VALUE;
    int end = 0;
    for (int i = 0; i < ns.length; i++) {
      final int n = ns[i];
      if (n > max) {
        max = n;
      }
      if (n < max) {
        end = i;
      }
    }

    // Inverse - Find first integer that is greater than the smallest later integer
    int min = Integer.MAX_VALUE;
    int start = 0;
    for (int i = ns.length - 1; i >= 0; i--) {
      final int n = ns[i];
      if (n < min) {
        min = n;
      }
      if (n > min) {
        start = i;
      }
    }

    return Range.closed(start, end);
  }
}
