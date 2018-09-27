package etai.DoFnFunctions;

import etai.Elements.CompareElement;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class FilterBy extends DoFn<KV<String, KV<Row, Row>>, KV<Integer, CompareElement>> {
    public String param;


    public FilterBy(String param){
        this.param = param;
    }

    @ProcessElement
    public void processElement(@Element KV<String, KV<Row, Row>> el, OutputReceiver<KV<Integer, CompareElement>> receiver) {

        KV<Row, Row> value = el.getValue();
        Row ngcEl = value.getKey();
        Row reqEl = value.getValue();

        String marque = Objects.requireNonNull(value.getKey()).getString("Marque");
        List<String> resultats = reqEl.getArray("Resultat");

        if ( marque.equals(param) && resultats.size() == 1) {

            String variante = resultats.get(0);

            receiver.output(KV.of( Integer.parseInt(variante),
                    CompareElement.create(
                    ngcEl.getString("Marque"),
                    ngcEl.getString("Immat"),
                    Integer.parseInt(variante),
                    ngcEl.getString("Modele"),

                    Integer.parseInt(ngcEl.getString("Cylindree")),
                    Integer.parseInt(ngcEl.getString("NbVitesse")),
                    Integer.parseInt(ngcEl.getString("NbPortes")),
                    Integer.parseInt(ngcEl.getString("PuissanceKW")),

                    ngcEl.getString("Vin")
                    )));

        }

    }
}
