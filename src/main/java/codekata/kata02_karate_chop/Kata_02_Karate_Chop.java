package codekata.kata02_karate_chop;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Kata_02_Karate_Chop {

  public static void main(final String... args) {
    assertEquals(-1, chop(3, ImmutableList.of()));
    assertEquals(-1, chop(3, ImmutableList.of(1)));
    assertEquals(0, chop(1, ImmutableList.of(1)));

    assertEquals(0, chop(1, ImmutableList.of(1, 3, 5)));
    assertEquals(1, chop(3, ImmutableList.of(1, 3, 5)));
    assertEquals(2, chop(5, ImmutableList.of(1, 3, 5)));
    assertEquals(-1, chop(0, ImmutableList.of(1, 3, 5)));
    assertEquals(-1, chop(2, ImmutableList.of(1, 3, 5)));
    assertEquals(-1, chop(4, ImmutableList.of(1, 3, 5)));
    assertEquals(-1, chop(6, ImmutableList.of(1, 3, 5)));

    assertEquals(0, chop(1, ImmutableList.of(1, 3, 5, 7)));
    assertEquals(1, chop(3, ImmutableList.of(1, 3, 5, 7)));
    assertEquals(2, chop(5, ImmutableList.of(1, 3, 5, 7)));
    assertEquals(3, chop(7, ImmutableList.of(1, 3, 5, 7)));
    assertEquals(-1, chop(0, ImmutableList.of(1, 3, 5, 7)));
    assertEquals(-1, chop(2, ImmutableList.of(1, 3, 5, 7)));
    assertEquals(-1, chop(4, ImmutableList.of(1, 3, 5, 7)));
    assertEquals(-1, chop(6, ImmutableList.of(1, 3, 5, 7)));
    assertEquals(-1, chop(8, ImmutableList.of(1, 3, 5, 7)));
  }

  private static int chop(final int needle, final List<Integer> haystack) {
    return chop(needle, haystack, 0, haystack.size());
  }

  private static int chop(final int needle, final List<Integer> haystack, final int a, final int b) {
    if (a >= b) {
      return -1;
    }
    final int c = (b - a) / 2;
    final int i = a + c;
    final int x = haystack.get(i);
    if (x == needle) {
      return i;
    } else if (x < needle) {
      return chop(needle, haystack, a + c + 1, b);
    } else {
      return chop(needle, haystack, a, a + c);
    }
  }
}
