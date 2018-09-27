package etai.DoFnFunctions;

import etai.Elements.BveElement;
import etai.Elements.RequestElement;
import etai.Elements.VehNgcElement;
import org.apache.beam.sdk.coders.RowCoder;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.Row;

import java.util.List;
import java.util.Arrays;


public class convertToRow extends DoFn<Object, Row> {


    static Schema requestSchema = Schema
                                        .builder()
                                        .addStringField("Immat")
                                        .addStringField("Pays")
                                        .addStringField("Algorithme")
                                        .addArrayField("Resultat", Schema.FieldType.STRING)
                                        .build();

    public static RowCoder requestCoder = requestSchema.getRowCoder();

    static Schema vehNgcSchema = Schema
                                    .builder()
                                    .addStringField("Immat")
                                    .addStringField("Marque")
                                    .addStringField("Modele")
                                    .addStringField("Vin")
                                    .addStringField("Version")
                                    .addStringField("NbVitesse")
                                    .addStringField("Energie")
                                    .addStringField("CodeMoteur")
                                    .addStringField("Cylindree")
                                    .addStringField("Carosserie")
                                    .addStringField("TypeBoiteVitesse")
                                    .addStringField("Longueur")
                                    .addStringField("Largeur")
                                    .addStringField("Hauteur")
                                    .addStringField("NbPlaces")
                                    .addStringField("NbCylindres")
                                    .addStringField("PuissanceKW")
                                    .addStringField("PuissanceFISC")
                                    .addStringField("NbPortes")
                                    .addStringField("GenreCV")
                                    .build();

    public static RowCoder vehNgcCoder = vehNgcSchema.getRowCoder();


    static Schema bveSchema = Schema
            .builder()
            .addInt32Field("Id")
            .addStringField("Marque")
            .addStringField("Modele")
            .addStringField("Modelegen")
            .addStringField("Generation")
            .addStringField("Alimentation")
            .addFloatField("CapaciteCarter")
            .addStringField("Carosserie")
            .addStringField("Chassis")
            .addStringField("CodeConduite")
            .addInt32Field("CylindreeCm3")
            .addFloatField("CylindreeLit")
            .addInt32Field("NbCylindres")
            .addStringField("Energie")
            .addInt32Field("Phase")
            .addInt32Field("NbPortes")
            .addInt32Field("PuissanceKW")
            .addInt32Field("PuissanceCOM")
            .addStringField("TypeMoteur")
            .addStringField("TypeBoiteVitesse")
            .addStringField("BoiteVitesse")
            .addInt32Field("NbVitesse")
            .addStringField("TypeVehicule")
            .addStringField("Injection")
            .build();

    public static RowCoder bveCoder = bveSchema.getRowCoder();

    @ProcessElement
    public void processElement(@Element Object element, OutputReceiver<Row> receiver) {


        if (element instanceof RequestElement) {
            RequestElement el = (RequestElement) element;
            List<String> tab = Arrays.asList(el.resultat().split(","));

            if ( tab.size() == 1 ) {
                Row appRow = Row
                        .withSchema(requestSchema)
                        .addValues(
                                el.immat(),
                                el.pays(),
                                el.algori(),
                                tab )
                        .build();

                receiver.output(appRow);
            }

        } else if ( element instanceof VehNgcElement) {
            VehNgcElement el = (VehNgcElement) element;

            Row appRow = Row
                    .withSchema(vehNgcSchema)
                    .addValues(
                            el.immat(),
                            el.marque(),
                            el.modele(),
                            el.vin(),
                            el.version(),
                            el.nbvitesse(),
                            el.energie(),
                            el.codemoteur(),
                            el.cylindree(),
                            el.carrosserie(),
                            el.tpboitevit(),
                            el.longueur(),
                            el.largeur(),
                            el.hauteur(),
                            el.nbplass(),
                            el.nbcylind(),
                            el.puiskw(),
                            el.puisfisc(),
                            el.nbportes(),
                            el.genrev()
                             )
                    .build();

            receiver.output(appRow);
        } else if ( element instanceof BveElement) {
            BveElement el = (BveElement) element;

            Row appRow = Row
                    .withSchema(bveSchema)
                    .addValues(
                            el.Id(),
                            el.marque(),
                            el.modele(),
                            el.modelegen(),
                            el.generation(),
                            el.alimentation(),
                            el.capacitecarter(),
                            el.carrosserie(),
                            el.chassis(),
                            el.codeconduite(),
                            el.cylindreecm3(),
                            el.cylindreelit(),
                            el.cylindresnbr(),
                            el.energie(),
                            el.phase(),
                            el.portesnbr(),
                            el.puissancekw(),
                            el.puissancecom(),
                            el.typemoteur(),
                            el.typeboitevitesses(),
                            el.vitessesbte(),
                            el.vitessesnbr(),
                            el.typevehicule(),
                            el.injection()
                    )
                    .build();

            receiver.output(appRow);
        }

    }

}
