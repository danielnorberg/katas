package ctci.ch7;

import java.util.PriorityQueue;

public class Q7_7_Factors {

  public static void main(final String... args) {
    final int k = 17;
    final Factors factors = findFactors(k, 3, 5, 7);
    System.out.println(factors);
  }

  public static class Factors implements Comparable<Factors> {

    private final int a;
    private final int b;
    private final int c;
    private final int value;

    public Factors(final int a, final int b, final int c, final int value) {
      this.a = a;
      this.b = b;
      this.c = c;
      this.value = value;
    }

    public static Factors of(final int a, final int b, final int c, final int value) {
      return new Factors(a, b, c, value);
    }

    @Override
    public int compareTo(final Factors o) {
      return Integer.compare(value, o.value);
    }

    public static Factors of(final int a, final int b, final int c, final int x, final int y, final int z) {
      final int value = pow(x, a) * pow(y, b) * pow(z, c);
      return of(a, b, c, value);
    }

    private static int pow(final int a, final int b) {
      return (int) Math.pow(a, b);
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      final Factors factors = (Factors) o;

      if (a != factors.a) {
        return false;
      }
      if (b != factors.b) {
        return false;
      }
      if (c != factors.c) {
        return false;
      }
      return value == factors.value;

    }

    @Override
    public int hashCode() {
      int result = a;
      result = 31 * result + b;
      result = 31 * result + c;
      result = 31 * result + value;
      return result;
    }

    @Override
    public String toString() {
      return "Factors{" +
             "a=" + a +
             ", b=" + b +
             ", c=" + c +
             ", value=" + value +
             '}';
    }
  }

  private static Factors findFactors(final int k, final int x, final int y, final int z) {
    if (k <= 0) {
      throw new IllegalArgumentException();
    }
    final PriorityQueue<Factors> queue = new PriorityQueue<>();
    queue.add(Factors.of(1, 1, 1, x * y * z));
    Factors f = null;
    for (int i = 0; i < k; i++) {
      f = queue.poll();
      queue.add(Factors.of(f.a + 1, f.b, f.c, x, y, z));
      queue.add(Factors.of(f.a, f.b + 1, f.c, x, y, z));
      queue.add(Factors.of(f.a, f.b, f.c + 1, x, y, z));
    }
    return f;
  }
}
