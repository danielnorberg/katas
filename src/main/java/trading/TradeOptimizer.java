package trading;

import static com.google.common.base.Preconditions.checkArgument;

public class TradeOptimizer {

  public Trade trade(final int... prices) {
    checkArgument(prices.length > 0);
    int minT = 0, min = prices[0];
    int buy = 0, sell = 0, best = 0;
    for (int i = 0; i < prices.length; i++) {
      final int p = prices[i];
      if (p < min) {
        min = p;
        minT = i;
      }
      int profit = p - min;
      if (profit > best) {
        buy = minT;
        sell = i;
        best = profit;
      }
    }
    final int profit = prices[sell] - prices[buy];
    return Trade.of(buy, sell, profit);
  }
}
