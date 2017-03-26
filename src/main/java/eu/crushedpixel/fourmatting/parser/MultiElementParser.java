package eu.crushedpixel.fourmatting.parser;

import eu.crushedpixel.fourmatting.elements.MultiElement;
import eu.crushedpixel.fourmatting.elements.StringElement;
import eu.crushedpixel.fourmatting.utils.StringIterator;

public class MultiElementParser extends AbstractParser<MultiElement, MultiElement.Builder> {

    private boolean escaped;

    private StringElement.Builder stringElementBuilder;

    private final char delimiter;
    private boolean delimiterReached;

    public MultiElementParser(StringIterator iterator, char delimiter) {
        super(iterator, new MultiElement.Builder());
        this.delimiter = delimiter;
    }

    @Override
    boolean parseChar(char c) throws ParsingException {
        if (!escaped) {
            if (c == '\\') {
                escaped = true;
                return false;
            }

            if (c == '{') {
                finishStringElement();
                builder.append(new PlaceholderElementParser(iterator).parse());
                return false;
            }

            if (c == '[') {
                finishStringElement();
                builder.append(new RandomElementParser(iterator).parse());
                return false;
            }

            if (c == '|') {
                finishStringElement();
                return true;
            }

            if (c == delimiter) {
                finishStringElement();
                delimiterReached = true;
                return true;
            }

            // these should only be encountered by their respective parsers
            if (c == ']' || c == '}') {
                throw new ParsingException(iterator.getIndex(), c);
            }
        } else {
            escaped = false;
        }

        if (stringElementBuilder == null) {
            stringElementBuilder = new StringElement.Builder();
        }

        stringElementBuilder.append(c);
        return false;
    }

    @Override
    protected void finish() throws ParsingException {
        if (escaped) throw new ParsingException(iterator.getIndex(), "Escape character \"\\\" was not resolved");
        finishStringElement();
        super.finish();
    }

    private void finishStringElement() {
        if (stringElementBuilder != null) {
            builder.append(stringElementBuilder.build());
            stringElementBuilder = null;
        }
    }

    public boolean delimiterReached() {
        return delimiterReached;
    }
}
