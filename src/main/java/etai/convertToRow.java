package etai;

import org.apache.beam.sdk.coders.RowCoder;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.Row;

import java.util.List;
import java.util.Arrays;


public class convertToRow extends DoFn<RequestElement, Row> {
    static Schema appSchema =
            Schema
                .builder()
                .addStringField("Immat")
                .addStringField("Pays")
                .addStringField("Algorithme")
                .addArrayField("Resultat", Schema.FieldType.STRING)
                .build();

    public static RowCoder coder = appSchema.getRowCoder();

    @ProcessElement
    public void processElement(@Element RequestElement element, OutputReceiver<Row> receiver) {

        List<String> tab = Arrays.asList(element.resultat().split(","));

        Row appRow = Row
                        .withSchema(appSchema)
                        .addValues(
                                element.immat(),
                                element.pays(),
                                element.algori(),
                                tab )
                        .build();


        receiver.output(appRow);
    }

}
