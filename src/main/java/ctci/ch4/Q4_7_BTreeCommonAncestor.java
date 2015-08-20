package ctci.ch4;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q4_7_BTreeCommonAncestor {

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

    public boolean isAncestor(Node n) {
      while (n != null) {
        if (n == this) {
          return true;
        }
        n = n.parent;
      }
      return false;
    }
  }

  public static void main(final String... args) {
    //     1
    //   2  3
    //  4 5

    final Node n4 = new Node(4);
    final Node n5 = new Node(5);
    final Node n2 = new Node(2, n4, n5);
    final Node n3 = new Node(3);
    final Node n1 = new Node(1, n2, n3);
    assertThat(commonAncestor(n1, n1), is(n1));
    assertThat(commonAncestor(n2, n1), is(n1));
    assertThat(commonAncestor(n2, n3), is(n1));
    assertThat(commonAncestor(n4, n3), is(n1));
    assertThat(commonAncestor(n4, n5), is(n2));
  }

  private static Node commonAncestor(final Node a, final Node b) {
    Node n = a;
    while (n != null) {
      if (n.isAncestor(b)) {
        return n;
      }
      n = n.parent;
    }
    return null;
  }
}
