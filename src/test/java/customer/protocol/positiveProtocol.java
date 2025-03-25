package customer.protocol;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.ExtentReportManager;
import utils.baseTest;

import java.awt.*;
import java.io.IOException;

public class positiveProtocol extends baseTest {

    @BeforeSuite
    public void setupReport() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod
    public void setUp() throws IOException, InterruptedException {
        ExtentReportManager.startTest("Add protocol Test", "<b>Login with valid credentials</b>");
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
        webSteps.select("protocolConnectivityDropdown");
        webSteps.click("addProtocolButton");
        Assert.assertEquals("Addon created successfully",webSteps.getText("addProtocolToastMessage"), "Passed");
    }


}
