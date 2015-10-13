package ctci.ch18;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Q18_2_Shuffle {

  private static class NumberedCard {
    final int number;
    final int card;

    public NumberedCard(final int number, final int card) {
      this.number = number;
      this.card = card;
    }
  }

  /**
   * Iterative random swap shuffle.
   *
   * O(n)
   */
  private static int[] knuthShuffle(final int[] cards) {
    final ThreadLocalRandom r = ThreadLocalRandom.current();
    for (int i = cards.length - 1; i > 0; i--) {
      final int j = r.nextInt(0, i + 1);
      swap(cards, i, j);
    }
    return cards;
  }

  /**
   * Iterative random swap shuffle.
   *
   * O(n)
   */
  private static int[] knuthShuffleForward(final int[] cards) {
    final ThreadLocalRandom r = ThreadLocalRandom.current();
    for (int i = 0; i < cards.length - 1; i++) {
      final int j = r.nextInt(i, cards.length);
      swap(cards, i, j);
    }
    return cards;
  }

  private static void swap(final int[] cards, final int i, final int j) {
    final int t = cards[i];
    cards[i] = cards[j];
    cards[j] = t;
  }

  /**
   * Shuffle by recursively swapping a random card
   *
   * O(n)
   */
  private static int[] recursiveSwapShuffle(final int[] cards) {
    return recursiveSwapShuffle(cards, cards.length);
  }

  private static int[] recursiveSwapShuffle(final int[] cards, final int n) {
    if (n < 2) {
      return cards;
    }
    recursiveSwapShuffle(cards, n - 1);
    final int swapCard = cards[n - 1];
    final int swapIndex = (n == 2) ? 0 : ThreadLocalRandom.current().nextInt(n - 2);
    cards[n - 1] = cards[swapIndex];
    cards[swapIndex] = swapCard;
    return cards;
  }

  /**
   * Shuffle by associating each card with a random number.
   *
   * O(nlogn)
   */
  private static int[] associationShuffle(final int[] cards) {
    final NumberedCard[] numbered = new NumberedCard[cards.length];
    for (int i = 0; i < cards.length; i++) {
      numbered[i] = new NumberedCard(rand(), cards[i]);
    }
    Arrays.sort(numbered, (a, b) -> Integer.compare(a.number, b.number));
    final int[] shuffled = new int[cards.length];
    for (int i = 0; i < cards.length; i++) {
      shuffled[i] = numbered[i].card;
    }
    return shuffled;
  }

  private static int rand() {
    return ThreadLocalRandom.current().nextInt();
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(associationShuffle(deck())));
    System.out.println(Arrays.toString(recursiveSwapShuffle(deck())));
    System.out.println(Arrays.toString(knuthShuffle(deck())));
    System.out.println(Arrays.toString(knuthShuffleForward(deck())));
  }

  private static int[] deck() {
    final int[] cards = new int[52];
    for (int i = 0; i < cards.length; i++) {
      cards[i] = i;
    }
    return cards;
  }
}
