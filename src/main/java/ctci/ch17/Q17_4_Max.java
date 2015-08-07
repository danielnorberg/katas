package ctci.ch17;

public class Q17_4_Max {

  public static int sign(int x) {
    return (x >> 31) & 0x1;
  }

  private static int max(final int a, final int b) {
    final int ka = sign(b - a);
    final int kb = sign(a - b);
    return a * ka + b * kb;
  }

  public static void main(final String... args) {
    System.out.println(max(17, 4711));
    System.out.println(max(1, -1));
    System.out.println(max(0, 17));
  }
}
