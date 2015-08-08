package ctci.ch17;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Q17_10_XmlEncoding {

  private static final int END = 0;

  private static final Map<String, Integer> TAGS = ImmutableMap.of(
      "family", 1,
      "person", 2,
      "firstName", 3,
      "lastName", 4,
      "state", 5
  );

  private static final class Element {

    public final String tag;
    public final List<Attribute> attributes;
    public final List<Element> children;
    public final String value;

    public Element(final String tag, final List<Attribute> attributes,
                   final List<Element> children, final String value) {
      this.tag = tag;
      this.attributes = attributes;
      this.children = children;
      this.value = value;
    }

    public Element(final String tag, final List<Attribute> attributes,
                   final List<Element> children) {
      this(tag, attributes, children, "");
    }

    public Element(final String value) {
      this("", Collections.<Attribute>emptyList(), Collections.emptyList(), value);
    }

    public String encode() {
      final StringBuilder s = new StringBuilder();
      encode(s);
      return s.toString();
    }

    private void encode(final StringBuilder s) {
      s.append(TAGS.get(tag)).append(' ');
      for (final Attribute attribute : attributes) {
        s.append(TAGS.get(attribute.tag)).append(' ').append(attribute.value).append(' ');
      }
      s.append(END).append(' ');
      for (final Element child : children) {
        child.encode(s);
        s.append(' ');
      }
      if (!value.isEmpty()) {
        s.append(value).append(' ');
      }
      s.append(END);
    }
  }

  private static class Attribute {

    public final String tag;
    public final String value;

    public Attribute(final String tag, final String value) {
      this.tag = tag;
      this.value = value;
    }
  }

  @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
  public static void main(String[] args) {
    final Element document = new Element("family", asList(
        new Attribute("lastName", "McDowell"),
        new Attribute("state", "CA")), asList(
        new Element("person", asList(new Attribute("firstName", "Gayle")), asList(), "Some Message")
    ));
    assertThat(document.encode(), is("1 4 McDowell 5 CA 0 2 3 Gayle 0 Some Message 0 0"));
  }

}
