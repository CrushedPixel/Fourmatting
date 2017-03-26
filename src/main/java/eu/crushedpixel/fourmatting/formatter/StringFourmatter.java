package eu.crushedpixel.fourmatting.formatter;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class StringFourmatter extends GenericFourmatter<String, StringBuilder> {

    public StringFourmatter() {
        this(StringBuilder::new, s -> s, StringBuilder::append, StringBuilder::toString, new Random()::nextInt);
    }

    public StringFourmatter(Function<Integer, Integer> random) {
        this(StringBuilder::new, s -> s, StringBuilder::append, StringBuilder::toString, random);
    }

    private StringFourmatter(Supplier<StringBuilder> create, Function<String, String> fromString,
                     BiFunction<StringBuilder, String, StringBuilder> append, Function<StringBuilder, String> build,
                     Function<Integer, Integer> random) {
        super(create, fromString, append, build, random);
    }

    @Override
    public GenericFourmatter<String, StringBuilder> with(Function<Integer, Integer> random) {
        return new StringFourmatter(create, fromString, append, build, random);
    }
}
