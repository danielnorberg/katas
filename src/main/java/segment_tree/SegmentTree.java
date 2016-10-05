package segment_tree;

import com.google.common.primitives.Ints;

import java.util.List;

public class SegmentTree {

  private final Node root;
  private final int size;

  public SegmentTree(final Node root, final int size) {
    this.root = root;
    this.size = size;
  }

  public int min() {
    return min(0, size);
  }

  public int min(final int start, final int end) {
    return min(root, start, end);
  }

  private int min(Node n, final int qs, final int qe) {
    if (n == null) {
      return Integer.MAX_VALUE;
    }

    if (qs <= n.start && n.end <= qe) {
      return n.min;
    } else if (n.end <= qs || qe <= n.start) {
      return Integer.MAX_VALUE;
    } else {
      return Math.min(min(n.left, qs, qe), min(n.right, qs, qe));
    }
  }

  public static SegmentTree of(int... values) {
    return of(Ints.asList(values));
  }

  private static SegmentTree of(final List<Integer> values) {
    return new SegmentTree(node(values, 0, values.size()), values.size());
  }

  private static Node node(final List<Integer> values, final int start, final int end) {
    if (start >= end) {
      return null;
    }
    if (start + 1 == end) {
      return new Node(values.get(start), start, end, null, null);
    }
    final int middle = start + (end - start) / 2;
    final Node left = node(values, start, middle);
    final Node right = node(values, middle, end);
    final int min = Math.min(
        left == null ? Integer.MAX_VALUE : left.min,
        right == null ? Integer.MAX_VALUE : right.min);
    return new Node(min, start, end, left, right);
  }

  private static final class Node {

    private final int min;
    private final int start;
    private final int end;
    private final Node left;
    private final Node right;

    private Node(final int min, final int start, final int end, final Node left, final Node right) {
      this.min = min;
      this.start = start;
      this.end = end;
      this.left = left;
      this.right = right;
    }
  }
}
