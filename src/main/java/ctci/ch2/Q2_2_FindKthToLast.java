package ctci.ch2;

import com.google.common.base.Joiner;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q2_2_FindKthToLast {

  public static void main(final String... args) {
    assertThat(findKthToLastRecursive(3, list(1, 2, 3, 4, 5, 6, 7)), is(4));
    assertThat(findKthToLastIterative(3, list(1, 2, 3, 4, 5, 6, 7)), is(4));
  }

  private static int findKthToLastIterative(final int k, final Node list) {
    int len = 0;
    Node node = list;
    while (node != null) {
      len++;
      node = node.next;
    }
    node = list;
    final int index = len - k - 1;
    for (int i = 0; i < index; i++) {
      node = node.next;
    }
    return node.value;
  }

  private static class Pair {
    int i;
    int value;
  }

  private static int findKthToLastRecursive(final int k, final Node list) {
    final Pair value = new Pair();
    findKthToLastRecursive(value, k, list);
    return value.value;
  }

  private static void findKthToLastRecursive(final Pair value, final int k, final Node node) {
    if (node.next == null) {
      return;
    }
    findKthToLastRecursive(value, k, node.next);
    value.i++;
    if (value.i == k) {
      value.value = node.value;
    }
  }

  private static Node list(final int... values) {
    Node head = null;
    for (int i = values.length - 1; i >= 0; i--) {
      head = new Node(values[i], head);
    }
    return head;
  }

  private static class Node implements Iterable<Integer> {

    private final int value;

    private Node next;

    public Node(final int value, final Node next) {
      this.value = value;
      this.next = next;
    }

    public Node(final int value) {
      this(value, null);
    }

    @Override
    public Iterator<Integer> iterator() {
      return new Iterator<Integer>() {
        Node next = Node.this;

        @Override
        public boolean hasNext() {
          return next != null;
        }

        @Override
        public Integer next() {
          if (next == null) {
            throw new NoSuchElementException();
          }
          final int value = next.value;
          next = next.next;
          return value;
        }
      };
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

      if (value != node.value) {
        return false;
      }
      return !(next != null ? !next.equals(node.next) : node.next != null);
    }

    @Override
    public int hashCode() {
      int result = value;
      result = 31 * result + (next != null ? next.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "[" + Joiner.on(',').join(this) + "]";
    }
  }

}
