package org.API.Structures.base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.API.Structures.Requests.GetRequest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseSetup {

    protected GetRequest apiClient2;
    static String currentDirectory = System.getProperty("user.dir");
    static String extentReportPath = currentDirectory + "/reports/extent-reports/extent.html";
    String log4jConfigPath = currentDirectory + "/reports/log4j-reports/log4j2.xml";
    private String url = "https://reqres.in/api";

    protected static ExtentReports extent;
    protected static ExtentTest test;
    private static final Logger logger = LogManager.getLogger(BaseSetup.class);

    @BeforeClass
    public static void setUp() {
        initializeExtentReports();
        initializeLogger();
        // Other setup actions if needed
    }

    @AfterClass
    public static void tearDown() {
        extent.flush();
        // Perform any teardown actions
    }

    private static void initializeExtentReports() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(extentReportPath);

        extent.attachReporter(spark);
    }

    private static void initializeLogger() {
        // Basic logging configuration
        String logConfigFilePath = currentDirectory + "/src/main/resources/log4j2.xml";
        System.out.println("Log4j Configuration File Path: " + logConfigFilePath); // Debugging
        System.setProperty("log4j.configurationFile", logConfigFilePath); // Specify the path relative to the project directory
        System.out.println("Log4j Configuration Loaded Successfully."); // Debugging
    }

    @Before
    public void initializeApiClient2() {
        apiClient2 = new GetRequest(url);
        // Other initialization steps if needed
    }
}