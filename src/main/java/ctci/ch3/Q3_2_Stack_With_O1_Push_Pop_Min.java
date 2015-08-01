package ctci.ch3;

public class Q3_2_Stack_With_O1_Push_Pop_Min {

  private static final class MinStack<T extends Comparable<T>> {

    private int size;

    private static final class Node<T> {

      private final int i;
      private Node<T> next;
      private T e;

      public Node(final int i, final T e) {
        this.i = i;
        this.e = e;
      }

      @Override
      public String toString() {
        return String.valueOf(e);
      }
    }

    private Node<T> min;
    private Node<T> top;

    public void push(T e) {
      final Node<T> n = new Node<>(size, e);
      n.next = top;
      top = n;
      if (min == null || e.compareTo(min.e) <= 0) {
        final Node<T> newMin = new Node<>(size, e);
        newMin.next = min;
        min = newMin;
      }
      size++;
    }

    public T pop() {
      final T e = top.e;
      if (top.i == min.i) {
        min = min.next;
      }
      top = top.next;
      size--;
      return e;
    }

    public T min() {
      return min == null ? null : min.e;
    }

    public int size() {
      return size;
    }

    @Override
    public String toString() {
      final StringBuilder s = new StringBuilder("MinStack(");
      s.append("min=").append(min == null ? "" : min).append(", stack={");
      Node<T> n = top;
      while (n != null) {
        s.append(n.e);
        n = n.next;
        if (n != null) {
          s.append(',');
        }
      }
      s.append("})");
      return s.toString();
    }
  }

  public static void main(String[] args) {
    final MinStack<Integer> s = new MinStack<>();
    System.out.println(s);
    s.push(2);
    System.out.println(s);
    s.push(1);
    System.out.println(s);
    s.push(-1);
    System.out.println(s);
    s.push(-1);
    System.out.println(s);
    s.pop();
    System.out.println(s);
    s.pop();
    System.out.println(s);
    s.pop();
    System.out.println(s);
    s.pop();
    System.out.println(s);
  }
}
