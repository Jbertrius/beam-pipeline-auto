package etai;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface VinextractOptions extends PipelineOptions {

    /** hostname  */
    @Description("hostname dbSIM")
    @Default.String("10.23.10.23")

    String getHostnamedbSIM();
    void setHostnamedbSIM(String hostname);

    /** login */
    @Description("login dbSIM")
    @Default.String("sraoul")

    String getLogindbSIM();
    void setLogindbSIM(String login);

    /** password */
    @Description("password dbSIM")
    @Default.String("LeSimCestLourd")

    String getPassworddbSIM();
    void setPassworddbSIM(String password);

    /** database */
    @Description("database dbSIM")
    @Default.String("servim")

    String getBasedbSIM();
    void setBasedbSIM(String database);

    /** port */
    @Description("port dbSIM")
    @Default.String("3307")

    String getPortdbSIM();
    void setPortdbSIM(String port);

    /** pays */
    @Description("pays Req")
    @Default.String("FR")

    String getPays();
    void setPays(String port);

    /** Set this required option to specify where to write the output. */
    @Description("Path of the file to write to")
    @Default.String("/outputData.txt")
    String getOutput();

    void setOutput(String value);

}
