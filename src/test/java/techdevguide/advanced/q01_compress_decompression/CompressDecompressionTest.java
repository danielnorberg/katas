package techdevguide.advanced.q01_compress_decompression;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CompressDecompressionTest {

  @Test
  public void decompress0() {
    assertThat(CompressDecompression.decompress("aa"), is("aa"));
    assertThat(CompressDecompression.decompress("3[abc]4[ab]c"), is("abcabcabcababababc"));
    assertThat(CompressDecompression.decompress("10[a]"), is("aaaaaaaaaa"));
    assertThat(CompressDecompression.decompress("2[3[a]b]"), is("aaabaaab"));
  }
}