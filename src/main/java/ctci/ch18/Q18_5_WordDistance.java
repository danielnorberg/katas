package ctci.ch18;

import com.google.auto.value.AutoValue;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ctci.ch18.Q18_5_WordDistance.Pair.pair;
import static java.lang.Math.abs;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q18_5_WordDistance {

  public static void main(String[] args) {
    assertThat(minDistIndexed("A X B C X D Y E", "X", "Y"), is(pair(4, 6)));
    assertThat(minDistOneOff("A X B C X D Y E", "X", "Y"), is(pair(4, 6)));
  }

  private static Pair minDistOneOff(final String haystack, final String x, final String y) {
    return minDistOneOff(Splitter.on(CharMatcher.WHITESPACE).splitToList(haystack), x, y);
  }

  private static Pair minDistOneOff(final List<String> haystack, final String x, final String y) {
    int minDist = Integer.MAX_VALUE;
    int lastX = -1;
    int lastY = -1;
    int minX = -1;
    int minY = -1;
    for (int i = 0; i < haystack.size(); i++) {
      final String w = haystack.get(i);
      boolean hit = false;
      if (x.equals(w)) {
        lastX = i;
        hit = true;
      } else if (y.equals(w)) {
        lastY = i;
        hit = true;
      }
      if (hit && lastX != -1 && lastY != -1) {
        final int dist = Math.abs(lastX - lastY);
        if (dist < minDist) {
          minDist = dist;
          minX = lastX;
          minY = lastY;
        }
      }
    }
    if (minX == -1 || minY == -1) {
      return null;
    }
    return pair(minX, minY);
  }

  private static Pair minDistIndexed(final String haystack, final String x, final String y) {
    return minDistIndexed(Splitter.on(CharMatcher.WHITESPACE).splitToList(haystack), x, y);
  }

  private static Pair minDistIndexed(final List<String> haystack, final String x, final String y) {
    final Map<String, List<Integer>> index = index(haystack);
    return minDistIndexed(index, x, y);

  }

  private static Pair minDistIndexed(final Map<String, List<Integer>> index, final String x, final String y) {
    final List<Integer> xix = index.get(x);
    final List<Integer> yix = index.get(y);
    if (xix == null || yix == null) {
      return null;
    }
    int xi = 0;
    int yi = 0;
    int xp = xix.get(xi);
    int yp = yix.get(yi);
    int minXp = xp;
    int minYp = yp;
    int minDist = abs(xp - yp);
    while (true) {
      if (xi > yi) {
        yi++;
        if (yi >= yix.size()) {
          break;
        }
        yp = yix.get(yi);
      } else {
        xi++;
        if (xi >= xix.size()) {
          break;
        }
        xp = xix.get(xi);
      }
      final int dist = abs(xp - yp);
      if (dist < minDist) {
        minDist = dist;
        minXp = xp;
        minYp = yp;
      }
    }
    return pair(minXp, minYp);
  }

  private static Map<String, List<Integer>> index(final List<String> haystack) {
    final Map<String, List<Integer>> index = new HashMap<>();
    for (int i = 0; i < haystack.size(); i++) {
      final String s = haystack.get(i);
      index.computeIfAbsent(s, k -> new ArrayList<>()).add(i);
    }
    return index;
  }

  @AutoValue
  public abstract static class Pair {

    abstract int a();

    abstract int b();

    public static Pair pair(final int a, final int b) {
      return new AutoValue_Q18_5_WordDistance_Pair(a, b);
    }
  }
}
