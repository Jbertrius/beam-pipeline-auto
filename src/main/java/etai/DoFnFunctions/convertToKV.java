package etai.DoFnFunctions;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.Row;

public class convertToKV extends DoFn<Row, KV<String, Row>> {
    public String key;

    public convertToKV(String key) {
        this.key = key;
    }

    @ProcessElement
    public void processElement(@Element Row element, OutputReceiver<KV<String, Row>> receiver) {
        receiver.output(  KV.of(element.getString(key), element)  );
    }
}
