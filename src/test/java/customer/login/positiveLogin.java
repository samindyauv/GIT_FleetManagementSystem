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
        String browser = "Chrome"; // Change this dynamically if needed
        ExtentReportManager.initReport(browser); // Pass the browser name
    }

    @BeforeMethod
    public void setUp() throws IOException, InterruptedException {
        ExtentReportManager.startTest("Positive Test Case for User Login with Valid Credentials", "<b>Login with valid credentials</b>");
        loadUrl();
        ExtentReportManager.testSteps("<b><font color='blue'>Opened the application URL</font></b>");

        webSteps.login();
        ExtentReportManager.testSteps("Entered valid credentials and clicked login");
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
            ExtentReportManager.logFail("Failed to navigate to dashboard");
        }
    }

    @AfterMethod
    public void tearDownTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportManager.logFail("❌ <b><font color='red'> FAILED : </font></b>" + result.getThrowable().getMessage());
        } else {
            ExtentReportManager.logPass("✅ <b><font color='green'> PASSED </font></b>");
        }

        // Capture screenshot for both passed and failed tests
        ExtentReportManager.captureScreenshot(driver, result);
        tearDown();
    }

    @AfterSuite
    public void finalizeReport() {
        ExtentReportManager.flushReport(); // Ensures the report is generated
        ExtentReportManager.openReport();  // Opens the report automatically
    }
}
