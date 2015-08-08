package ctci.ch17;

public class Q17_7_IntegerEnglishDescription {

  private static final String[] NUMBERS = {
      "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
      "Ten", "Eleven", "Twelwe", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
  };

  private static final String[] TENS = {
      "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
  };

  private static final String[] THOUSANDS = {
      "Thousand", "Million", "Billion", "Trillion", "Quadrillion", "Quintillion"
  };

  public static void main(final String... args) {
    System.out.println(description(1234));
    System.out.println(description(1234567));
    System.out.println(description(10_000_007));
    System.out.println(description(1));
    System.out.println(description(-1));
    System.out.println(description(-123456789));
  }

  private static String description(long i) {
    if (i == 0) {
      return "Zero";
    }
    final StringBuilder s = new StringBuilder();
    if (i < 0) {
      s.append("Minus");
      i = -i;
    }
    thousand(i, s, 0);
    return s.toString();
  }

  private static void thousand(final long i, final StringBuilder s, final int thousand) {
    if (i == 0) {
      return;
    }
    thousand(i / 1000, s, thousand + 1);
    final long t = (i / 1000) % 1000;
    if (t != 0) {
      s.append(' ').append(THOUSANDS[thousand]);
    }

    final int h = (int) ((i / 100) % 10);
    if (h != 0) {
      number(s, h);
      s.append(" Hundred");
    }

    final int n = (int) (i % 100);
    if (n > 0) {
      number(s, n);
    }
  }

  private static void number(final StringBuilder s, final int n) {
    if (s.length() > 0) {
      s.append(' ');
    }
    if (n < 20) {
      s.append(NUMBERS[n]);
    } else {
      s.append(TENS[n / 10]).append(' ').append(NUMBERS[n % 10]);
    }
  }
}
