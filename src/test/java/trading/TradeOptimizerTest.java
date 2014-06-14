package trading;

import org.junit.Test;

import trading.Trade;
import trading.TradeOptimizer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TradeOptimizerTest {

  final TradeOptimizer sut = new TradeOptimizer();

  @Test
  public void testTrivial() {
    assertThat(sut.trade(0), is(Trade.of(0, 0, 0)));
  }

  @Test
  public void testTwoDays() {
    assertThat(sut.trade(0, 1), is(Trade.of(0, 1, 1)));
  }

  @Test
  public void testLocalMin() {
    assertThat(sut.trade(2, 4, 1, 5, 3), is(Trade.of(2, 3, 4)));
  }

  @Test
  public void testLocalMin2() {
    assertThat(sut.trade(4, 5, 3, 6, 2, 7), is(Trade.of(4, 5, 5)));
  }

  @Test
  public void testLocalMin3() {
    assertThat(sut.trade(5, 6, 4, 7, 5, 6, 1, 8, 4, 5), is(Trade.of(6, 7, 7)));
  }
}