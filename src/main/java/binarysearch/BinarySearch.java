package binarysearch;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BinarySearch {

  public static void main(final String... args) {
    assertThat(binarySearch(7, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9), is(7));
    assertThat(binarySearch(7, 0, 5, 10), is(2));
    assertThat(binarySearch(17, 0, 5, 10), is(3));
    assertThat(sequentialSearch(7, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9), is(7));
    assertThat(sequentialSearch(7, 0, 5, 10), is(2));
    assertThat(sequentialSearch(17, 0, 5, 10), is(3));
    fuzz();
  }

  private static void fuzz() {
    while (true) {
      final ThreadLocalRandom r = ThreadLocalRandom.current();
      final int n = r.nextInt(10_000);
      final int[] vs = r.ints(n).toArray();
      Arrays.sort(vs);
      final int needle = r.nextInt();
      final int expected = sequentialSearch(needle, vs);
      final Integer expectedV = expected < vs.length ? vs[expected] : null;
      final int i = binarySearch(needle, vs);
      final Integer v = i < vs.length ? vs[i] : null;
      if (i != expected && !Objects.equals(expectedV, v)) {
        System.out.println(needle);
        System.out.println(Arrays.toString(vs));
        throw new AssertionError();
      }
    }
  }

  private static int sequentialSearch(final int needle, final int... vs) {
    int i;
    for (i = 0; i < vs.length; i++) {
      final int v = vs[i];
      if (needle <= v) {
        break;
      }
    }
    return i;
  }

  public static int binarySearch(final int needle, final int... vs) {
    int a = 0;
    int b = vs.length;
    int i = 0;
    while (a < b) {
      i = a + (b - a) / 2;
      if (i >= vs.length) {
        break;
      }
      // 0: i = 5
      // 1: i = 5 + (10 - 5) / 2 = 5 + 5 / 2 = 5 + 2 = 7
      // 2: i = 5 + (7 - 5) / 2 = 5 + 2 / 2 = 6
      final int v = vs[i];
      // 0: v = vs[5] = 6
      // 1: v = vs[7] = 8
      // 2: v = vs[6] = 7
      if (v == needle) {
        return i;
      } else if (v < needle) {
        i++;
        a = i;
        // 0: a = 5
      } else {
        b = i;
        // 1: b = 7
      }
    }
    return i;
  }
}
