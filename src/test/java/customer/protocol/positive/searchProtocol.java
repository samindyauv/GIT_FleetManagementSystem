package customer.protocol.positive;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReportManager;
import utils.baseTest;

import java.io.IOException;

public class searchProtocol extends baseTest {

    @BeforeSuite
    public void setupReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUp() throws IOException, InterruptedException {
        ExtentReportManager.startTest("Search protocol Test", "<b>Search protocol</b>");
        loadUrl();
        webSteps.login();
        ExtentReportManager.testSteps("Logged to System");
        webSteps.waiting();
    }

    @DataProvider(name="searchProtocolData")
    public Object [][] searchProtocolDataProvider(){

        String [][] data = {
                {"Protocol_1","Search user","Search Protocol"},
                {"PROTOCOL_1","Search protocol with uppercase","Search Protocol with Uppercase"},
                {"protocol_1","Search protocol with lowercase","Search Protocol with Lowercase"},
                {"pRoToCoL_1","Search protocol with mixcased","Search Protocol with Mixed Case"},
                {"!@#$%^&*","Search protocol with special characters","Search Protocol with Special Characters"}
        };
        return data;
    }

    @Test(dataProvider = "searchProtocolData")
    public void searchUser(String userName, String expValidation, String testCase) throws InterruptedException {
        ExtentReportManager.testSteps("<b><font color='blue'>Test Case : </font></b>"+ testCase);
        webSteps.waiting();
        webSteps.click("clickAdmin");
        webSteps.click("clickProtocol");

        webSteps.type(userName,"searchProtocolField");
        webSteps.waiting();

        switch (expValidation) {
            case "Search" -> {
                Assert.assertEquals("Protocol_1", webSteps.getText("protocolSearchResult"), "Passed");
            }
            case "No Protocol Data" -> {
                Assert.assertEquals("No Protocol Data", webSteps.getText("noProtocolData"), "Passed");
            }
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
