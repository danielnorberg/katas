package codekata.kata04_data_munging;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Kata04_DataMunging_Part2_SoccerLeagueTable {

  private static final Splitter SPLITTER = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings();

  public static void main(final String... args) throws IOException {
    try (final BufferedReader reader = new BufferedReader(
        new InputStreamReader(Kata04_DataMunging_Part2_SoccerLeagueTable.class.getResourceAsStream("football.dat")))) {
      final List<String> rows = reader.lines().skip(1).collect(Collectors.toList());
      String minDiffTeam = "";
      int minDiff = Integer.MAX_VALUE;
      int minDiffFor = 0;
      int minDiffAgainst = 0;
      for (final String row : rows) {
        final List<String> columns = SPLITTER.splitToList(row.trim());
        if (columns.size() != 10) {
          continue;
        }
        String team = columns.get(1);
        int f = Integer.parseInt(columns.get(6));
        int a = Integer.parseInt(columns.get(8));
        int d = Math.abs(f - a);
        if (d < minDiff) {
          minDiff = d;
          minDiffTeam = team;
          minDiffFor = f;
          minDiffAgainst = a;
        }
      }
      System.out.printf("minDiff=%d team=%s F=%d A=%d", minDiff, minDiffTeam, minDiffFor, minDiffAgainst);
    }
  }
}
