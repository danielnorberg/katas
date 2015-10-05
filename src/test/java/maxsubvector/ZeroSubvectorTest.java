package maxsubvector;

import com.google.common.collect.Range;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ZeroSubvectorTest {

  @Test
  public void testZeroSubvector() throws Exception {
    assertThat(ZeroSubvector.zeroSubvector(), is(Range.closedOpen(-1, -1)));
    assertThat(ZeroSubvector.zeroSubvector(0), is(Range.closedOpen(0, 1)));
    assertThat(ZeroSubvector.zeroSubvector(0, 1), is(Range.closedOpen(0, 1)));
    assertThat(ZeroSubvector.zeroSubvector(1, 0), is(Range.closedOpen(1, 2)));
    assertThat(ZeroSubvector.zeroSubvector(1, -1), is(Range.closedOpen(0, 2)));
    assertThat(ZeroSubvector.zeroSubvector(1, -2, 1), is(Range.closedOpen(0, 3)));
    assertThat(ZeroSubvector.zeroSubvector(2, -2, 1), is(Range.closedOpen(0, 2)));
    assertThat(ZeroSubvector.zeroSubvector(3, -2, 1, -2), is(Range.closedOpen(0, 4)));
  }
}