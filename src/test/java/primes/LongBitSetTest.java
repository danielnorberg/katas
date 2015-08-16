package primes;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LongBitSetTest {

  @Test
  public void testPreviousSet() {
    final LongBitSet sut = new LongBitSet(10_000_000_000L);
    sut.set(9_876_543_210L);
    sut.set(1_876_543_210L);
    assertThat(sut.previousSetBit(10_000_000_000L - 1L), is(9_876_543_210L));
    assertThat(sut.previousSetBit(9_876_543_210L - 1L), is(1_876_543_210L));
  }
}