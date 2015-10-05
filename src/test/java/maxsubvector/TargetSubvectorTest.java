package maxsubvector;

import com.google.common.collect.Range;

import org.junit.Test;

import static maxsubvector.TargetSubvector.targetSubvector;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TargetSubvectorTest {

  @Test
  public void testTargetSubvectorZero() throws Exception {
    assertThat(targetSubvector(0), is(Range.closedOpen(-1, -1)));
    assertThat(targetSubvector(0, 0), is(Range.closedOpen(0, 1)));
    assertThat(targetSubvector(0, 0, 1), is(Range.closedOpen(0, 1)));
    assertThat(targetSubvector(0, 1, 0), is(Range.closedOpen(1, 2)));
    assertThat(targetSubvector(0, 1, -1), is(Range.closedOpen(0, 2)));
    assertThat(targetSubvector(0, 1, -2, 1), is(Range.closedOpen(0, 3)));
    assertThat(targetSubvector(0, 2, -2, 1), is(Range.closedOpen(0, 2)));
    assertThat(targetSubvector(0, 3, -2, 1, -2), is(Range.closedOpen(0, 4)));
  }

  @Test
  public void testTargetSubvectorNonZero() throws Exception {
    assertThat(targetSubvector(1), is(Range.closedOpen(-1, -1)));
    assertThat(targetSubvector(1, 1), is(Range.closedOpen(0, 1)));
    assertThat(targetSubvector(1, 1, 2), is(Range.closedOpen(0, 1)));
    assertThat(targetSubvector(1, 2, -1), is(Range.closedOpen(0, 2)));
    assertThat(targetSubvector(1, -5, 3, -0.5, -1, -0.5, 6), is(Range.closedOpen(1, 5)));
  }
}