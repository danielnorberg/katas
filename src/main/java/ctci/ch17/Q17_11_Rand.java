package ctci.ch17;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class Q17_11_Rand {

  private static int rand5() {
    return ThreadLocalRandom.current().nextInt(0, 5);
  }

  private static int rand2() {
    while (true) {
      final int r = rand5();
      if (r == 4) {
        continue;
      }
      return r & 1;
    }
  }

  private static int rand7() {
    while (true) {
      final int r = (rand2() << 2) | (rand2() << 1) | rand2();
      if (r == 7) {
        continue;
      }
      return r;
    }
  }

  public static void main(String[] args) {
    final int[] hist = new int[7];
    for (int i = 0; i < 10000; i++) {
      final int r = rand7();
      assertThat(r, is(both(greaterThanOrEqualTo(0)).and(lessThan(7))));
      hist[r]++;
    }
    System.out.println(Arrays.toString(hist));
  }
}
