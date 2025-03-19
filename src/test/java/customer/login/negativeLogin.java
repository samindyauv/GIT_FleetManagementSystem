package customer.login;

import jdk.jfr.Name;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReportManager;
import utils.baseTest;

import java.io.IOException;

public class negativeLogin extends baseTest {
    @BeforeSuite
    public void setupReport() {
        String browser = "Chrome";
        ExtentReportManager.initReport(browser);
    }

    @BeforeMethod
    public void setUp() throws IOException, InterruptedException {
        ExtentReportManager.startTest("Negative Login Test", "Login with invalid credentials");
        loadUrl();
        ExtentReportManager.testSteps("Opened the application URL");
        ExtentReportManager.testSteps("Entered invalid credentials and clicked login");
        webSteps.waiting();
    }

    @DataProvider(name="loginData")
    public Object [][] negativeLoginDataProvider(){

        String [][] data = {
                {"customerdemo@gmail.com","invalidPassword","Invalid login credentials"}, // Valid email, invalid password
                {"customerdemo@gmail.com","","Password is required"}, // Valid email, empty password
                {"invalidemail@gmail.com","123456@Cd","Invalid login credentials"}, // Invalid email, valid password
                {"invalidemail@gmail.com","","Password is required"}, // Invalid email, empty password
                {"invalidemail@gmail.com","invalidPassword","Invalid login credentials"}, // Invalid email, invalid password
                {"invalidemail@","123456@Cd","Invalid email format"}, // Invalid email format, valid password
                {"invalidemail@","","Invalid login credentials"}, // Invalid email format, empty password
                {"invalidemail@","invalidPassword","Invalid login credentials"}, // Invalid email format, invalid password
                {"","123456@Cd","Email is required"}, // Empty email, valid password
                {"","invalidPassword","Email is required"}, // Empty email, invalid password
                {"","","Email and Password are required"} // Empty email & password
        };
        return data;
    }
    @Test(dataProvider = "loginData")
    @Name("Negative Login Test Cases")
    public void loginWithInvalidCredentials(String email, String password, String expValidation) throws InterruptedException {

        ExtentReportManager.testSteps("Attempting login with:"+ expValidation);
        //ExtentReportManager.
        ExtentReportManager.testSteps("Email: " + (email.isEmpty() ? "[Empty]" : email));
        ExtentReportManager.testSteps("Password: " + (password.isEmpty() ? "[Empty]" : password));

        webSteps.type(email,"customerEmailField");
        webSteps.type(password,"customerPasswordField");
        webSteps.click("customerLoginButton");

        ExtentReportManager.testSteps("Checking expected validation: " + expValidation);

        switch (expValidation) {
            case "Email is required" -> {
                Assert.assertEquals(webSteps.getText("loginEmailIsRequired"), "Email is required", "Validation Failed");
                ExtentReportManager.logPass("Validation matched: Email is required");
            }
            case "Password is required" -> {
                Assert.assertEquals(webSteps.getText("loginPasswordIsRequired"), "Password is required", "Validation Failed");
                ExtentReportManager.logPass("Validation matched: Password is required");
            }
            case "Email and Password are required" -> {
                Assert.assertEquals(webSteps.getText("loginInvalidEmailFormat"), "Email is required", "Validation Failed");
                Assert.assertEquals(webSteps.getText("loginPasswordIsRequired"), "Password is required", "Validation Failed");
                ExtentReportManager.logPass("Validation matched: Email and Password are required");
            }
            case "Invalid email address" -> {
                Assert.assertEquals(webSteps.getText("loginInvalidEmailFormat"), "Invalid email address", "Validation Failed");
                ExtentReportManager.logPass("Validation matched: Invalid email address");
            }
            case "Invalid login credentials" -> {
                Assert.assertEquals(webSteps.getText("loginToastMessage"), "Invalid login credentials", "Validation Failed");
                ExtentReportManager.logPass("Validation matched: Invalid login credentials");
            }
            case "Invalid email format and Password is required" -> {
                Assert.assertEquals(webSteps.getText("loginInvalidEmailFormat"), "Invalid email address", "Validation Failed");
                Assert.assertEquals(webSteps.getText("loginPasswordIsRequired"), "Password is required", "Validation Failed");
                ExtentReportManager.logPass("Validation matched: Invalid email format & Password required");
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
            ExtentReportManager.logFail("❌ Test failed: " + result.getThrowable().getMessage());
        } else {
            ExtentReportManager.logPass("✅ Test passed.");
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
