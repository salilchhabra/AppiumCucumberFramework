package runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.*;
import utils.DriverManager;
import utils.GlobalParams;
import utils.ServerManager;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

/**
 * An example of using TestNG when the test class does not inherit from
 * AbstractTestNGCucumberTests but still executes each scenario as a separate
 * TestNG test.
 */
@CucumberOptions(plugin = {"pretty", "html:target/cucumber", "summary", "de.monochromata.cucumber.report.PrettyReports:targetcucumber-html-reports"},
        features = {"src/test/resources"},
        glue = {"stepdef"},
        dryRun = false,
        monochrome = true,
        strict = true
//        tags = "@test"
)
public class MyTestNGRunnerTest {

    private TestNGCucumberRunner testNGCucumberRunner;

//    @Parameters({"platformName", "udid", "deviceName",
////            "systemPort",
////            "chromeDriverPort",
//            })
    @BeforeSuite(alwaysRun = true)
    public void setUpClass() throws Exception {
        GlobalParams params = new GlobalParams();
        params.setPlatformName("Android");
        params.setUDID("emulator-5554");
        params.setDeviceName("Pixel_2_API_27");
//        params.setSystemPort(systemPort);
//        params.setChromeDriverPort(chromeDriverPort);


        new ServerManager().startServer();
        new DriverManager().initializeDriver();
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runScenario(pickle.getPickle());
    }

    @DataProvider(name = "scenarios")
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownClass() {
        DriverManager driverManager = new DriverManager();
        if (driverManager.getDriver() != null) {
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
        ServerManager serverManager = new ServerManager();
        if (serverManager.getServer() != null) {
            serverManager.getServer().stop();
        }
        if (testNGCucumberRunner != null) {
            testNGCucumberRunner.finish();
        }
    }

}
