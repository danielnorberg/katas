package codekata.kata04_data_munging;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

public class Kata04_DataMunging_Part1 {

  private static final Pattern TEMP_PATTERN = Pattern.compile("[^+-.\\d]");
  private static final Pattern DAY_PATTERN = Pattern.compile("[^\\d]");

  public static void main(final String... args) throws IOException {
    try (final InputStream input = Kata04_DataMunging_Part1.class.getResourceAsStream("weather.dat")) {
      minTempSpreadDay(input);
    }
  }

  private static void minTempSpreadDay(final InputStream input) throws IOException {
    int minDay = -1;
    float minSpread = Float.MAX_VALUE;
    float minHigh = 0;
    float minLow = 0;
    final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    // Skip header
    reader.readLine();
    while (true) {
      final String line = reader.readLine();
      if (line == null) {
        break;
      }
      String trimmed = line.trim();
      if (trimmed.isEmpty()) {
        continue;
      }
      final List<String> values = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings().splitToList(trimmed);
      final String dayString = DAY_PATTERN.matcher(values.get(0)).replaceAll("");
      if (dayString.isEmpty()) {
        continue;
      }
      int day = Integer.parseUnsignedInt(dayString);
      final String highString = TEMP_PATTERN.matcher(values.get(1)).replaceAll("");
      final String lowString = TEMP_PATTERN.matcher(values.get(2)).replaceAll("");
      float high = Float.parseFloat(highString);
      float low = Float.parseFloat(lowString);
      float spread = high - low;
      if (spread < minSpread) {
        minSpread = spread;
        minDay = day;
        minHigh = high;
        minLow = low;
      }
    }
    System.out.printf("Min spread=%.2f day=%d high=%.2f low=%.2f%n", minSpread, minDay, minHigh, minLow);
  }
}
