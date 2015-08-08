package ctci.ch17;

import com.google.common.collect.Range;

import java.util.Collections;
import java.util.List;

import static com.google.common.primitives.Ints.asList;

public class Q17_8_MaxContiguousSumSequence {

  public static void main(final String... args) {
    System.out.println(maxSequence(2, -8, 3, -2, 4, -10));
  }

  private static Sequence maxSequence(final int... values) {
    if (values.length == 0) {
      return new Sequence(0, Range.open(0, 0), Collections.<Integer>emptyList());
    }
    int sum = 0;
    int start = 0;
    int maxSum = 0;
    int maxStart = 0;
    int maxEnd = 0;
    for (int i = 0; i < values.length; i++) {
      final int v = values[i];
      sum += v;
      if (sum > maxSum) {
        maxSum = sum;
        maxStart = start;
        maxEnd = i + 1;
      }
      if (sum < 0) {
        sum = 0;
        start = i + 1;
      }
    }
    return new Sequence(maxSum, Range.closedOpen(maxStart, maxEnd), asList(values).subList(maxStart, maxEnd));
  }

  private static class Sequence {

    private final int sum;
    private final Range<Integer> range;
    private final List<Integer> values;

    public Sequence(final int sum, final Range<Integer> range, final List<Integer> values) {
      this.sum = sum;
      this.range = range;
      this.values = values;
    }

    @Override
    public String toString() {
      return "Sequence{" +
             "sum=" + sum +
             ", range=" + range +
             ", values=" + values +
             '}';
    }
  }
}
