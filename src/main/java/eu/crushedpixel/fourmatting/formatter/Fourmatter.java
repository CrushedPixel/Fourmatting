package eu.crushedpixel.fourmatting.formatter;

import eu.crushedpixel.fourmatting.parser.ParsingException;

public interface Fourmatter<T> {

    /**
     * Formats a string with the given parameters.
     * @param format The format string
     * @param params The parameters
     * @return The formatted object
     * @throws ParsingException
     */
    T format(String format, T... params) throws ParsingException;

}
