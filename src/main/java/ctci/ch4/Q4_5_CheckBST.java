package ctci.ch4;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q4_5_CheckBST {

  private static class Node {

    private final int value;
    private Node left;
    private Node right;

    public Node(final int value, final Node left, final Node right) {
      this.value = value;
      this.left = left;
      this.right = right;
    }

    public Node(final int value) {
      this(value, null, null);
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public boolean isBst() {
      if (left != null) {
        if (left.value >= value) {
          return false;
        }
        if (!left.isBst()) {
          return false;
        }
      }
      if (right != null) {
        if (right.value <= value) {
          return false;
        }
        if (!right.isBst()) {
          return false;
        }
      }
      return true;
    }
  }

  public static void main(final String... args) {
    final Node bst = new Node(5, new Node(3), new Node(7));
    final Node nonBst = new Node(1, new Node(2), new Node(3));
    assertThat(bst.isBst(), is(true));
    assertThat(nonBst.isBst(), is(false));
  }
}
