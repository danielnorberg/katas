package storecredit;

import com.google.common.base.Splitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

/**
 * https://code.google.com/codejam/contest/351101/dashboard#s=p0
 */
import static com.google.common.base.CharMatcher.WHITESPACE;

public class StoreCredit {

  public static final URL LARGE_PRACTICE = StoreCredit.class.getResource("A-large-practice.in");
  public static final URL SMALL_PRACTICE = StoreCredit.class.getResource("A-small-practice.in");

  private static class Case {
    private final int credit;
    private final int[] items;

    public Case(final int credit, final int[] items) {
      this.credit = credit;
      this.items = items;
    }

    @Override
    public String toString() {
      return "Case{" +
             "credit=" + credit +
             ", items=" + Arrays.toString(items) +
             '}';
    }
  }

  public static void main(final String... args) throws IOException {
    final BufferedReader reader = new BufferedReader(new InputStreamReader(SMALL_PRACTICE.openStream()));
    final int cases = Integer.valueOf(reader.readLine());
    for (int i = 0; i < cases; i++) {
      final Case c = readCase(reader);
      solve(i, c);
    }
  }

  private static void solve(final int x, final Case c) {
    for (int i = 0; i < c.items.length; i++) {
      for (int j = i + 1; j < c.items.length; j++) {
        if (j == i) {
          continue;
        }
        final int sum = c.items[i] + c.items[j];
        if (sum == c.credit) {
          System.out.println("Case #" + (x + 1) + ": " + (i + 1) + " " + (j + 1));
          return;
        }
      }
    }
    throw new AssertionError();
  }

  private static Case readCase(final BufferedReader reader) throws IOException {
    final int credit = Integer.valueOf(reader.readLine());
    final int n = Integer.valueOf(reader.readLine());
    final int[] items = new int[n];
    int i = 0;
    final Iterable<String> tokens = Splitter.on(WHITESPACE).split(reader.readLine());
    for (final String token : tokens) {
      items[i++] = Integer.valueOf(token);
    }
    if (i != n) {
      throw new AssertionError(i + " != " + n);
    }
    return new Case(credit, items);
  }

}
