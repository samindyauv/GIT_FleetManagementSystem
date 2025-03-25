package customer.login;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReportManager;
import utils.baseTest;

import java.io.IOException;

public class positiveLogin extends baseTest {

    @BeforeSuite
    public void setupReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUp() throws IOException, InterruptedException {
        ExtentReportManager.startTest("Positive Login Test", "<b>Login with valid credentials</b>");
        loadUrl();
        ExtentReportManager.testSteps("Opened the application URL");

        webSteps.login();
        ExtentReportManager.testSteps("<b><font color='blue'>Test Case : </font></b>Verify that the user can successfully log in with valid email and password credentials");
        webSteps.waiting();
    }

    @Test()
    public void loginWithValidCredentials() throws InterruptedException {
        ExtentReportManager.testSteps("Verifying login success...");

        boolean urlVerification = driver.getCurrentUrl().contains("live-tracking");
        Assert.assertTrue(urlVerification, "Expecting login success but not navigated to dashboard");

        if (urlVerification) {
            ExtentReportManager.logPass("Navigated to dashboard successfully");
        } else {
            ExtentReportManager.logFail("Failed to navigate dashboard");
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
