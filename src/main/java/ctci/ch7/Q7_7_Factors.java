package ctci.ch7;

import com.google.auto.value.AutoValue;

import java.util.PriorityQueue;

public class Q7_7_Factors {

  public static void main(final String... args) {
    final int k = 17;
    final Factors factors = findFactors(k, 3, 5, 7);
    System.out.println(factors);
  }

  @AutoValue
  public abstract static class Factors implements Comparable<Factors> {

    public abstract int a();

    public abstract int b();

    public abstract int c();

    public abstract int value();

    public static Factors of(final int a, final int b, final int c, final int value) {
      return new AutoValue_Q7_7_Factors_Factors(a, b, c, value);
    }

    @Override
    public int compareTo(final Factors o) {
      return Integer.compare(value(), o.value());
    }

    public static Factors of(final int a, final int b, final int c, final int x, final int y, final int z) {
      final int value = pow(x, a) * pow(y, b) * pow(z, c);
      return of(a, b, c, value);
    }

    private static int pow(final int a, final int b) {
      return (int) Math.pow(a, b);
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
      queue.add(Factors.of(f.a() + 1, f.b(), f.c(), x, y, z));
      queue.add(Factors.of(f.a(), f.b() + 1, f.c(), x, y, z));
      queue.add(Factors.of(f.a(), f.b(), f.c() + 1, x, y, z));
    }
    return f;
  }
}
