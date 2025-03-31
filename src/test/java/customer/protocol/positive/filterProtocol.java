package customer.protocol.positive;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReportManager;
import utils.baseTest;

import java.io.IOException;

public class filterProtocol extends baseTest {
    @BeforeSuite
    public void setupReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUp() throws IOException, InterruptedException {
        ExtentReportManager.startTest("Filter Protocol Test", "<b>Filter Protocol Test</b>");
        loadUrl();
        ExtentReportManager.testSteps("Opened the application URL");
        webSteps.login();
        ExtentReportManager.testSteps("Logged to System");
        webSteps.waiting();
        webSteps.click("clickAdmin");
        webSteps.click("clickProtocol");
        webSteps.click("protocolFilterDropdown");
    }

    @Test(testName = "Filter Protocols by Active status")
    public void filterProtocolByActiveStatus() throws InterruptedException {
        ExtentReportManager.testSteps("<b><font color='blue'>Test Case : </font></b> Filter Protocols by Active status");
        webSteps.click("clickProtocolFilterActive");
        Assert.assertEquals("Active", webSteps.getText("protocolFilterActive"));
    }

    @Test(testName = "Filter Protocols by Inactive status")
    public void filterProtocolsByInactiveStatus() throws InterruptedException {
        ExtentReportManager.testSteps("<b><font color='blue'>Test Case : </font></b>Filter Protocols by Inactive status");
        webSteps.click("clickProtocolFilterInactive");
        Assert.assertEquals("Inactive", webSteps.getText("protocolFilterInactive"));
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
