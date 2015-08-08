package ctci.ch17;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q17_5_MasterMind {

  private static final int SLOTS = 4;

  public static void main(String[] args) {
    assertThat(hits("RGBY", "GGRR"), is(Hits.of(1, 1)));
    assertThat(hits("RGBY", "RGBY"), is(Hits.of(4, 0)));
    assertThat(hits("RGBY", "GBYR"), is(Hits.of(0, 4)));
    assertThat(hits("RRRR", "GGGG"), is(Hits.of(0, 0)));
  }

  private static Hits hits(final String actual, final String guess) {
    final boolean[] counted = new boolean[SLOTS];

    // Count full hits
    int full = 0;
    for (int i = 0; i < SLOTS; i++) {
      if (actual.charAt(i) == guess.charAt(i)) {
        counted[i] = true;
        full++;
      }
    }

    // Count pseudo-hits
    int pseudo = 0;
    for (int i = 0; i < SLOTS; i++) {
      final char c = guess.charAt(i);
      for (int j = 0; j < SLOTS; j++) {
        if (counted[j]) {
          continue;
        }
        if (actual.charAt(j) == c) {
          counted[j] = true;
          pseudo++;
        }
      }
    }

    return Hits.of(full, pseudo);
  }

  private static class Hits {

    public final int full;
    public final int pseudo;

    private Hits(final int full, final int pseudo) {
      this.full = full;
      this.pseudo = pseudo;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }

      final Hits hits = (Hits) o;

      if (full != hits.full) { return false; }
      if (pseudo != hits.pseudo) { return false; }

      return true;
    }

    @Override
    public int hashCode() {
      int result = full;
      result = 31 * result + pseudo;
      return result;
    }

    @Override
    public String toString() {
      return "Hits{" +
             "full=" + full +
             ", pseudo=" + pseudo +
             '}';
    }

    public static Hits of(final int full, final int pseudo) {
      return new Hits(full, pseudo);
    }
  }
}
