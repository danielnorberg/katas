package polynomials;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HornersMethod {

  public static void main(final String... args) {
    assertThat(evaluatePolynomial(17, 1.0, 0.5, 2.0), is(closeTo(1.0 + 0.5 * 17 + 2.0 * 17 * 17, 0.01)));
  }

  /**
   * O(n)
   */
  private static double evaluatePolynomial(final double x, final double... a) {
    double b = a[a.length - 1];
    for (int i = a.length - 2; i >= 0; i--) {
      b = a[i] + b * x;
    }
    return b;
  }
}
