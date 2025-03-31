package customer.protocol.negative;

import jdk.jfr.Name;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReportManager;
import utils.baseTest;
import java.awt.*;
import java.io.IOException;

public class negativeAddProtocol extends baseTest {

    @BeforeSuite
    public void setupReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUp() throws IOException, InterruptedException {
        ExtentReportManager.startTest("Add protocol Test", "<b>Add protocol with invalid data</b>");
        loadUrl();
        webSteps.login();
        ExtentReportManager.testSteps("Logged to System");
        webSteps.waiting();
        webSteps.click("clickAdmin");
        webSteps.click("clickProtocol");
        webSteps.click("clickAddProtocol");
    }

    @DataProvider(name="protocolData")
    public Object [][] negativeProtocolDataProvider(){

        String [][] data = {
                {"Verify that the user cannot add protocol without a protocol name","","Testing_Model","Testing_Protocol","3G","Protocol name is required"},
        };
        return data;
    }

    @Test(dataProvider = "protocolData")
    @Name("Negative Protocol Test Cases")
    public void addProtocolWithInvalidData(String testCase,String protocolName, String protocolModel,String protocol,String connectivity, String expValidation) throws InterruptedException, AWTException {

        ExtentReportManager.testSteps("<b><font color='blue'>Test Case : </font></b>"+ testCase);
        ExtentReportManager.testSteps("<b>Protocol Name : </b> " + (protocolName.isEmpty() ? "[Empty]" : protocolName));
        ExtentReportManager.testSteps("<b>Model : </b>" + (protocolModel.isEmpty() ? "[Empty]" : protocolModel));
        ExtentReportManager.testSteps("<b>Protocol : </b> " + (protocol.isEmpty() ? "[Empty]" : protocol));
        ExtentReportManager.testSteps("<b>Connectivity : </b>" + (connectivity.isEmpty() ? "[Empty]" : connectivity));

        webSteps.type(protocolName,"protocolNameField");
        webSteps.type(protocolModel,"protocolModelNameField");
        webSteps.type(protocol,"protocolProtocolField");
        webSteps.select2("protocolConnectivityDropdown");
        webSteps.click("addProtocolButton");

        ExtentReportManager.testSteps("<b> Checking expected validation : </b>" + expValidation);

        switch (expValidation) {
            case "Protocol name is required" -> {
                Assert.assertEquals(webSteps.getText("protocolNameIsRequired"), "Protocol name is required", "Validation Failed");
            }
            case "Invalid" -> {
                ExtentReportManager.logPass("Invalid credentials detected as expected.");
            }
            default -> ExtentReportManager.testSteps("⚠️ Unknown validation case encountered.");
        }
    }



    @AfterMethod
    public void tearDownTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportManager.logFail("❌ <b><font color='red'> FAILED : </font></b>" + result.getThrowable().getMessage());
        } else {
            ExtentReportManager.logPass("✅ <b><font color='green'> PASSED </font></b>");
        }

        ExtentReportManager.captureScreenshot(driver, result);
        tearDown();
    }

    @AfterSuite
    public void finalizeReport() {
        ExtentReportManager.flushReport();
        ExtentReportManager.openReport();
    }

}
