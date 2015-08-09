package ctci.ch18;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q18_1_Add {

  public static void main(String[] args) {
    assertThat(add(1, 2), is(3));
    assertThat(add(0b011, 0b001), is(0b100));
    assertThat(add(17, 4711), is(17 + 4711));
  }

  private static int add(final int a, final int b) {
    int s = a;
    int c = b;
    while (c != 0) {
      int ns = s ^ c;
      int nc = (s & c) << 1;
      s = ns;
      c = nc;
    }
    return s;
  }
}
