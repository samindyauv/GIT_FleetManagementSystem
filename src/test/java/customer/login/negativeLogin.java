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
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUp() throws IOException, InterruptedException {
        ExtentReportManager.startTest("Negative Login Test", "<b>Login with invalid credentials</b>");
        loadUrl();
        ExtentReportManager.testSteps("Opened the application URL");
        webSteps.waiting();
    }

    @DataProvider(name="loginData")
    public Object [][] negativeLoginDataProvider(){

        String [][] data = {
                {"Verify that the user cannot log in with a valid email and an invalid password","customerdemo@gmail.com","invalidPassword","Invalid login credentials"},
                {"Verify that the user cannot log in with a valid email and an empty password","customerdemo@gmail.com","","Password is required"},
                {"Verify that the user cannot log in with an invalid email and a valid password","invalidemail@gmail.com","123456@Cd","Invalid login credentials"},
                {"Verify that the user cannot log in with an invalid email and an empty password","invalidemail@gmail.com","","Password is required"},
                {"Verify that the user cannot log in with an invalid email and an invalid password","invalidemail@gmail.com","invalidPassword","Invalid login credentials"},
                {"Verify that the user cannot log in with an invalid email format and a valid password","invalidemail@","123456@Cd","Invalid email format"},
                {"Verify that the user cannot log in with an invalid email format and an empty password","invalidemail@","","Invalid login credentials"},
                {"Verify that the user cannot log in with an invalid email format and an invalid password","invalidemail@","invalidPassword","Invalid login credentials"},
                {"Verify that the user cannot log in with an empty email and a valid password","","123456@Cd","Email is required"},
                {"Verify that the user cannot log in with an empty email and an invalid password","","invalidPassword","Email is required"},
                {"Verify that the user cannot log in with an empty email and an empty password","","","Email and Password are required"}
        };
        return data;
    }
    @Test(dataProvider = "loginData")
    @Name("Negative Login Test Cases")
    public void loginWithInvalidCredentials(String testCase,String email, String password, String expValidation) throws InterruptedException {

        ExtentReportManager.testSteps("<b><font color='blue'>Test Case : </font></b>"+ testCase);
        ExtentReportManager.testSteps("<b>Email : </b> " + (email.isEmpty() ? "[Empty]" : email));
        ExtentReportManager.testSteps("<b>Password : </b>" + (password.isEmpty() ? "[Empty]" : password));

        webSteps.type(email,"customerEmailField");
        webSteps.type(password,"customerPasswordField");
        webSteps.click("customerLoginButton");

        ExtentReportManager.testSteps("<b> Checking expected validation : </b>" + expValidation);

        switch (expValidation) {
            case "Email is required" -> {
                Assert.assertEquals(webSteps.getText("loginEmailIsRequired"), "Email is required", "Validation Failed");
            }
            case "Password is required" -> {
                Assert.assertEquals(webSteps.getText("loginPasswordIsRequired"), "Password is required", "Validation Failed");
            }
            case "Email and Password are required" -> {
                Assert.assertEquals(webSteps.getText("loginInvalidEmailFormat"), "Email is required", "Validation Failed");
                Assert.assertEquals(webSteps.getText("loginPasswordIsRequired"), "Password is required", "Validation Failed");
            }
            case "Invalid email address" -> {
                Assert.assertEquals(webSteps.getText("loginInvalidEmailFormat"), "Invalid email address", "Validation Failed");
            }
            case "Invalid login credentials" -> {
                Assert.assertEquals(webSteps.getText("loginToastMessage"), "Invalid login credentials", "Validation Failed");
            }
            case "Invalid email format and Password is required" -> {
                Assert.assertEquals(webSteps.getText("loginInvalidEmailFormat"), "Invalid email address", "Validation Failed");
                Assert.assertEquals(webSteps.getText("loginPasswordIsRequired"), "Password is required", "Validation Failed");
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
