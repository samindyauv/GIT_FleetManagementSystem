package customer.protocol.positive;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReportManager;
import utils.baseTest;

import java.awt.*;
import java.io.IOException;

public class positiveAddProtocol extends baseTest {

    @BeforeSuite
    public void setupReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUp() throws IOException, InterruptedException {
        ExtentReportManager.startTest("Add protocol Test", "<b>Add protocol with valid data</b>");
        loadUrl();
        webSteps.login();
        ExtentReportManager.testSteps("Logged to System");
        ExtentReportManager.testSteps("<b><font color='blue'>Test Case : </font></b>Verify that the user can successfully add protocol with valid data");
        webSteps.waiting();
        webSteps.click("clickAdmin");
        webSteps.click("clickProtocol");
        webSteps.click("clickAddProtocol");
    }
    @Test(testName = "Add Addon with Active status")
    public void addAddonWithActiveStatus() throws InterruptedException, AWTException {
        webSteps.type(webSteps.generateRandomProtocolName(), "protocolNameField");
        webSteps.type(webSteps.generateRandomProtocolModelName(), "protocolModelNameField");
        webSteps.type(webSteps.generateRandomProtocolProtocol(), "protocolProtocolField");
        webSteps.select2("protocolConnectivityDropdown");
        webSteps.click("addProtocolButton");
        Assert.assertEquals("Protocol added successfully",webSteps.getText("ToastMessage"), "Passed");
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
