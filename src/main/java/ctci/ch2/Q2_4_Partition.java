package ctci.ch2;

import com.google.common.base.Joiner;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q2_4_Partition {

  public static void main(final String... args) {
    assertThat(partition(5, list(1,9,2,8,3,7,4,6)), is(list(1, 2, 3, 4, 9, 8, 7, 6)));
  }

  private static Node partition(final int pivot, final Node list) {
    Node head = new Node(0, list);
    Node i = head;
    Node p = head;
    Node n = list;
    while (n != null) {
      if (n.value < pivot) {
        p.next = n.next;
        n.next = i.next;
        i.next = n;
        i = n;
      }
      p = n;
      n = n.next;
    }
    p.next = null;
    return head.next;
  }

  private static Node list(final int... values) {
    Node head = null;
    for (int i = values.length - 1; i >= 0; i--) {
      head = new Node(values[i], head);
    }
    return head;
  }

  private static class Node implements Iterable<Integer> {

    private int value;

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
