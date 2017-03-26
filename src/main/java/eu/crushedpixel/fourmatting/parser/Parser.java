package eu.crushedpixel.fourmatting.parser;

import eu.crushedpixel.fourmatting.elements.Element;

public interface Parser<E extends Element> {

    E parse() throws ParsingException;

    public static Element parse(String toParse) throws ParsingException {
        return new BasicParser(toParse).parse();
    }
}
