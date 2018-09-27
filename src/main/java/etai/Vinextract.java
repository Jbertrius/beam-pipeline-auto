package etai;

import etai.DoFnFunctions.FilterBy;
import etai.DoFnFunctions.convertToKV;
import etai.DoFnFunctions.convertToRow;
import etai.Elements.BveElement;
import etai.Elements.CompareElement;
import etai.Elements.RequestElement;
import etai.Elements.VehNgcElement;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.joinlibrary.Join;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.coders.*;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.*;
import org.apache.beam.sdk.io.jdbc.*;

import javax.annotation.Nullable;
import java.util.List;

public class Vinextract {

    //private static final Logger LOG = LoggerFactory.getLogger(Vinextract.class);


    public static void main(String[] args) {


        /* Get Pipeline options */
        VinextractOptions options =
                PipelineOptionsFactory.fromArgs(args)
                        .withValidation()
                        .as(VinextractOptions.class);


        /* Define Remote or Local State */
        String driver,databaseType,queryReq, queryBVE;

        String limit = "  LIMIT 40000";
        Integer fetchsize = 500;

        if (options.getEnvironment().equals("Local")) {
            driver = "org.postgresql.Driver";
            databaseType = "postgresql";
            queryReq = Query.queryReqLocal.getQuery();
            queryBVE = Query.queryBVELocal.getQuery();
        } else {
            driver = "com.mysql.jdbc.Driver";
            databaseType = "mysql";
            queryReq = Query.queryReqRemote.getQuery();
            queryBVE = Query.queryBVELocal.getQuery();
        }


        /* Create the pipeline */
        Pipeline p = Pipeline.create(options);

        /* Source Request
            Create the PCollection 'RequestElement' by applying a 'Read' transform.
        */
        PCollection<RequestElement> requests = p.apply("ReadDatabase", JdbcIO.<RequestElement>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                        driver,
                        "jdbc:" + databaseType + "://"+ options.getHostnamedbSIM() +":"  + options.getPortdbSIM() + "/"+ options.getBasedbSIM() )
                        .withUsername(options.getLogindbSIM())
                        .withPassword(options.getPassworddbSIM()))
                .withCoder( SerializableCoder.of(RequestElement.class) )
                .withFetchSize(fetchsize)
                .withQuery(queryReq + limit)
                .withRowMapper( (JdbcIO.RowMapper<RequestElement>) resultSet -> {

                    String regex = "[\\{\\}]";
                    return RequestElement.create(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5).replaceAll(regex, "")
                    );

                }));


        PCollection<Row> processedRequests = requests.apply( "conversion", ParDo.of(new convertToRow())).setCoder(convertToRow.requestCoder)
                .apply( "remove duplicates", Distinct.<Row>create() );


        PCollection<KV<String, Row>> KVrequest = processedRequests.apply( "KV_request", ParDo.of(new convertToKV("Immat")));





        /* Source Vehicule NGC
            Create the PCollection 'VehNgcElement' by applying a 'Read' transform.
        */
        PCollection<VehNgcElement> ngcdata = p.apply("ReadNGCData", JdbcIO.<VehNgcElement>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                        driver,
                        "jdbc:" + databaseType + "://"+ options.getHostnamedbSIM() +":"  + options.getPortdbSIM() + "/"+ options.getBasedbSIM() )
                        .withUsername(options.getLogindbSIM())
                        .withPassword(options.getPassworddbSIM()))
                .withCoder( SerializableCoder.of(VehNgcElement.class) )
                .withFetchSize(fetchsize)
                .withQuery(Query.queryNGC.getQuery() + limit)
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

        PCollection<Row> ngcdataRow = ngcdata.apply("conversionNGC", ParDo.of(new convertToRow())).setCoder(convertToRow.vehNgcCoder);

        PCollection<KV<String, Row>> KVngc =  ngcdataRow.apply( "KV_ngc", ParDo.of(new convertToKV("Immat")));



        /* Source Vehicule BVE
            Create the PCollection 'BveElement' by applying a 'Read' transform.
        */


        PCollection<BveElement> bveData = p.apply("ReadBVEData", JdbcIO.<BveElement>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                        "org.postgresql.Driver",
                        "jdbc:postgresql://"+ options.getHostnamedbBVE()  +":"  + options.getPortdbBVE() + "/"+ options.getBasedbBVE() )
                        .withUsername(options.getLogindbBVE())
                        .withPassword(options.getPassworddbBVE()))
                .withCoder( SerializableCoder.of(BveElement.class) )
                .withFetchSize(fetchsize)
                .withQuery(queryBVE + " WHERE marque = '" + options.getMarque().toUpperCase() + "'")
                .withRowMapper( (JdbcIO.RowMapper<BveElement>) resultSet -> {

                    return BveElement.create(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5) == null ? "" : resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getFloat(7),
                            resultSet.getString(8),
                            resultSet.getString(9) == null ? "" : resultSet.getString(9),
                            resultSet.getString(10),
                            resultSet.getInt(11),
                            resultSet.getFloat(12),
                            resultSet.getInt(13),
                            resultSet.getString(14),
                            resultSet.getInt(15),
                            resultSet.getInt(16),
                            resultSet.getInt(17),
                            resultSet.getInt(18),
                            resultSet.getString(19) == null ? "" : resultSet.getString(19),
                            resultSet.getString(20) == null ? "" : resultSet.getString(20),
                            resultSet.getString(21),
                            resultSet.getInt(22),
                            resultSet.getString(23),
                            resultSet.getString(24) == null ? "" : resultSet.getString(24)
                    );
                }));

        PCollection<KV< Integer, BveElement >> KVbve =  bveData.apply("KV_bve", ParDo.of(new DoFn<BveElement, KV< Integer, BveElement >>() {
            @ProcessElement
            public void processElement(@Element BveElement element, OutputReceiver<KV<Integer, BveElement>> receiver) {
                receiver.output(  KV.of(element.Id(), element)  );
            }
        }));




        /*
         * Jointure entre NGC et les requetes sur Immat
         * */
        PCollection<KV<String, KV<Row, Row>>> joinedDatasets = Join.innerJoin(KVngc, KVrequest);

        /*
         * TEST
         * **
         */
        /*KVbve.apply(
                "log_result",
                MapElements.via(
                        new SimpleFunction<KV< Integer, BveElement >, Void>() {
                            @Override
                            public @Nullable
                            Void apply(KV< Integer, BveElement > input) {
                                System.out.println( String.format("PCOLLECTION: %s ", input.getKey()  ));
                                return null;
                            }
                        }));*/

        /*
           Filtrage par marque
          **/

        PCollection<KV<Integer, CompareElement>> filtered  = joinedDatasets.apply( "Filter_by_Marque", ParDo.of(new FilterBy(options.getMarque())));




        /*
           Jointure entre NGC, requetes et bve
          **/
        PCollection<KV<Integer, KV<CompareElement, BveElement>>> finalJoinedDatasets =  Join.innerJoin(filtered, KVbve);

        finalJoinedDatasets.apply("ToString", ParDo.of(new DoFn<KV<Integer, KV<CompareElement, BveElement>>, String>() {

            @ProcessElement
            public void processElement(ProcessContext c) throws Exception {
                StringBuilder sb = new StringBuilder();

                KV<CompareElement, BveElement> value = c.element().getValue();

                sb.append(String.format("%s", value.getKey().immat()  ));

                c.output(sb.toString());
            }

        }))
                .apply( "WriteToFile", TextIO.write().to(options.getOutput()).withSuffix(".csv").withoutSharding());


        p.run().waitUntilFinish();
    }


}
