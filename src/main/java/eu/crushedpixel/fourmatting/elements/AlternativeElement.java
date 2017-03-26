package eu.crushedpixel.fourmatting.elements;

import java.util.ArrayList;
import java.util.List;

public class AlternativeElement extends AbstractMultiElement {

    public AlternativeElement(Element... elements) {
        super(elements);
    }

    public AlternativeElement(List<Element> choices) {
        super(choices);
    }

    public static class Builder implements Element.Builder<AlternativeElement> {

        private List<Element> elements = new ArrayList<>();

        @Override
        public Builder append(Element element) {
            this.elements.add(element);
            return this;
        }

        @Override
        public AlternativeElement build() {
            return new AlternativeElement(elements);
        }
    }
}
