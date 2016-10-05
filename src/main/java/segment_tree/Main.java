package segment_tree;

import java.util.Random;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Main {

  public static void main(final String... args) {
    assertThat(SegmentTree.of().min(), is(Integer.MAX_VALUE));
    assertThat(SegmentTree.of(1, 2).min(), is(1));
    assertThat(SegmentTree.of(2, 1).min(), is(1));
    assertThat(SegmentTree.of(2, -1).min(), is(-1));
    assertThat(SegmentTree.of(1, 2, 3).min(), is(1));
    assertThat(SegmentTree.of(3, 1, 2).min(), is(1));
    assertThat(SegmentTree.of(3, 1, 4, 2).min(), is(1));
    assertThat(SegmentTree.of(3, 1, 4, 2).min(2, 3), is(4));
    assertThat(SegmentTree.of(3, 1, 4, 2).min(2, 4), is(2));

    final Random r = new Random(17);
    final int n = 17;
    int[] values = new int[n];
    for (int i = 0; i < n; i++) {
      values[i] = r.nextInt();
    }
    SegmentTree segmentTree = SegmentTree.of(values);
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j <= n; j++) {
        int expectedMin = IntStream.of(values).skip(i).limit(j - i).min().getAsInt();
        assertThat(segmentTree.min(i, j), is(expectedMin));
      }
    }
  }
}
