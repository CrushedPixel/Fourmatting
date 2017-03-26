package eu.crushedpixel.fourmatting.formatter;

import eu.crushedpixel.fourmatting.parser.ParsingException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringFourmatterTest {

    private static final StringFourmatter stringFourmatter = new StringFourmatter(i -> 0);

    private static final String plainStringA = "Hello World!";
    private static final String plainStringB = "Hello \\[World\\]!";

    private static final String placeholderStringA = "Hello {}!";
    private static final String placeholderStringB = "Hello {0}!";
    private static final String placeholderStringC = "{} {}!";
    private static final String placeholderStringD = "{0} {1}!";
    private static final String placeholderStringE = "{1} {0}!";

    private static final String randomElementStringA = "Hello [Wo|Java][rld|JS]!";
    private static final String randomElementStringB = "Hello [Wo[rld|Java]|Java]!";

    @Test
    public void testPlainString() throws ParsingException {
        assertEquals("Hello World!", stringFourmatter.format(plainStringA));
        assertEquals("Hello [World]!", stringFourmatter.format(plainStringB));
    }

    @Test
    public void testPlaceholderString() throws ParsingException {
        assertEquals("Hello World!", stringFourmatter.format(placeholderStringA, "World"));
        assertEquals("Hello World!", stringFourmatter.format(placeholderStringB, "World"));
        assertEquals("Hello World!", stringFourmatter.format(placeholderStringC, "Hello", "World"));
        assertEquals("Hello World!", stringFourmatter.format(placeholderStringD, "Hello", "World"));
        assertEquals("Hello World!", stringFourmatter.format(placeholderStringE, "World", "Hello"));
    }

    @Test
    public void testRandomElementString() throws ParsingException {
        assertEquals("Hello World!", stringFourmatter.format(randomElementStringA));
        assertEquals("Hello World!", stringFourmatter.format(randomElementStringB));
    }

}
