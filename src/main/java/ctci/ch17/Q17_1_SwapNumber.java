package ctci.ch17;

public class Q17_1_SwapNumber {

  public static void main(final String... args) {
    int a = 17;   // 0000000010001
    int b = 4711; // 1001001100111

    System.out.printf("before: a=%d, b=%d%n", a, b);

    //   0000000010001 ^
    //   1001001100111
    // = 1001001110110
    a ^= b;

    //   1001001100111 ^
    //   1001001110110
    // = 0000000010001
    b ^= a;

    //   1001001110110
    //   0000000010001
    // = 1001001100111
    a ^= b;

    System.out.printf("after: a=%d, b=%d%n", a, b);
  }
}
