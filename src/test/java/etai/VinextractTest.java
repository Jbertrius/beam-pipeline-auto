package etai;

import etai.Elements.BveElement;
import etai.Elements.CompareElement;
import etai.Elements.RequestElement;
import etai.Elements.VehNgcElement;
import org.apache.avro.reflect.Nullable;
import org.apache.beam.model.pipeline.v1.RunnerApi;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.extensions.joinlibrary.Join;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.testing.ValidatesRunner;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Arrays;
import java.util.List;

public class VinextractTest {

    private final VehNgcElement[] VEH_NGC_ELEMENTS =
            new VehNgcElement[] {
                VehNgcElement.create(1460723,"PEUGEOT","VF31AVJZE51910866","BX119KR","106","OPEN/COL","1AVJZE","1AVJZE","GAZOLE","VJZ","MECANIQUE","5","1527","5","BERLINE","CI","VP","VP","5","42","4","5","239","159","368","138"),
                VehNgcElement.create(1460724,"VOLKSWAGEN","VF39D4HXG92110321","BW177AQ","607","HDI","","MPE5502LB947","GAZOLE","4HX","MECANIQUE","6","2179","4","BERLINE","CI","VP","VP","8","98","4","5","280","180","488","144"),
                VehNgcElement.create(1460726,"VOLKSWAGEN","ZAR90700005668045","CB512AF","ALFA 33","1L7 16S","907A1B","907A1B","ESSENCE","","MECANIQUE","5","1712","5","BERLINE","CI","VP","VP","8","0","4","5","0","161","408","0"),
                VehNgcElement.create(1460728,"VOLKSWAGEN","VSSZZZ1LZZR030464","BX531GE","TOLEDO","","1LABS2B","1LABS2B","ESSENCE","","MECANIQUE","5","1781","5","BERLINE","CI","VP","VP","7","0","4","5","0","166","432","0"),
            };

    private final BveElement[] BVE_ELEMENTS =
            new BveElement[] {
                    BveElement.create(37727,"VOLKSWAGEN","607","GTV II (95->03)","II","INJECTION",(float)5.0,"COUPE","","GAUCHE",2179,(float)2.0,4,"ESSENCE",0,4,150,98,"16201","","MANUELLE",6,"VL",""),
                    BveElement.create(229748,"VOLKSWAGEN","155","155 (92->98)","","INJECTION",(float)0.0,"BERLINE","","GAUCHE",2499,(float)2.5,4,"DIESEL",0,4,125,92,"VM07B","","MANUELLE",5,"VL",""),
                    BveElement.create(383455,"VOLKSWAGEN","TOLEDO","GTV I (72->87)","I","CARBURATION",(float)0.0,"COUPE","","GAUCHE",1781,(float)2.0,4,"ESSENCE",0,5,120,90,"01623","","MANUELLE",5,"VL",""),
                    BveElement.create(28708,"VOLKSWAGEN","33","33 II (90->95)","II","INJECTION",(float)4.0,"BERLINE","","GAUCHE",1712,(float)1.7,4,"ESSENCE",0,5,110,79,"30736","","MANUELLE",5,"VL",""),
            };

    private final RequestElement[] REQUEST_ELEMENTS =
            new RequestElement[] {
                    RequestElement.create(10390830,"BW177AQ","FR","VL","37727"),
                    RequestElement.create(10390831,"CB512AF","FR","VL","264429, 51254"),
                    RequestElement.create(10390832,"BX119KR","FR","VL","229748"),
                    RequestElement.create(10390833,"BX531GE","FR","VL","383455"),
                    RequestElement.create(10390833,"BX531GE","FR","VL","383455"),
                    RequestElement.create(10390833,"BX531GE","FR","VL","383455"),
            };

    private final List<VehNgcElement> VEH_NGC = Arrays.asList(VEH_NGC_ELEMENTS);
    private final List<RequestElement> REQUEST = Arrays.asList(REQUEST_ELEMENTS);
    private final List<BveElement> BVE = Arrays.asList(BVE_ELEMENTS);

    @Rule
    public TestPipeline p = TestPipeline.create();

    @Test
    @Category(ValidatesRunner.class)
    public void testVinExtract() throws Exception {

        PCollection<BveElement> bveElements = p.apply( "Bve", Create.of(BVE).withCoder( SerializableCoder.of(BveElement.class))  );
        PCollection<RequestElement> reqElements = p.apply( "Req", Create.of(REQUEST).withCoder( SerializableCoder.of(RequestElement.class)) );
        PCollection<VehNgcElement> ngcElements = p.apply("Veh", Create.of(VEH_NGC).withCoder( SerializableCoder.of(VehNgcElement.class)) );

        PCollection<KV<String, Integer>>  KVReq =  reqElements.apply("handleRequest", new Vinextract.TransformRequest());


        PAssert.that(KVReq).containsInAnyOrder(
                KV.of("BX531GE", 383455),
                KV.of("BX119KR", 229748),
                KV.of("BW177AQ", 37727)
        );


        PCollection<KV<String, VehNgcElement>> KVngc = ngcElements.apply("KV_ngc_mapping", MapElements
                .into(TypeDescriptors.kvs(TypeDescriptors.strings(), TypeDescriptor.of(VehNgcElement.class) ))
                .via( new Vinextract.KVNGC()));


        PCollection< KV<String, KV<Integer, VehNgcElement>> > joinedDatasets = Join.innerJoin(KVReq, KVngc);


        PAssert.that(joinedDatasets).containsInAnyOrder(
                KV.of("BX531GE", KV.of(383455, VehNgcElement.create(1460728,"VOLKSWAGEN","VSSZZZ1LZZR030464","BX531GE","TOLEDO","","1LABS2B","1LABS2B","ESSENCE","","MECANIQUE","5","1781","5","BERLINE","CI","VP","VP","7","0","4","5","0","166","432","0")) ),
                KV.of("BW177AQ", KV.of(37727, VehNgcElement.create(1460724,"VOLKSWAGEN","VF39D4HXG92110321","BW177AQ","607","HDI","","MPE5502LB947","GAZOLE","4HX","MECANIQUE","6","2179","4","BERLINE","CI","VP","VP","8","98","4","5","280","180","488","144")) ),
                KV.of("BX119KR", KV.of(229748 ,VehNgcElement.create(1460723,"PEUGEOT","VF31AVJZE51910866","BX119KR","106","OPEN/COL","1AVJZE","1AVJZE","GAZOLE","VJZ","MECANIQUE","5","1527","5","BERLINE","CI","VP","VP","5","42","4","5","239","159","368","138")))
        );


        PCollection< KV<Integer, CompareElement> > filteredJoin = joinedDatasets.apply( "Filter_by_Marque", Filter.by(new Vinextract.FilterMarque("VOLKSWAGEN")) )
                                                                                .apply( "KV_join_mapping",  MapElements
                                                                                        .into(TypeDescriptors.kvs(TypeDescriptors.integers(), TypeDescriptor.of(CompareElement.class) ))
                                                                                        .via( new Vinextract.KVJoin() ));

        PAssert.that(filteredJoin).containsInAnyOrder(
                KV.of(383455, CompareElement.create("VOLKSWAGEN","BX531GE",383455,"TOLEDO",1781, 5, 5,0,"VSSZZZ1LZZR030464")),
                KV.of(37727, CompareElement.create("VOLKSWAGEN","BW177AQ",37727,"607",2179,6, 4,98,"VF39D4HXG92110321"))
        );




        PCollection<KV< Integer, BveElement >> KVbve =  bveElements.apply("KV_bve_mapping",  MapElements
                                                                        .into(TypeDescriptors.kvs(TypeDescriptors.integers(),  TypeDescriptor.of(BveElement.class)))
                                                                        .via( new Vinextract.KVBVE()));


        /*PCollection<String> finalJoinedDatasets =  Join.innerJoin(filteredJoin, KVbve)
                .apply( Filter.by(new Vinextract.FilterByCompare())  )
                .apply("ToString", ParDo.of(new Vinextract.ConvertToString()));



        PAssert.that(finalJoinedDatasets).containsInAnyOrder(
                "VSSZZZ1LZZR030464,383455"
        );*/

        p.run().waitUntilFinish();


    }


}
