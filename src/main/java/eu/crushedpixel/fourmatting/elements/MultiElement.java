package eu.crushedpixel.fourmatting.elements;

import java.util.ArrayList;
import java.util.List;

public class MultiElement extends AbstractMultiElement {

    public MultiElement(Element... elements) {
        super(elements);
    }

    public MultiElement(List<Element> elements) {
        super(elements);
    }

    public static class Builder implements Element.Builder<MultiElement> {

        private List<Element> elements = new ArrayList<>();

        @Override
        public Builder append(Element element) {
            this.elements.add(element);
            return this;
        }

        @Override
        public MultiElement build() {
            return new MultiElement(elements);
        }
    }
}