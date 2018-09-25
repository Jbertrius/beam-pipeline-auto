package etai.DoFnFunctions;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FilterBy extends DoFn<KV<String, KV<Row, Row>>, KV<String, KV<Row, Row>>> {
    public String param;

    private static final Logger LOG = LoggerFactory.getLogger(FilterBy.class);

    public FilterBy(String param){
        this.param = param;
    }

    @ProcessElement
    public void processElement(@Element KV<String, KV<Row, Row>> el, OutputReceiver<KV<String, KV<Row, Row>>> receiver) {

        KV<Row, Row> value = el.getValue();

        String marque = value.getKey().getString("Marque");
        List<String> resultats = value.getValue().getArray("Resultat");

        if ( marque.equals(param) && resultats.size() == 1) {
            receiver.output(el);

            LOG.info(String.format("IMMAT : %s - MARQUE: %s - MODELE : %s - PAYS : %s - VARIANTE: %s",
                    el.getKey(),
                    marque,
                    value.getKey().getString("Modele"),
                    value.getValue().getString("Pays"),
                    resultats.get(0)));
        }

    }
}
