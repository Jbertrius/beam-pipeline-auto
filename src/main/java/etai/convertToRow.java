package etai;

import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.Row;


public class convertToRow extends DoFn<RequestElement, Row> {
    Schema appSchema =
            Schema
                .builder()
                .addInt32Field("Id")
                .addStringField("Immat")
                .addStringField("Pays")
                .addStringField("Algorithme")
                .addArrayField("Resultat", Schema.FieldType.STRING)
                .build();

    @ProcessElement
    public void processElement(@Element RequestElement element, OutputReceiver<Row> receiver) {

        String tab[] = element.resultat().split(",");
        Row appRow = Row
                        .withSchema(appSchema)
                        .addValues(
                                element.id(),
                                element.immat(),
                                element.pays(),
                                tab )
                        .build();

        receiver.output(appRow);
    }

}
