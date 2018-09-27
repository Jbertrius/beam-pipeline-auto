package etai;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface VinextractOptions extends PipelineOptions {


    /** SIM Database Parameters */

    // hostname
    @Description("hostname dbSIM")
    @Default.String("localhost")

    String getHostnamedbSIM();
    void setHostnamedbSIM(String hostname);

    // login
    @Description("login dbSIM")
    @Default.String("postgres")

    String getLogindbSIM();
    void setLogindbSIM(String login);

    // password
    @Description("password dbSIM")
    @Default.String("jeanbertrand")

    String getPassworddbSIM();
    void setPassworddbSIM(String password);

    // database
    @Description("database dbSIM")
    @Default.String("CarData")

    String getBasedbSIM();
    void setBasedbSIM(String database);

    // port
    @Description("port dbSIM")
    @Default.String("5432")

    String getPortdbSIM();
    void setPortdbSIM(String port);


    /** BVE Database Parameters */

    // hostname
    @Description("hostname dbBVE")
    @Default.String("localhost")

    String getHostnamedbBVE();
    void setHostnamedbBVE(String hostname);

    // login
    @Description("login dbBVE")
    @Default.String("postgres")

    String getLogindbBVE();
    void setLogindbBVE(String login);

    // password
    @Description("password dbBVE")
    @Default.String("jeanbertrand")

    String getPassworddbBVE();
    void setPassworddbBVE(String password);

    // database
    @Description("database dbBVE")
    @Default.String("CarData")

    String getBasedbBVE();
    void setBasedbBVE(String database);

    // port
    @Description("port dbBVE")
    @Default.String("5432")

    String getPortdbBVE();
    void setPortdbBVE(String port);
    
    
    
    /** Requests Query Parameters */

    // pays
    @Description("pays Req")
    @Default.String("FR")

    String getPays();
    void setPays(String port);


    // Marque pour filtrer
    @Description("Marque Filter parameter")
    @Default.String("VOLKSWAGEN")
    String getMarque();

    void setMarque(String value);

    // Set this  option to specify where to write the output.
    @Description("Path of the file to write to")
    @Default.String("/outputData")
    String getOutput();
    void setOutput(String value);


    /** Mode */

    // Set this required option to specify where to run the program.
    @Description("Environment ")
    @Default.String("Local")
    String getEnvironment();
    void setEnvironment(String value);
}
