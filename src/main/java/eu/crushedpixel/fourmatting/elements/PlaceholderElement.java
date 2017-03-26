package eu.crushedpixel.fourmatting.elements;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlaceholderElement implements Element {

    private final Integer index;

    public PlaceholderElement() {
        this(null);
    }

    public PlaceholderElement(Integer index) {
        this.index = index;
    }

    public Optional<Integer> getIndex() {
        return Optional.ofNullable(index);
    }

    @Override
    public List<Element> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "PlaceholderElement{" +
                "index=" + index +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceholderElement)) return false;

        PlaceholderElement that = (PlaceholderElement) o;

        return index != null ? index.equals(that.index) : that.index == null;
    }

    @Override
    public int hashCode() {
        return index != null ? index.hashCode() : 0;
    }

    public static class Builder implements Element.Builder<PlaceholderElement> {

        private StringBuilder stringBuilder = new StringBuilder();

        public Builder append(int i) {
            stringBuilder.append(i);
            return this;
        }

        @Override
        public Element.Builder<PlaceholderElement> append(Element element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PlaceholderElement build() {
            Integer index = null;

            String indexString = stringBuilder.toString();
            if (!indexString.isEmpty()) {
                index = Integer.valueOf(indexString);
            }

            return new PlaceholderElement(index);
        }
    }
}
