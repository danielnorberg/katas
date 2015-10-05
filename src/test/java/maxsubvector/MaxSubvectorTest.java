package maxsubvector;

import com.google.common.collect.Range;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MaxSubvectorTest {

  @Test
  public void testMaxSubvector() throws Exception {
    assertThat(MaxSubvector.maxSubvector(), is(Range.closedOpen(-1, -1)));
    assertThat(MaxSubvector.maxSubvector(1), is(Range.closedOpen(0, 1)));
    assertThat(MaxSubvector.maxSubvector(1, 2), is(Range.closedOpen(0, 2)));
    assertThat(MaxSubvector.maxSubvector(-1, 2), is(Range.closedOpen(1, 2)));
    assertThat(MaxSubvector.maxSubvector(-1, 2, 3), is(Range.closedOpen(1, 3)));
    assertThat(MaxSubvector.maxSubvector(-1, 2, 3, -6, 7, 8, -1), is(Range.closedOpen(4, 6)));
  }
}