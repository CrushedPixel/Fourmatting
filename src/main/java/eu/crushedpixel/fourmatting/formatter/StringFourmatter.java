package eu.crushedpixel.fourmatting.formatter;

import java.util.function.Function;

public class StringFourmatter extends GenericFourmatter<String, StringBuilder> {

    public StringFourmatter() {
        super(StringBuilder::new, s -> s, StringBuilder::append, StringBuilder::toString);
    }

    public StringFourmatter(Function<Integer, Integer> random) {
        super(StringBuilder::new, s -> s, StringBuilder::append, random, StringBuilder::toString);
    }
}
