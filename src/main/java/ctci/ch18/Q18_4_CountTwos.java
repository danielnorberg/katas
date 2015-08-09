package ctci.ch18;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q18_4_CountTwos {

  public static void main(String[] args) {
    assertThat(pow(10, 0), is(1));
    assertThat(pow(10, 1), is(10));
    assertThat(pow(10, 2), is(100));
    assertThat(countTwosBruteForce(25), is(9));
    assertThat(countTwosByDigit(25), is(9));
  }

  private static int countTwosBruteForce(final int n) {
    int count = 0;
    for (int i = 0; i <= n; i++) {
      int x = i;
      while (x > 0) {
        if (x % 10 == 2) {
          count++;
        }
        x /= 10;
      }
    }
    return count;
  }

  private static int countTwosByDigit(final int n) {
    int count = 0;
    int d = 0;
    int x = n;
    while (x > 0) {
      final int digit = x % 10;
      // d == 0: x == 25
      // d == 0: x / 10 == 2
      // d == 0: y == 2 * 1 == 2
      // d == 0: digit == 5

      // d == 1: x == 2
      // d == 1: y == 0
      // d == 1: digit = 2

      final int y = (x / 10) * pow(10, d);
      final int twos;
      if (digit < 2) {
        twos = y;
      } else if (digit == 2) {
        // d == 1: n == 25
        // d == 1: x == 2
        // d == 1: pow(10, d) == pow(10, 1) == 10
        // d == 1: twosInRange == 1 + 25 - 2 * 10 == 1 + 25 - 20 == 6 (20, 21, 22, 23, 24, 25)
        // d == 1: twos == 0 + 6 == 6
        final int twosInRange = 1 + n - x * pow(10, d);
        twos = y + twosInRange;
      } else {
        // d == 0: twos == 2 + 1 == 3
        twos = y + 1;
      }
      // d == 0: count == 3
      // d == 1: count == 3 + 6 == 9
      count += twos;
      x /= 10;
      d++;
    }
    return count;
  }

  private static int pow(final int x, final int n) {
    int pow = 1;
    for (int j = 0; j < n; j++) {
      pow *= x;
    }
    return pow;
  }
}
