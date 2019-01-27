package techdevguide.advanced.q01_compress_decompression;

public class CompressDecompression {

  private final String s;
  private int i;
  private char c;

  public CompressDecompression(final String s) {
    this.s = s;
  }

  public static String decompress(String s) {
    return new CompressDecompression(s).decompress();
  }

  private String decompress() {
    var b = new StringBuilder();
    while (true) {
      if (i >= s.length()) {
        return b.toString();
      }
      // End of compressed string?
      if (s.charAt(i) == ']') {
        i++;
        return b.toString();
      }
      // Read uncompressed letters
      while (Character.isLetter(c = s.charAt(i))) {
        b.append(c);
        i++;
        if (i >= s.length()) {
          return b.toString();
        }
      }
      // Read compressed string
      if (Character.isDigit(c)) {
        var number = readNumber();
        assert s.charAt(i) == '[';
        i++;
        assert i < s.length();
        var subString = decompress();
        for (int j = 0; j < number; j++) {
          b.append(subString);
        }
      }
    }
  }

  private int readNumber() {
    var start = i;
    while (Character.isDigit(s.charAt(i))) {
      i++;
    }
    return Integer.parseInt(s, start, i, 10);
  }
}
