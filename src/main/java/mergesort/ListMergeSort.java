package mergesort;

import java.util.List;
import java.util.ArrayList;
import static java.util.Arrays.asList;

public class ListMergeSort {
  public static void main(final String[] args) {
    final List<Integer> v = asList(5,2,4,3,1);
    final List<Integer> sorted = mergeSort(v);
    System.out.println(sorted);
  }

  public static List<Integer> mergeSort(final List<Integer> v) {
    if (v.size() <= 1) {
      return v;
    }
    final int p = v.size() / 2;
    final List<Integer> left = mergeSort(v.subList(0, p));
    final List<Integer> right = mergeSort(v.subList(p, v.size()));
    final List<Integer> sorted = new ArrayList<Integer>(v.size());
    int i = 0;
    int j = 0;
    while (true) {
      if (i < left.size() && j < right.size()) {
        final int l = left.get(i);
        final int r = right.get(j);
        if (l < r) {
          sorted.add(l);
          i++;
        } else {
          sorted.add(r);
          j++;
        }
      } else if (i < left.size()) {
        sorted.add(left.get(i));
        i++;
      } else if (j < right.size()) {
        sorted.add(right.get(j));
        j++;
      } else {
        break;
      }
    }
    return sorted;
  }
}



