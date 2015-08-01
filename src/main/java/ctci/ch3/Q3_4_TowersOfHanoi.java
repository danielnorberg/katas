package ctci.ch3;

import java.util.ArrayDeque;
import java.util.Deque;

public class Q3_4_TowersOfHanoi {

  private static class TowersOfHanoi {
    private final Deque<Integer> a = new ArrayDeque<>();
    private final Deque<Integer> b = new ArrayDeque<>();
    private final Deque<Integer> c = new ArrayDeque<>();

    public TowersOfHanoi(final int n) {
      for (int i = 0; i < n; i++) {
        a.push(n - i);
      }
    }

    public void move() {
      move(a.size());
    }

    public void move(final int n) {
      move(a, c, b, n);
    }

    private void move(final Deque<Integer> from, final Deque<Integer> to, final Deque<Integer> tmp, final int n) {
      if (n == 0) {
        return;
      }
      move(from, tmp, to, n - 1);
      if (!to.isEmpty() && to.peek() < from.peek()) {
        throw new AssertionError();
      }
      to.push(from.pop());
      move(tmp, to, from, n - 1);
      System.out.println(this);
    }

    @Override
    public String toString() {
      return "TowersOfHanoi{" +
             "a=" + a +
             ", b=" + b +
             ", c=" + c +
             '}';
    }
  }

  public static void main(String[] args) {
    final TowersOfHanoi towers = new TowersOfHanoi(10);
    System.out.println("before: " + towers);
    towers.move();
    System.out.println("after: " + towers);
  }

}
