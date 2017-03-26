package eu.crushedpixel.fourmatting.parser;

import eu.crushedpixel.fourmatting.elements.PlaceholderElement;
import eu.crushedpixel.fourmatting.utils.StringIterator;

public class PlaceholderElementParser extends AbstractParser<PlaceholderElement, PlaceholderElement.Builder> {

    public PlaceholderElementParser(StringIterator iterator) {
        super(iterator, new PlaceholderElement.Builder());
    }

    @Override
    boolean parseChar(char c) throws ParsingException {
        if (c == '}') return true;

        try {
            int i = Integer.valueOf(String.valueOf(c));
            builder.append(i);
            return false;

        } catch (NumberFormatException e) {
            throw new ParsingException(iterator.getIndex(), "digit", c);
        }
    }
}
