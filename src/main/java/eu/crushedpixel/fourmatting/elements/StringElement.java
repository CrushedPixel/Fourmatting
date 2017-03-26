package eu.crushedpixel.fourmatting.elements;

import java.util.Collections;
import java.util.List;

public class StringElement implements Element {

    private final String value;

    public StringElement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StringElement{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringElement)) return false;

        StringElement that = (StringElement) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public List<Element> getChildren() {
        return Collections.emptyList();
    }

    public static class Builder implements Element.Builder<StringElement> {

        private StringBuilder stringBuilder = new StringBuilder();

        public Builder append(char c) {
            stringBuilder.append(c);
            return this;
        }

        @Override
        public Element.Builder<StringElement> append(Element element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public StringElement build() {
            return new StringElement(stringBuilder.toString());
        }

    }
}
