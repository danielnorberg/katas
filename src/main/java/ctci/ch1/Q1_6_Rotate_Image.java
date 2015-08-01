package ctci.ch1;

public class Q1_6_Rotate_Image {

  public static void main(String[] args) {
    for (int n = 1; n < 10; n++) {
      System.out.println("=== n: " +n);
      final int[][] image = image(n);
      System.out.println("before");
      print(image);
      rotate(image);
      System.out.println("after");
      print(image);
    }
  }

  private static void print(final int[][] image) {
    for (final int[] row : image) {
      for (final int p : row) {
        System.out.printf("%2d ", p);
      }
      System.out.println();
    }
  }

  private static int[][] image(final int n) {
    final int[][] image = new int[n][];
    int i = 1;
    for (int y = 0; y < n; y++) {
      image[y] = new int[n];
      for (int x = 0; x < n; x++, i++) {
        image[y][x] = i;
      }
    }
    return image;
  }

  /**
   * y1                | y2 = x1
   * x1                | x2 = n - y1
   * - - - - - - - - - + - - - - - -
   * y4 = x3           | y3 = x2
   * x4 = n - y3       | x3 = n - y2
   */
  public static void rotate(final int[][] image) {
    final int n = image.length;
    if (n < 1) {
      throw new IllegalArgumentException();
    }
    for (final int[] row : image) {
      if (row.length != n) {
        throw new IllegalArgumentException();
      }
    }
    final int m = n - 1;
    final int mx = n / 2;
    final int my = mx + ((n % 2 == 0) ? 0 : 1);
    for (int y1 = 0; y1 < my; y1++) {
      for (int x1 = 0; x1 < mx; x1++) {
        final int p1 = image[y1][x1];
        final int y2 = x1;
        final int x2 = m - y1;
        final int p2 = image[y2][x2];
        image[y2][x2] = p1;
        final int y3 = x2;
        final int x3 = m - y2;
        final int p3 = image[y3][x3];
        image[y3][x3] = p2;
        final int y4 = x3;
        final int x4 = m - y3;
        final int p4 = image[y4][x4];
        image[y4][x4] = p3;
        image[y1][x1] = p4;
      }
    }
  }
}
