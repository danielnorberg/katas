package sorting;

public class BubbleSort {
  public static void main(final String[] args) {
    final int[] v = {5,3,4,1,2};
    bubbleSort(v);
    for (int i : v) {
      System.out.println(i);
    }
  }

  public static void bubbleSort(final int[] v) {
    for (int i = 0; i < v.length; i++) {
      bubble(v, i);
    }
  }

  public static void bubble(final int[] v, final int i) {
    for (int j = i; j > 0; j--) {
      if (v[j] >= v[j-1]) {
        break;
      }
      swap(v, j, j-1);
    }
  }

  public static void swap(final int[] v, final int a, final int b) {
    final int tmp = v[a];
    v[a] = v[b];
    v[b] = tmp;
  }
}