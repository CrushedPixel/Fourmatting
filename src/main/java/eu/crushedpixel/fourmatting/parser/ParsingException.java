package eu.crushedpixel.fourmatting.parser;

public class ParsingException extends Exception {

    public ParsingException(int index, String expected, char actual) {
        this(index, String.format("Expected %s, got \"%s\"", expected, actual));
    }

    public ParsingException(int index, char actual) {
        this(index, String.format("Unexpected token: \"%s\"", actual));
    }

    public ParsingException(int index, String message) {
        super(String.format("%s at column %d", message, index));
    }
}
