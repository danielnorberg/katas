package ctci.ch7;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q7_4_MulSubDiv {

  public static void main(final String... args) {
    assertThat(mul(0, 0), is(0));
    assertThat(mul(1, 0), is(0));
    assertThat(mul(0, 1), is(0));

    assertThat(mul(1, 1), is(1));
    assertThat(mul(1, -1), is(-1));
    assertThat(mul(-1, -1), is(1));

    assertThat(mul(1, 2), is(2));
    assertThat(mul(1, -2), is(-2));
    assertThat(mul(-1, 2), is(-2));
    assertThat(mul(-1, -2), is(2));

    assertThat(mul(2, 2), is(4));
    assertThat(mul(2, -2), is(-4));
    assertThat(mul(-2, 2), is(-4));
    assertThat(mul(-2, -2), is(4));

    assertThat(sub(0, 0), is(0));
    assertThat(sub(0, 1), is(-1));
    assertThat(sub(0, -1), is(1));

    assertThat(sub(1, 0), is(1));
    assertThat(sub(1, 1), is(0));
    assertThat(sub(1, -1), is(2));
    assertThat(sub(-1, 1), is(-2));

    assertThat(sub(2, 0), is(2));
    assertThat(sub(2, 1), is(1));
    assertThat(sub(2, -1), is(3));
    assertThat(sub(2, -2), is(4));
    assertThat(sub(-2, 1), is(-3));
    assertThat(sub(-1, 2), is(-3));
    assertThat(sub(-2, 2), is(-4));
    assertThat(sub(-2, -2), is(0));

    assertThat(div(1, 1), is(1));
    assertThat(div(1, -1), is(-1));
    assertThat(div(-1, 1), is(-1));
    assertThat(div(-1, -1), is(1));
    assertThat(div(2, 1), is(2));
    assertThat(div(-2, 1), is(-2));
    assertThat(div(2, -1), is(-2));
    assertThat(div(-2, -1), is(2));
    assertThat(div(2, 2), is(1));
    assertThat(div(2, -2), is(-1));
    assertThat(div(-2, 2), is(-1));
    assertThat(div(-2, -2), is(1));
    assertThat(div(3, 2), is(1));
    assertThat(div(3, -2), is(-1));
    assertThat(div(-3, 2), is(-1));
    assertThat(div(-3, -2), is(1));
    assertThat(div(4, 2), is(2));
    assertThat(div(4, -2), is(-2));
    assertThat(div(-4, 2), is(-2));
    assertThat(div(-4, -2), is(2));
    assertThat(div(1, 2), is(0));
    assertThat(div(1, -2), is(0));
    assertThat(div(-1, 2), is(0));
    assertThat(div(-1, -2), is(0));
    assertThat(div(2, 4), is(0));
    assertThat(div(2, -4), is(0));
    assertThat(div(-2, 4), is(0));
    assertThat(div(-2, -4), is(0));
  }

  private static int div(final int x, final int y) {
    if (y == 0) {
      throw new IllegalArgumentException();
    }
    if (x < 0) {
      return -div(-x, y);
    }
    if (y < 0) {
      return -div(x, -y);
    }
    int res = 0;
    int s = 0;
    for (int i = 30; i >= 0 && s != x; i--) {
      final int v = y << i;
      if (v <= 0) {
        continue;
      }
      if (s + v <= x) {
        s += v;
        res |= 1 << i;
      }
    }
    return res;
  }

  private static int sub(final int x, final int y) {
    return x + mul(y, -1);
  }

  private static int mul(final int x, final int y) {
    if (x < -1) {
      return -mul(-x, y);
    }
    if (y < -1) {
      return -mul(x, -y);
    }
    int res = 0;
    for (int i = 0; i < 32; i++) {
      final int b = 1 << i;
      if ((y & b) != 0) {
        res += x << i;
      }
    }
    return res;
  }
}
