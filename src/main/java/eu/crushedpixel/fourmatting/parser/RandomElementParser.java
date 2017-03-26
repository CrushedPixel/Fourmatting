package eu.crushedpixel.fourmatting.parser;

import eu.crushedpixel.fourmatting.elements.AlternativeElement;
import eu.crushedpixel.fourmatting.utils.StringIterator;

public class RandomElementParser extends AbstractParser<AlternativeElement, AlternativeElement.Builder> {

    private MultiElementParser multiElementParser;

    private boolean expectDelimiter;

    public RandomElementParser(StringIterator iterator) {
        this(iterator, true);
    }

    public RandomElementParser(StringIterator iterator, boolean expectDelimiter) {
        super(iterator, new AlternativeElement.Builder());
        this.expectDelimiter = expectDelimiter;
    }

    @Override
    public AlternativeElement parse() throws ParsingException {
        while (iterator.hasNext()) {
            multiElementParser = new MultiElementParser(iterator, ']');
            builder.append(multiElementParser.parse());

            // if the delimiter was reached, this AlternativeElement is over.
            // otherwise, read another MultiElement.
            if (multiElementParser.delimiterReached()) {
                if (!expectDelimiter) throw new ParsingException(iterator.getIndex(), ']');
                break;
            }
        }

        finish();
        return builder.build();
    }

    @Override
    protected void finish() throws ParsingException {
        if (expectDelimiter) {
            if (multiElementParser == null || !multiElementParser.delimiterReached()) {
                throw new ParsingException(iterator.getIndex(), "Expected closing delimiter ]");
            }
        }
        super.finish();
    }

    @Override
    boolean parseChar(char c) throws ParsingException {
        throw new UnsupportedOperationException();
    }
}
