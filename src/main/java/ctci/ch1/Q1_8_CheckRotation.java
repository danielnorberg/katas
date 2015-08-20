package ctci.ch1;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q1_8_CheckRotation {

  public static void main(final String... args) {
    assertThat(isRotation("foobar", "barfoo"), is(true));
    assertThat(isRotation("foobaz", "barfoo"), is(false));
  }

  private static boolean isRotation(final String s1, final String s2) {
    return (s1 + s1).contains(s2);
  }
}
