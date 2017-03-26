package eu.crushedpixel.fourmatting.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMultiElement implements Element {

    protected final List<Element> elements;

    public AbstractMultiElement(Element... elements) {
        this(Arrays.asList(elements));
    }

    public AbstractMultiElement(List<Element> elements) {
        this.elements = elements;
    }

    @Override
    public List<Element> getChildren() {
        return Collections.unmodifiableList(elements);
    }

    @Override
    public String toString() {
        return "AbstractMultiElement{" +
                "elements=" + elements +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractMultiElement)) return false;

        AbstractMultiElement that = (AbstractMultiElement) o;

        return elements != null ? elements.equals(that.elements) : that.elements == null;
    }

    @Override
    public int hashCode() {
        return elements != null ? elements.hashCode() : 0;
    }
}
