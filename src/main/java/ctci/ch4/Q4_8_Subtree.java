package ctci.ch4;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class Q4_8_Subtree {

  private static class Node {

    private final int hashCode;
    private final int value;
    private Node left;
    private Node right;

    public Node(final int value, final Node left, final Node right) {
      this.value = value;
      this.left = left;
      this.right = right;
      this.hashCode = hashCode0();
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

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      final Node node = (Node) o;

      if (hashCode != node.hashCode) {
        return false;
      }
      if (value != node.value) {
        return false;
      }
      if (left != null ? !left.equals(node.left) : node.left != null) {
        return false;
      }
      return !(right != null ? !right.equals(node.right) : node.right != null);
    }

    @Override
    public int hashCode() {
      return hashCode;
    }

    private int hashCode0() {
      int result = value;
      result = 31 * result + (left != null ? left.hashCode() : 0);
      result = 31 * result + (right != null ? right.hashCode() : 0);
      return result;
    }
  }

  public static void main(final String... args) {
    final Node needle = new Node(-1, new Node(-2), new Node(-3));
    final int height = 16;
    final int t = ThreadLocalRandom.current().nextInt(0, (int) Math.pow(2, height));
    final Node haystack = tree(0, t, height, needle);
    final Set<Node> index = index(haystack);
    assertThat(needle, isIn(index));
    assertThat(new Node(-1), not(isIn(index)));
    final Node unexpected = new Node(-10, new Node(-11), new Node(-12));
    assertThat(unexpected, not(isIn(index)));
  }

  private static Set<Node> index(final Node node) {
    final Set<Node> index = new HashSet<>();
    index(index, node);
    return index;
  }

  private static void index(final Set<Node> index, final Node node) {
    index.add(node);
    if (node.left != null) {
      index(index, node.left);
    }
    if (node.right != null) {
      index(index, node.right);
    }
  }

  private static Node tree(final int i, final int t, final int height, final Node needle) {
    if (height == 0) {
      if (i == t) {
        return needle;
      } else {
        return null;
      }
    }
    final int value = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
    final Node left = tree(i * 2, t, height - 1, needle);
    final Node right = tree(i * 2 + 1, t, height - 1, needle);
    return new Node(value, left, right);
  }
}
