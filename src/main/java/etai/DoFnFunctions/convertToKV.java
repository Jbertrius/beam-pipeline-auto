package etai.DoFnFunctions;

import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class convertToKV extends DoFn<Row, KV<String, Row>> {
    public String key;
    private static final Logger LOG = LoggerFactory.getLogger(convertToKV.class);

    public convertToKV(String key) {
        this.key = key;
    }


    @ProcessElement
    public void processElement(@Element Row element, OutputReceiver<KV<String, Row>> receiver) {

        receiver.output(  KV.of(element.getString(key), element)  );
    }
}
