package eu.crushedpixel.fourmatting.utils;

import java.util.Iterator;

public class StringIterator implements Iterator<Character> {

    private final String string;
    private int index = 0;

    public StringIterator(String string) {
        this.string = string;
    }

    @Override
    public boolean hasNext() {
        return index < string.length();
    }

    @Override
    public Character next() {
        return string.charAt(index++);
    }

    public int getIndex() {
        return index;
    }
}