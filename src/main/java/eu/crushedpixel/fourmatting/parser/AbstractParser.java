package eu.crushedpixel.fourmatting.parser;

import eu.crushedpixel.fourmatting.elements.Element;
import eu.crushedpixel.fourmatting.utils.StringIterator;

public abstract class AbstractParser<E extends Element, B extends Element.Builder<E>> implements Parser<E> {

    protected final StringIterator iterator;
    protected final B builder;

    public AbstractParser(StringIterator iterator, B builder) {
        this.iterator = iterator;
        this.builder = builder;
    }

    @Override
    public E parse() throws ParsingException {
        while (iterator.hasNext()) {
            if (parseChar(iterator.next())) break;
        }

        finish();
        return builder.build();
    }

    protected void finish() throws ParsingException {}

    /**
     * Parses a character.
     * @param c The character to parse
     * @return {@code true} if the end of the element was reached, {@code false} otherwise
     */
    abstract boolean parseChar(char c) throws ParsingException;

}
