package eu.crushedpixel.fourmatting.elements;

import java.util.List;

public interface Element {

    /**
     * Returns an immutable list containing all child elements of this element.
     * @return The list
     */
    List<Element> getChildren();

    interface Builder<T extends Element> {

        Builder<T> append(Element element);

        T build();
    }

}
