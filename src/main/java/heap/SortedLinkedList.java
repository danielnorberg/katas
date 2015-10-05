package heap;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SortedLinkedList<T> extends AbstractList<T> {

  private static class Entry<T> {

    private Entry<T> next;
    private T value;

    public Entry(final T t) {
      this.value = Objects.requireNonNull(t);
    }
  }

  private final Comparator<T> comparator;

  private Entry<T> head;

  public SortedLinkedList(final Comparator<T> comparator) {
    this.comparator = comparator;
  }

  @Override
  public T get(final int index) {
    Entry<T> e = head;
    for (int i = 0; i < index; i++) {
      e = e.next;
      if (e == null) {
        throw new IndexOutOfBoundsException();
      }
    }
    return e.value;
  }

  @Override
  public int size() {
    Entry<T> e = head;
    int size = 0;
    while (e != null) {
      e = e.next;
      size++;
    }
    return size;
  }

  @Override
  public boolean add(final T t) {
    // Special case: insert at head
    final Entry<T> ne = new Entry<>(t);
    if (head == null || comparator.compare(t, head.value) <= 0) {
      ne.next = head;
      head = ne;
      return true;
    }

    Entry<T> e = head;
    while (true) {
      if (e.next == null || comparator.compare(t, e.next.value) <= 0) {
        ne.next = e.next;
        e.next = ne;
        return true;
      }
      e = e.next;
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new It();
  }

  private class It implements Iterator<T> {

    private Entry<T> e = head;

    @Override
    public boolean hasNext() {
      return e != null;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      final T value = e.value;
      e = e.next;
      return value;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
