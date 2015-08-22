package ctci.ch2;

import com.google.common.base.Joiner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q2_1_Dedup {

  public static void main(final String... args) {
    final Node list = list(1, 1, 2, 3, 3, 1, 2);
    dedup(list);
    assertThat(list, is(list(1, 2, 3)));
  }

  private static Node list(final int... values) {
    Node head = null;
    for (int i = values.length - 1; i >= 0; i--) {
      head = new Node(values[i], head);
    }
    return head;
  }

  private static void dedup(final Node list) {
    final Set<Integer> seen = new HashSet<>();
    Node prev = list;
    seen.add(prev.value);
    Node node = list.next;
    while (node != null) {
      if (seen.contains(node.value)) {
        node = node.next;
        while (node != null && seen.contains(node.value)) {
          node = node.next;
        }
        prev.next = node;
        if (node == null) {
          return;
        }
      }
      seen.add(node.value);
      prev = node;
      node = node.next;
    }
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
