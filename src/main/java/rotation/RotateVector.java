package rotation;

import static org.junit.Assert.assertArrayEquals;

public class RotateVector {

  public static void main(final String... args) {
    final char[] v = "abcdefgh".toCharArray();
    final char[] e = "defghabc".toCharArray();
    assertArrayEquals(juggleRotate(v.clone(), -3), e);
    assertArrayEquals(reverseRotate(v.clone(), -3), e);
  }

  private static char[] reverseRotate(final char[] v, final int i) {
    // abc defgh
    // cba hgfed
    // cbahgfed
    // defghabc

    final int pivot = (i < 0) ? -i : v.length - i;
    reverse(v, 0, pivot);
    reverse(v, pivot, v.length);
    reverse(v, 0, v.length);
    return v;
  }

  private static void reverse(final char[] v, final int start, final int end) {
    final int n = (end - start) / 2;
    for (int i = 0, a = start, b = end - 1; i < n; i++, a++, b--) {
      swap(v, a, b);
    }
  }

  private static void swap(final char[] v, final int a, final int b) {
    final char tmp = v[a];
    v[a] = v[b];
    v[b] = tmp;
  }

  private static char[] juggleRotate(final char[] v, final int i) {
    int n = 0;
    int k = 0;
    while (n < v.length) {
      int s = k;
      char c = v[s];
      do {
        int d = s + i;
        if (d < 0) {
          d += v.length;
        }
        final char tmp = v[d];
        v[d] = c;
        c = tmp;
        s = d;
        n++;
      } while (s != k);
      k++;
    }
    return v;
  }

}
