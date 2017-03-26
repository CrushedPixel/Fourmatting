package eu.crushedpixel.fourmatting.formatter;

import eu.crushedpixel.fourmatting.elements.Element;
import eu.crushedpixel.fourmatting.elements.MultiElement;
import eu.crushedpixel.fourmatting.elements.PlaceholderElement;
import eu.crushedpixel.fourmatting.elements.AlternativeElement;
import eu.crushedpixel.fourmatting.elements.StringElement;
import eu.crushedpixel.fourmatting.parser.Parser;
import eu.crushedpixel.fourmatting.parser.ParsingException;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class GenericFourmatter<T, B> implements Fourmatter<T> {

    /**
     * Function mapping a string (as retrieved from StringElement) to {@code T}.
     */
    protected final Function<String, T> fromString;

    /**
     * Function choosing a list index for a given list length.
     * Used to determine which element of a AlternativeElement to use.
     */
    protected final Function<Integer, Integer> random;

    /**
     * Supplier creating a new builder.
     */
    protected final Supplier<B> create;

    /**
     * Function appending an element to a builder and returning that builder.
     */
    protected final BiFunction<B, T, B> append;

    /**
     * Function building the builder.
     */
    protected final Function<B, T> build;

    private int index;

    public GenericFourmatter(Supplier<B> create, Function<String, T> fromString,
                             BiFunction<B, T, B> append, Function<B, T> build) {
        this(create, fromString, append, build, new Random()::nextInt);
    }

    /**
     * @param create A supplier returning a new Builder instance
     * @param fromString A function converting a {@code String} to the formatter's type format
     * @param append A function appending an element to a builder and returning that builder
     * @param build A function returning the result of building a builder
     * @param random A function returning an index for a given count of alternatives to choose from
     */
    public GenericFourmatter(Supplier<B> create, Function<String, T> fromString,
                             BiFunction<B, T, B> append, Function<B, T> build,
                             Function<Integer, Integer> random) {
        this.create = create;
        this.fromString = fromString;
        this.append = append;
        this.random = random;
        this.build = build;
    }

    @Override
    public T format(String format, T... params) throws ParsingException {
        index = 0;

        Element e = Parser.parse(format);

        return build.apply(applyElement(e, params, create.get()));
    }

    /**
     * Returns a copy of this formatter with the specified alternative chooser
     * @param random A function returning an index for a given count of alternatives to choose from
     * @return The GenericFourmatter
     */
    public GenericFourmatter<T, B> with(Function<Integer, Integer> random) {
        return new GenericFourmatter<>(create, fromString, append, build, random);
    }

    private B applyElement(Element e, T[] params, B builder) {
        if (e instanceof MultiElement) {
            for (Element child : e.getChildren()) {
                builder = applyElement(child, params, builder);
            }
            return builder;
        }

        if (e instanceof AlternativeElement) {
            return applyElement(e.getChildren().get(random.apply(e.getChildren().size())), params, builder);
        }

        if (e instanceof StringElement) {
            return append.apply(builder, fromString.apply(((StringElement) e).getValue()));
        }

        if (e instanceof PlaceholderElement) {
            PlaceholderElement placeholderElement = (PlaceholderElement) e;
            int i;
            if (placeholderElement.getIndex().isPresent()) {
                i = placeholderElement.getIndex().get();
            } else {
                i = index++;
            }
            return append.apply(builder, params[i]);
        }

        throw new UnsupportedOperationException(String.format("Can't process element %s", e));
    }
}
