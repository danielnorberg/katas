package ctci.ch1;

import static org.junit.Assert.assertArrayEquals;

public class Q1_7_ZeroColRows {

  public static void main(final String... args) {
    // 0 1 1 1    0 0 0 0
    // 1 1 1 1 -> 0 1 0 1
    // 1 1 0 1    0 0 0 0
    // 1 1 1 1    0 1 0 1

    final int[][] matrix = {
        {0, 1, 1, 1},
        {1, 1, 1, 1},
        {1, 1, 0, 1},
        {1, 1, 1, 1},
    };

    final int[][] expected = {
        {0, 0, 0, 0},
        {0, 1, 0, 1},
        {0, 0, 0, 0},
        {0, 1, 0, 1},
    };

    zeroColRows(matrix);

    assertArrayEquals(matrix, expected);
  }

  private static void zeroColRows(final int[][] matrix) {
    final int n = matrix.length;
    final int m = matrix[0].length;
    final boolean[] visitedRows = new boolean[n];
    final boolean[] visitedCols = new boolean[m];
    for (int i = 0; i < n; i++) {
      if (visitedRows[i]) {
        continue;
      }
      for (int j = 0; j < m; j++) {
        if (visitedCols[j]) {
          continue;
        }
        if (matrix[i][j] == 0) {
          visitedRows[i] = true;
          visitedCols[j] = true;
          for (int k = 0; k < n; k++) {
            matrix[k][j] = 0;
          }
          for (int k = 0; k < m; k++) {
            matrix[i][k] = 0;
          }
          break;
        }
      }
    }
  }
}
