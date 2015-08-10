package heap;

import java.util.Arrays;
import java.util.Comparator;

import static java.util.Arrays.asList;

public class BinaryHeap<T> {

  private final Comparator<T> comparator;

  private Object[] heap;

  private int size;
  private int insertIndex;

  public BinaryHeap(final int initialCapacity, final Comparator<T> comparator) {
    this.comparator = comparator;
    heap = new Object[initialCapacity];
  }

  public BinaryHeap(final Comparator<T> comparator) {
    this(16, comparator);
  }

  public void insert(final T value) {
    if (size == heap.length) {
      grow();
    }
    int index = size;
    heap[index] = value;
    size++;
    while (index > 0) {
      int parentIndex = (index - 1) >> 1;
      @SuppressWarnings("unchecked") T parent = (T) heap[parentIndex];
      if (comparator.compare(parent, value) < 0) {
        break;
      }
      heap[parentIndex] = value;
      heap[index] = parent;
      index = parentIndex;
    }
    do {
      insertIndex++;
    } while (insertIndex < heap.length && heap[insertIndex] != null);
  }

  private void grow() {
    heap = Arrays.copyOf(heap, heap.length << 1);
  }

  public int size() {
    return size;
  }

  @SuppressWarnings("unchecked")
  public T extract() {
    if (size == 0) {
      throw new IllegalStateException();
    }
    final T value = (T) heap[0];
    size--;

    // No bubbling needed if the heap is now empty
    if (size == 0) {
      heap[0] = null;
      insertIndex = 0;
      return value;
    }

    // Bubble up to restore heap
    int parentIndex = 0;
    T parent = (T) heap[size];
    heap[0] = parent;
    heap[size] = null;
    while (true) {
      final int ci1 = (parentIndex << 1) + 1;
      final int ci2 = ci1 + 1;
      final T c1 = (ci1 < size) ? (T) heap[ci1] : null;
      final T c2 = (ci2 < size) ? (T) heap[ci2] : null;
      if (c1 == null && c2 == null) {
        break;
      }
      final T c;
      final int ci;
      if (c1 != null && c2 != null) {
        if (comparator.compare(c1, c2) < 0) {
          c = c1;
          ci = ci1;
        } else {
          c = c2;
          ci = ci2;
        }
      } else if (c1 != null)  {
        c = c1;
        ci = ci1;
      } else {
        c = c2;
        ci = ci2;
      }
      if (comparator.compare(parent, c) < 0) {
        break;
      }
      heap[ci] = parent;
      heap[parentIndex] = c;
      parentIndex = ci;
    }

    return value;
  }

  @Override
  public String toString() {
    return asList(heap).subList(0, size).toString();
  }
}
