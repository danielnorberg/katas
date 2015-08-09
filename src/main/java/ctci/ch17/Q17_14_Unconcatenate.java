package ctci.ch17;

import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class Q17_14_Unconcatenate {

  private static final Set<String> DICTIONARY = ImmutableSet.of("looked", "just", "like", "her", "brother", "hit");

  public static void main(String[] args) {
    assertThat(unconcatenate("jesslookedjustliketimherbrother"),
               contains("jess", "looked", "just", "like", "tim", "her", "brother"));
  }

  private static class Result {

    private int unparsed = Integer.MAX_VALUE;
    private List<String> words;

    public Result(final int unparsed, final List<String> words) {
      this.unparsed = unparsed;
      this.words = words;
    }
  }

  private static final Map<String, Result> CACHE = new HashMap<>();

  static {
    CACHE.put("", new Result(0, Collections.<String>emptyList()));
  }

  private static List<String> unconcatenate(final String s) {
    final Result result = parse(s);
    return result.words;
  }

  private static Result parse(final String s) {
    final Result cached = CACHE.get(s);
    if (cached != null) {
      return cached;
    }

    if (DICTIONARY.contains(s)) {
      final Result r = new Result(0, singletonList(s));
      CACHE.put(s, r);
      return r;
    }

    if (s.length() == 1) {
      final Result r = new Result(1, singletonList(s));
      CACHE.put(s, r);
      return r;
    }

    int minUnparsed = s.length();
    List<String> words = singletonList(s);

    for (int i = 1; i < s.length(); i++) {
      final String suffix = s.substring(i);
      final Result suffixResult = parse(suffix);

      final String word = s.substring(0, i);
      final boolean wordUnparsed = DICTIONARY.contains(word);
      final int unparsed = wordUnparsed ? suffixResult.unparsed : suffixResult.unparsed + word.length();

      if (unparsed <= minUnparsed) {
        minUnparsed = unparsed;
        words = cat(singletonList(word), suffixResult.words);
      }
    }

    final Result result = new Result(minUnparsed, words);
    CACHE.put(s, result);

    return result;
  }

  private static List<String> cat(final List<String> a, final List<String> b) {
    final List<String> cat = new ArrayList<>(a.size() + b.size());
    cat.addAll(a);
    cat.addAll(b);
    return cat;
  }
}
