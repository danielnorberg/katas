package segment_tree;

import com.google.common.primitives.Ints;

import java.util.List;

public class SegmentTree {

  private final int[] heap;
  private final int size;

  public SegmentTree(final int[] heap, final int size) {
    this.heap = heap;
    this.size = size;
  }

  public int min() {
    return min(0, size - 1);
  }

  public int min(final int qs, final int qe) {
    if (qs < 0 || qs > qe || qe >= size) {
      throw new IllegalArgumentException();
    }
    return min(qs, qe, 0, 0, size - 1);
  }

  private int min(final int qs, final int qe, final int i, final int ns, final int ne) {
    final int mid = mid(ns, ne);

    if (qs <= ns && ne <= qe) {
      return heap[i];
    } else if (ne < qs || qe < ns) {
      return Integer.MAX_VALUE;
    } else {
      return Math.min(min(qs, qe, i * 2 + 1, ns, mid),
                      min(qs, qe, i * 2 + 2, mid + 1, ne));
    }
  }

  public static SegmentTree of(int... values) {
    return of(Ints.asList(values));
  }

  public static SegmentTree of(final List<Integer> values) {
    final int height = 32 - Integer.numberOfLeadingZeros(values.size());
    final int heapSize = (int) (2 * Math.pow(2, height));
    final int[] heap = new int[heapSize];
    populate(heap, values, 0, 0, values.size() - 1);
    return new SegmentTree(heap, values.size());
  }

  private static int populate(final int[] heap, final List<Integer> values, final int i, final int ns, final int ne) {
    if (ne < ns) {
      return Integer.MAX_VALUE;
    }
    if (ns == ne) {
      int value = values.get(ns);
      heap[i] = value;
      return value;
    }
    final int mid = mid(ns, ne);
    final int left = i * 2 + 1;
    final int right = i * 2 + 2;
    final int minLeft = populate(heap, values, left, ns, mid);
    final int minRight = populate(heap, values, right, mid + 1, ne);
    final int min = Math.min(minLeft, minRight);
    heap[i] = min;
    return min;
  }

  private static int mid(final int ns, final int ne) {
    return ns + (ne - ns) / 2;
  }
}
