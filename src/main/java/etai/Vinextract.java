package etai;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.coders.*;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.io.jdbc.*;
import org.apache.beam.sdk.transforms.Distinct;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.extensions.sql.SqlTransform;


import org.apache.beam.sdk.transforms.SimpleFunction;

import javax.annotation.Nullable;

public class Vinextract {


    public static void main(String[] args) {

        /* Get Pipeline options */
        VinextractOptions options =
                PipelineOptionsFactory.fromArgs(args)
                        .withValidation()
                        .as(VinextractOptions.class);

        /* Queries to get Data from Database */
        String queryReq = "select a.id, a.immat, a.pays, a.algori, replace(b.resultat, ' ', ',')  " +
                "from requetes a join resultats b on a.resident_fk = b.id where length(b.resultat) > 0 and a.pays = '" + options.getPays() +  "' LIMIT 1000";

        String queryBVE = "Select c.id, a.nom,  b.gamme,  b.nom, b.serie, e.description as alimentation,  c.capacitecarter,  " +
                "f.description as carrosserie,  c.chassis,  c.coteconduite,  c.cylindreecm3,  c.cylindreelit,  c.cylindresnbr,  c.energie,  c.phase,  " +
                "c.portesnbr,  c.puissancecom,  c.puissancekw, c.typemoteur, c.typeboitevitesses, c.vitessesbte, c.vitessesnbr, d.code, c.injection  " +
                "from bve_marque a  INNER JOIN  bve_modele b on a.id= b.idmarque INNER JOIN  bve_variante c on b.id= c.idmodele INNER JOIN  " +
                "bve_typevehicule d on d.id= c.typevehicule INNER JOIN  bve_codecaracteristiquevaleur e on e.codevaleur = c.alimentation INNER JOIN  " +
                "bve_codecaracteristiquevaleur f on f.codevaleur = c.carrosserie order by a.nom";

        String queryNGC = "select id, marque, vin, immat, modele, version, typevarversprf, cnit_mines, energie, codemoteur, tpboitevit, nbvitesse, cylindree, nbportes, carrosserie, carrosseriecg, genrev, genrevcg, puisfisc, puiskw, nbcylind, nbplass, empat, largeur, longueur, hauteur " +
                " from vehngc  where LENGTH(vin) > 0 " +
                " and vin not like '%,%' " +
                " and vin not like '%;%' " +
                " and vin not like '%\"%' " +
                " and LENGTH(vin) = CHAR_LENGTH(vin) LIMIT 10000";

        /* Create the pipeline */
        Pipeline p = Pipeline.create(options);

        /* Source Request
            Create the PCollection 'RequestElement' by applying a 'Read' transform.
        */
        PCollection<RequestElement> requests = p.apply("ReadDatabase", JdbcIO.<RequestElement>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                        "com.mysql.jdbc.Driver",
                        "jdbc:mysql://"+ options.getHostnamedbSIM() +":"  + options.getPortdbSIM() + "/"+ options.getBasedbSIM() )
                        .withUsername(options.getLogindbSIM())
                        .withPassword(options.getPassworddbSIM()))
                .withCoder( SerializableCoder.of(RequestElement.class) )
                .withQuery(queryReq)
                .withRowMapper( (JdbcIO.RowMapper<RequestElement>) resultSet -> {

                    return RequestElement.create(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    );

                }));



        PCollection<Row> processedRequests = requests.apply( "conversion", ParDo.of(new convertToRow())).setCoder(convertToRow.coder)
                                                    .apply( "remove duplicates", Distinct.<Row>create() );

        processedRequests.apply(SqlTransform.query("SELECT COUNT(*) FROM PCOLLECTION"))
                        .apply(
                                "log_result1",
                                MapElements.via(
                                        new SimpleFunction<Row, Void>() {
                                            @Override
                                            public @Nullable
                                            Void apply(Row input) {
                                                System.out.println("PCOLLECTION: " + input.getValues());
                                                return null;
                                            }
                                        }));

        /*requests.apply(MapElements.via(new FormatAsTextFn()))
                .apply("Write", TextIO.write().to(options.getOutput()));*/


        /* Source Vehicule NGC
            Create the PCollection 'VehNgcElement' by applying a 'Read' transform.
        */
        /*PCollection<VehNgcElement> ngcdata = p.apply("ReadNGCData", JdbcIO.<VehNgcElement>read()
                                    .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                            "com.mysql.jdbc.Driver",
                                            "jdbc:mysql://"+ options.getHostnamedbSIM() +":"  + options.getPortdbSIM() + "/"+ options.getBasedbSIM() )
                                            .withUsername(options.getLogindbSIM())
                                            .withPassword(options.getPassworddbSIM()))
                                    .withCoder( SerializableCoder.of(VehNgcElement.class) )
                                    .withQuery(queryNGC)
                                    .withRowMapper( (JdbcIO.RowMapper<VehNgcElement>) resultSet -> {

                                                return VehNgcElement.create(
                                                        resultSet.getInt(1),
                                                        resultSet.getString(2),
                                                        resultSet.getString(3),
                                                        resultSet.getString(4),
                                                        resultSet.getString(5),
                                                        resultSet.getString(6),
                                                        resultSet.getString(7),
                                                        resultSet.getString(8),
                                                        resultSet.getString(9),
                                                        resultSet.getString(10),
                                                        resultSet.getString(11),
                                                        resultSet.getString(12),
                                                        resultSet.getString(13),
                                                        resultSet.getString(14),
                                                        resultSet.getString(15),
                                                        resultSet.getString(16),
                                                        resultSet.getString(17),
                                                        resultSet.getString(18),
                                                        resultSet.getString(19),
                                                        resultSet.getString(20),
                                                        resultSet.getString(21),
                                                        resultSet.getString(22),
                                                        resultSet.getString(23),
                                                        resultSet.getString(24),
                                                        resultSet.getString(25),
                                                        resultSet.getString(26)
                                                );
                                                    }));

        ngcdata.apply(MapElements.via(new FormatAsTextFn()))
                .apply("Write", TextIO.write().to(options.getOutput()));*/

        p.run().waitUntilFinish();
    }

}