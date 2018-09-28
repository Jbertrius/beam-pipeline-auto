package etai;

import etai.Elements.BveElement;
import etai.Elements.CompareElement;
import etai.Elements.RequestElement;
import etai.Elements.VehNgcElement;
import org.apache.avro.reflect.Nullable;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.joinlibrary.Join;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.coders.*;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.*;
import org.apache.beam.sdk.io.jdbc.*;

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

        String limit;

        if (options.getLimit() == 0) {
            limit = " ";
        } else {
            limit = " LIMIT " + options.getLimit();
        }

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
        PCollection<RequestElement> requests = p.apply("ReadRequests", JdbcIO.<RequestElement>read()
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


        PCollection< KV<String, Integer> > KVrequest = requests.apply( "handleRequest", new TransformRequest() );




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


        PCollection<KV<String, VehNgcElement>> KVngc =  ngcdata.apply("KV_ngc_mapping", MapElements
                                                        .into(TypeDescriptors.kvs(TypeDescriptors.strings(), TypeDescriptor.of(VehNgcElement.class) ))
                                                        .via( new KVNGC()));



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

        PCollection<KV< Integer, BveElement >> KVbve =  bveData.apply("KV_bve_mapping",  MapElements
                                                                .into(TypeDescriptors.kvs(TypeDescriptors.integers(),  TypeDescriptor.of(BveElement.class)))
                                                                .via( new KVBVE()));




        /*
         * Jointure entre NGC et les requetes sur Immat
         * */
        PCollection< KV<Integer,CompareElement> > filteredJoin  = Join.innerJoin(KVrequest, KVngc)
                                                                        .apply( "Filter_by_Marque", Filter.by(new FilterMarque(options.getMarque())) )
                                                                        .apply( "KV_join_mapping",  MapElements
                                                                                                .into(TypeDescriptors.kvs(TypeDescriptors.integers(), TypeDescriptor.of(CompareElement.class) ))
                                                                                                .via( new KVJoin() ));

        /*
           Jointure entre NGC, requetes et bve
          **/
        PCollection< KV<Integer, KV<CompareElement, BveElement>> > filterFinalJoin =   Join.innerJoin(filteredJoin, KVbve)
                                                                                            .apply( "compare_bve_ngc", Filter.by( new FilterByCompare() ) );

        /*
           Write to File
          **/
        filterFinalJoin.apply("ToString", ParDo.of(new ConvertToString()))
                        .apply( "WriteToFile", TextIO.write().to(options.getOutput()).withSuffix(".csv").withoutSharding());


        p.run().waitUntilFinish();
    }


    static class KVRequest implements SerializableFunction<RequestElement, KV<String, Integer>> {
            @Override
            public KV<String, Integer> apply(RequestElement input) {
                Integer variante = Integer.parseInt(input.resultat().split(",")[0]);
                return KV.of(input.immat(), variante);
            }
    }

    static class KVJoin implements SerializableFunction<KV<String, KV<Integer, VehNgcElement>>, KV<Integer, CompareElement>> {
        @Override
        public KV<Integer, CompareElement> apply(KV<String, KV<Integer, VehNgcElement>> input) {
            VehNgcElement ngcElement = input.getValue().getValue();
            Integer variante = input.getValue().getKey();

            return KV.of(variante, CompareElement.create(
                                                ngcElement.marque(),
                                                input.getKey(),
                                                variante,
                                                ngcElement.modele(),
                                                Integer.parseInt(ngcElement.cylindree()),
                                                Integer.parseInt(ngcElement.nbvitesse()),
                                                Integer.parseInt(ngcElement.nbportes()),
                                                Integer.parseInt(ngcElement.puiskw()),
                                                ngcElement.vin() ));
        }
    }

    static class KVNGC implements SerializableFunction<VehNgcElement, KV<String, VehNgcElement>> {
        @Override
        public KV<String, VehNgcElement> apply(VehNgcElement input) {
            return KV.of(input.immat(), input);
        }
    }

    static class REQRepresent  implements SerializableFunction<RequestElement, String> {
            @Override
            public String apply(RequestElement input) {
                return input.immat();
            }
    }

    static class FilterReq implements SerializableFunction<RequestElement, Boolean> {

        @Override
        public Boolean apply(RequestElement input) {
            return input.resultat().split(",").length == 1;
        }
    }

    static class FilterMarque implements SerializableFunction<KV<String, KV<Integer, VehNgcElement>>, Boolean> {

        private String filterMarque;

        FilterMarque( String filterMarque ) {
            this.filterMarque = filterMarque;
        }

        @Override
        public Boolean apply(KV<String, KV<Integer, VehNgcElement>> input) {
            return input.getValue().getValue().marque().equals(this.filterMarque);
        }
    }

    static class FilterByCompare implements SerializableFunction< KV<Integer, KV<CompareElement, BveElement>>, Boolean> {
        @Override
        public Boolean apply(KV<Integer, KV<CompareElement, BveElement>> input) {

            BveElement bve = input.getValue().getValue();
            CompareElement cmp = input.getValue().getKey();

            return  cmp.marque().equals(bve.marque()) &&
                    cmp.portesnbr().equals(bve.portesnbr()) &&
                    cmp.modele().equals(bve.modele()) &&
                    cmp.cylindreecm3().equals(bve.cylindreecm3()) &&
                    cmp.puissancecom().equals(bve.puissancecom()) &&
                    cmp.vitessesnbr().equals(bve.vitessesnbr());
        }
    }

    static class KVBVE implements SerializableFunction<BveElement, KV< Integer, BveElement >> {
        @Override
        public KV<Integer, BveElement> apply(BveElement element) {
            return KV.of(element.Id(), element)  ;
        }
    }

    public static class TransformRequest extends PTransform<PCollection<RequestElement> , PCollection< KV<String, Integer> >> {

        @Override
        public PCollection<KV<String, Integer>> expand(PCollection<RequestElement> req) {


            // Remove duplicated requests
            PCollection<RequestElement> processedReq = req.apply("remove_duplicates_immat", Distinct.withRepresentativeValueFn( new REQRepresent() ));


            // Filter by selecting only request with one result
            PCollection<RequestElement> filterdReq = processedReq.apply("filter_more_than_one", Filter.by(new FilterReq()));


            // Mapping request Element to Key-Value Object with Immat as Key and Result as Value
            PCollection<KV<String, Integer>> KVreq = filterdReq.apply("KV_request_mapping", MapElements.into(TypeDescriptors.kvs(TypeDescriptors.strings(), TypeDescriptors.integers()))
                                        .via( new KVRequest()));

            return KVreq;
        }
    }

    public static class ConvertToString extends DoFn<KV<Integer, KV<CompareElement, BveElement>>, String> {
        @ProcessElement
        public void processElement(ProcessContext c) throws Exception {
            StringBuilder sb = new StringBuilder();
            KV<CompareElement, BveElement> value = c.element().getValue();
            sb.append(String.format("%s,%s", value.getKey().vin(), value.getValue().Id()));
            c.output(sb.toString());
        }
    }

}
