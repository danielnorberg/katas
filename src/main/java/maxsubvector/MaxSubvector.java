package maxsubvector;

import com.google.common.collect.Range;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
public class MaxSubvector {

  @Param({"100", "1000", "10000", "1000000"})
  private int n;

  private int[] input;

  @Setup
  public void setup() {
    input = ThreadLocalRandom.current().ints(n).toArray();
  }

  public static void main(final String... args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(".*" + MaxSubvector.class.getSimpleName() + ".*")
        .forks(1)
        .warmupIterations(3)
        .measurementIterations(2)
        .build();
    new Runner(opt).run();
  }

  @Benchmark
  public Range<Integer> benchmark() {
    return maxSubvector(input);
  }

  public static Range<Integer> maxSubvector(final int... input) {
    int maxStart = -1;
    int maxEnd = -1;
    int maxSum = 0;
    int start = 0;
    int sum = 0;
    for (int i = 0; i < input.length; i++) {
      sum += input[i];
      if (sum < 0) {
        sum = 0;
        start = i + 1;
      }
      if (sum > maxSum) {
        maxStart = start;
        maxEnd = i + 1;
        maxSum = sum;
      }
    }
    return Range.closedOpen(maxStart, maxEnd);
  }
}
