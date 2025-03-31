package customer.protocol.positive;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReportManager;
import utils.baseTest;

import java.io.IOException;

public class statusProtocol extends baseTest {

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

    @Test(testName = "Deactivate Protocols from Active status")
    public void deactivateProtocolStatus() throws InterruptedException {
        ExtentReportManager.testSteps("<b><font color='blue'>Test Case : </font></b> Deactivate Protocols from Activate status");
        webSteps.click("clickProtocolFilterActive");
        webSteps.waiting();
        webSteps.click("clickProtocolAction");
        webSteps.click("clickProtocolDeactivate");
        Assert.assertEquals("Protocol updated successfully", webSteps.getText("ToastMessage"));
    }

    @Test(testName = "Active Protocols from Deactivate status")
    public void activateProtocolStatus() throws InterruptedException {
        ExtentReportManager.testSteps("<b><font color='blue'>Test Case : </font></b> Active Protocols from deactivate status");
        webSteps.click("clickProtocolFilterInactive");
        webSteps.waiting();
        webSteps.click("clickProtocolAction");
        webSteps.click("clickProtocolActivate");
        Assert.assertEquals("Protocol updated successfully", webSteps.getText("ToastMessage"));
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
