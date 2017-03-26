package eu.crushedpixel.fourmatting.parser;

import eu.crushedpixel.fourmatting.elements.AbstractMultiElement;
import eu.crushedpixel.fourmatting.elements.AlternativeElement;
import eu.crushedpixel.fourmatting.elements.Element;
import eu.crushedpixel.fourmatting.elements.MultiElement;
import eu.crushedpixel.fourmatting.elements.StringElement;
import eu.crushedpixel.fourmatting.utils.StringIterator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BasicParser implements Parser<Element> {

    private final String toParse;

    public BasicParser(String toParse) {
        this.toParse = toParse;
    }

    @Override
    public Element parse() throws ParsingException {
        StringIterator iterator = new StringIterator(toParse);
        AlternativeElement element = new RandomElementParser(iterator, false).parse();
        Element simplified = simplify(element);
        if (simplified != null) return simplified;
        return new StringElement("");
    }

    private Element simplify(Element element) {
        if (element instanceof StringElement) {
            if (((StringElement) element).getValue().isEmpty()) return null;
        }

        if (element instanceof AbstractMultiElement) {
            List<Element> children = element.getChildren().stream()
                    .map(this::simplify)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // if all children of AlternativeElement are equal, simplify them to one Element
            if (element instanceof AlternativeElement && children.size() > 1) {
                Element first = children.get(0);

                boolean equal = true;
                for (Element child : children) {
                    if (!first.equals(child)) {
                        equal = false;
                        break;
                    }
                }

                if (equal) {
                    return first;
                }
            }

            if (children.isEmpty()) return null;
            if (children.size() == 1) return children.get(0);

            if (element instanceof AlternativeElement) {
                return new AlternativeElement(children);
            } else if (element instanceof MultiElement) {
                return new MultiElement(children);
            } else {
                throw new RuntimeException();
            }
        }

        return element;
    }

}
