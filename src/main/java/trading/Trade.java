package trading;

import com.google.common.base.Objects;

public class Trade {

  private final int buy;
  private final int sell;
  private final int profit;

  public Trade(final int buy, final int sell, final int profit) {
    this.buy = buy;
    this.sell = sell;
    this.profit = profit;
  }

  public int buy() {
    return buy;
  }

  public int sell() {
    return sell;
  }

  public int profit() {
    return profit;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Trade trade = (Trade) o;

    if (buy != trade.buy) {
      return false;
    }
    if (profit != trade.profit) {
      return false;
    }
    if (sell != trade.sell) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = buy;
    result = 31 * result + sell;
    result = 31 * result + profit;
    return result;
  }

  public static Trade of(final int buy, final int sell, final int profit) {
    return new Trade(buy, sell, profit);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("buy", buy)
        .add("sell", sell)
        .add("profit", profit)
        .toString();
  }
}
