package gcd;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GCD {
  public static void main(final String... args) {
    assertThat(naiveGCD(3 * 4, 4 * 7), is(4));
    assertThat(naiveGCD(4 * 7, 3 * 4), is(4));
    assertThat(euclidianGCD(3 * 4, 4 * 7), is(4));
    assertThat(euclidianGCD(4 * 7, 3 * 4), is(4));
  }

  private static int naiveGCD(final int a, final int b) {
    if (a > b) {
      return naiveGCD(b, a);
    }
    for (int i = a; i > 0; i--) {
      if (a % i == 0 && b % i == 0) {
        return i;
      }
    }
    return 1;
  }

  private static int euclidianGCD(int a, int b) {
    while (a != b) {
      if (a < b) {
        b = b - a;
      } else {
        a = a - b;
      }
    }
    return a;
  }
}
