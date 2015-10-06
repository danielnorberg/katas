package exp;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class POW {

  public static void main(final String... args) {
    assertThat(pow(3L, 13L), is(BigInteger.valueOf(3).pow(13).longValue()));
    assertThat(pow(BigInteger.valueOf(3), BigInteger.valueOf(13)), is(BigInteger.valueOf(3).pow(13)));
    assertThat(pow(BigInteger.valueOf(17), BigInteger.valueOf(4711)), is(BigInteger.valueOf(17).pow(4711)));
  }

  /**
   * O(log n)
   */
  private static long pow(final long a, final long b) {
    if (b < 0) {
      throw new IllegalArgumentException();
    }
    if (b == 0) {
      return 1;
    }
    if ((b & 1L) == 0) {
      return pow(a * a, b / 2);
    } else {
      return a * pow(a, b - 1);
    }
  }

  /**
   * O(log n)
   */
  private static BigInteger pow(final BigInteger a, final BigInteger b) {
    final int cmp = b.compareTo(ZERO);
    if (cmp < 0) {
      throw new IllegalArgumentException();
    }
    if (cmp == 0) {
      return ONE;
    }
    if (b.testBit(0)) {
      return a.multiply(pow(a, b.subtract(ONE)));
    } else {
      return pow(a.multiply(a), b.shiftRight(1));
    }
  }
}
