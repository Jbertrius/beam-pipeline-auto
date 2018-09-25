package etai.DoFnFunctions;

import org.apache.beam.sdk.transforms.SimpleFunction;

public class FormatAsTextFn extends SimpleFunction<Object, String> {
    @Override
    public String apply( Object input) {
        return input.toString();
    }
}
