package ctci.ch4;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class Q4_6_BstNext {

  private static class Node {

    private final int value;
    private Node parent;
    private Node left;
    private Node right;

    public Node(final int value, final Node left, final Node right) {
      this.value = value;
      this.left = left;
      this.right = right;
      if (left != null) {
        left.parent = this;
      }
      if (right != null) {
        right.parent = this;
      }
    }

    public Node(final int value) {
      this(value, null, null);
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public int value() {
      return value;
    }

    public Node first() {
      if (left != null) {
        return left.first();
      }
      return this;
    }

    public Node next() {
      if (right != null) {
        Node next = right;
        while (next.left != null) {
          next = next.left;
        }
        return next;
      }
      Node n = this;
      while (n.parent != null) {
        if (n == n.parent.left) {
          // Parent is greater
          return n.parent;
        } else {
          // Parent is smaller. Back up until we find a greater node.
          n = n.parent;
        }
      }
      return null;
    }
  }

  public static void main(final String... args) {
    final Node bst = new Node(5, new Node(3), new Node(7));
    assertThat(bst.first().value, is(3));
    assertThat(bst.first().next().value, is(5));
    assertThat(bst.first().next().next().value, is(7));
    assertThat(bst.first().next().next().next(), is(nullValue()));
  }
}
