package eu.crushedpixel.fourmatting.formatter;

import eu.crushedpixel.fourmatting.parser.ParsingException;

public interface Fourmatter<T> {

    T format(String format, T... params) throws ParsingException;

}
