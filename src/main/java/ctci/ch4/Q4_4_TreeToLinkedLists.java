package ctci.ch4;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class Q4_4_TreeToLinkedLists {

  public static void main(final String... args) {
    //       a
    //    b    c
    //   d e    f
    //           g

    final Node<String> g = new Node<>("g");
    final Node<String> f = new Node<>("f", null, g);
    final Node<String> c = new Node<>("c", null, f);
    final Node<String> d = new Node<>("d");
    final Node<String> e = new Node<>("e");
    final Node<String> b = new Node<>("b", d, e);
    final Node<String> a = new Node<>("a", b, c);

    final List<Node<String>> linkedLists = a.toLinkedLists();
    assertThat(linkedLists, is(asList(
        asList("a"),
        asList("b", "c"),
        asList("d", "e", "f"),
        asList("g")
    )));
  }

  private static class Node<T> extends AbstractList<T> {

    private final T value;
    private Node<T> left;
    private Node<T> right;

    public Node(final T value, final Node<T> left, final Node<T> right) {
      this.value = value;
      this.left = left;
      this.right = right;
    }

    public Node(final T value) {
      this(value, null, null);
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public List<Node<T>> toLinkedLists() {
      final List<Node<T>> heads = new ArrayList<>();
      final List<Node<T>> tails = new ArrayList<>();
      toLinkedLists(heads, tails, 0);
      return heads;
    }

    @Override
    public T get(final int index) {
      int i = 0;
      Node<T> node = this;
      while (i != index) {
        node = node.right;
        if (node == null) {
          throw new IndexOutOfBoundsException();
        }
        i++;
      }
      return node.value;
    }

    @Override
    public int size() {
      int size = 0;
      Node<T> node = this;
      while (node != null) {
        node = node.right;
        size++;
      }
      return size;
    }

    @Override
    public Iterator<T> iterator() {
      return listIterator();
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
      final ListIterator<T> it = listIterator();
      for (int i = 0; i < index; i++) {
        it.next();
      }
      return it;
    }

    @Override
    public ListIterator<T> listIterator() {
      return new ListIterator<T>() {
        int i = 0;
        Node<T> prev = null;
        Node<T> next = Node.this;

        @Override
        public boolean hasNext() {
          return next != null;
        }

        @Override
        public T next() {
          if (next == null)  {
            throw new NoSuchElementException();
          }
          final T value = next.value;
          this.prev = next;
          this.next = next.right;
          i++;
          return value;
        }

        @Override
        public boolean hasPrevious() {
          return prev != null;
        }

        @Override
        public T previous() {
          if (prev == null)  {
            throw new NoSuchElementException();
          }
          final T value = prev.value;
          this.next = prev;
          this.prev = prev.right;
          i--;
          return value;
        }

        @Override
        public int nextIndex() {
          return i;
        }

        @Override
        public int previousIndex() {
          return i - 1;
        }

        @Override
        public void remove() {
          throw new UnsupportedOperationException();
        }

        @Override
        public void set(final T t) {
          throw new UnsupportedOperationException();
        }

        @Override
        public void add(final T t) {
          throw new UnsupportedOperationException();
        }
      };
    }

    private void toLinkedLists(final List<Node<T>> heads, final List<Node<T>> tails, final int level) {
      final Node<T> left = this.left;
      final Node<T> right = this.right;
      this.left = null;
      final Node<T> tail;
      if (heads.size() <= level) {
        heads.add(this);
        tails.add(this);
        tail = this;
      } else {
        tail = tails.get(level);
      }
      if (left != null) {
        left.toLinkedLists(heads, tails, level + 1);
      }
      if (right != null) {
        right.toLinkedLists(heads, tails, level + 1);
      }
      if (tail != this) {
        tail.right = this;
        this.left = tail;
        tails.set(level, this);
      }
      this.right = null;
    }
  }
}
