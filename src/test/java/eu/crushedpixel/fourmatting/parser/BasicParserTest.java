package eu.crushedpixel.fourmatting.parser;

import eu.crushedpixel.fourmatting.elements.AlternativeElement;
import eu.crushedpixel.fourmatting.elements.Element;
import eu.crushedpixel.fourmatting.elements.MultiElement;
import eu.crushedpixel.fourmatting.elements.PlaceholderElement;
import eu.crushedpixel.fourmatting.elements.StringElement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasicParserTest {

    private static final String plainStringA = "Hello World!";
    private static final String plainStringB = "Hello \\[World\\]!";
    private static final String plainStringC = "Hello \\]World!";

    private static final String placeholderStringA = "Hello {}!";
    private static final String placeholderStringB = "{} World!";
    private static final String placeholderStringC = "Hello {0} and {1}!";
    private static final String placeholderStringD = "Hello {}, {1}, {0} and {}!";
    private static final String placeholderStringE = "{}{12}{0}";
    private static final String placeholderStringF = "{}";

    private static final String randomElementStringA = "Hello [World|Java]!";
    private static final String randomElementStringB = "Hello [World|Java|Programming]!";
    private static final String randomElementStringC = "[Hello|Good Day|Hi] World!";
    private static final String randomElementStringD = "[Hello|Good Day|Hi] [World|Java|Programming]!";
    private static final String randomElementStringE = "Hello [World]!";
    private static final String randomElementStringF = "Hello|World!";
    private static final String randomElementStringG = "[\\\\\\]]";

    private static final String mixedElementStringA = "Hello [{}|World]!";
    private static final String mixedElementStringB = "Hello [World {}|{} World]!";
    private static final String mixedElementStringC = "Hello [World|[Programming|Java]]!";

    private static final String simplificationStringA = "[]";
    private static final String simplificationStringB = "";
    private static final String simplificationStringC = "[[[]]]";
    private static final String simplificationStringD = "[|]";
    private static final String simplificationStringE = "|";
    private static final String simplificationStringF = "[a|a|a]";
    private static final String simplificationStringG = "[[[{}]]]";
    private static final String simplificationStringH = "[a|b]";
    private static final String simplificationStringI = "Hello [|]";

    private static final String invalidElementStringA = "Hello] World!";
    private static final String invalidElementStringB = "{-1}";
    private static final String invalidElementStringC = "{\\0}";
    private static final String invalidElementStringD = "[";
    private static final String invalidElementStringE = "{[]}";
    private static final String invalidElementStringF = "[}";
    private static final String invalidElementStringG = "{}}";

    @Test
    public void testPlainString() throws ParsingException {
        assertEquals(
                s("Hello World!"),
                new BasicParser(plainStringA).parse()
        );

        assertEquals(
                s("Hello [World]!"),
                new BasicParser(plainStringB).parse()
        );

        assertEquals(
                s("Hello ]World!"),
                new BasicParser(plainStringC).parse()
        );
    }

    @Test
    public void testPlaceholderString() throws ParsingException {
        assertEquals(m(
                s("Hello "),
                p(),
                s("!")
        ), new BasicParser(placeholderStringA).parse());

        assertEquals(m(
                p(),
                s(" World!")
        ), new BasicParser(placeholderStringB).parse());

        assertEquals(m(
                s("Hello "),
                p(0),
                s(" and "),
                p(1),
                s("!")
        ), new BasicParser(placeholderStringC).parse());

        assertEquals(m(
                s("Hello "),
                p(),
                s(", "),
                p(1),
                s(", "),
                p(0),
                s(" and "),
                p(),
                s("!")
        ), new BasicParser(placeholderStringD).parse());

        assertEquals(m(
                p(),
                p(12),
                p(0)
        ), new BasicParser(placeholderStringE).parse());

        assertEquals(p(), new BasicParser(placeholderStringF).parse());
    }

    @Test
    public void testRandomElementString() throws ParsingException {
        assertEquals(m(
                s("Hello "),
                r(s("World"), s("Java")),
                s("!")
        ), new BasicParser(randomElementStringA).parse());

        assertEquals(m(
                s("Hello "),
                r(s("World"), s("Java"), s("Programming")),
                s("!")
        ), new BasicParser(randomElementStringB).parse());

        assertEquals(m(
                r(s("Hello"), s("Good Day"), s("Hi")),
                s(" World!")
        ), new BasicParser(randomElementStringC).parse());

        assertEquals(m(
                r(s("Hello"), s("Good Day"), s("Hi")),
                s(" "),
                r(s("World"), s("Java"), s("Programming")),
                s("!")
        ), new BasicParser(randomElementStringD).parse());

        assertEquals(m(
                s("Hello "),
                s("World"),
                s("!")
        ), new BasicParser(randomElementStringE).parse());

        assertEquals(r(
                s("Hello"),
                s("World!")
        ), new BasicParser(randomElementStringF).parse());

        assertEquals(s("\\]"), new BasicParser(randomElementStringG).parse());
    }

    @Test
    public void testMixedElementString() throws ParsingException {
        assertEquals(m(
                s("Hello "),
                r(p(), s("World")),
                s("!")
        ), new BasicParser(mixedElementStringA).parse());

        assertEquals(m(
                s("Hello "),
                r(m(s("World "), p()), m(p(), s(" World"))),
                s("!")
        ), new BasicParser(mixedElementStringB).parse());

        assertEquals(m(
                s("Hello "),
                r(s("World"), r(s("Programming"), s("Java"))),
                s("!")
        ), new BasicParser(mixedElementStringC).parse());
    }

    @Test
    public void testSimplification() throws ParsingException {
        assertEquals(s(""), new BasicParser(simplificationStringA).parse());
        assertEquals(s(""), new BasicParser(simplificationStringB).parse());
        assertEquals(s(""), new BasicParser(simplificationStringC).parse());
        assertEquals(s(""), new BasicParser(simplificationStringD).parse());
        assertEquals(s(""), new BasicParser(simplificationStringE).parse());
        assertEquals(s("a"), new BasicParser(simplificationStringF).parse());
        assertEquals(p(), new BasicParser(simplificationStringG).parse());
        assertEquals(r(s("a"), s("b")), new BasicParser(simplificationStringH).parse());
        assertEquals(s("Hello "), new BasicParser(simplificationStringI).parse());
    }

    @Test
    public void testInvalidString() throws ParsingException {
        assertParsingException(invalidElementStringA);
        assertParsingException(invalidElementStringB);
        assertParsingException(invalidElementStringC);
        assertParsingException(invalidElementStringD);
        assertParsingException(invalidElementStringE);
        assertParsingException(invalidElementStringF);
        assertParsingException(invalidElementStringG);
    }

    private void assertParsingException(String toParse) {
        boolean caught = false;
        try {
            new BasicParser(toParse).parse();
        } catch (ParsingException e) {
            caught = true;
        }

        if (!caught) {
            throw new AssertionError(String.format("Expected ParsingException to be thrown by %s", toParse));
        }
    }

    private static StringElement s(String value) {
        return new StringElement(value);
    }

    private static PlaceholderElement p(int index) {
        return new PlaceholderElement(index);
    }

    private static PlaceholderElement p() {
        return new PlaceholderElement();
    }

    private static AlternativeElement r(Element... choices) {
        return new AlternativeElement(choices);
    }

    private static MultiElement m(Element... elements) { return new MultiElement(elements); }

}