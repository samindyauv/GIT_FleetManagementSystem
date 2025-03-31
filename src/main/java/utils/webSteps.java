package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static dataProviders.repositoryFileReader.constructElement;
import static dataProviders.repositoryFileReader.findElementRepo;

public class webSteps {
    protected static WebDriver driver;
    private final String email;
    private final String password;

    public webSteps(WebDriver driver) {
        webSteps.driver = driver;

        // Load email and password from properties file
        Properties properties = propertyLoader.loadProperties("src/main/resources/dataset.properties");
        this.email = properties.getProperty("email");
        this.password = properties.getProperty("password");
    }

    public void login() throws InterruptedException {
        waiting();
        type(email, "customerEmailField");
        type(password, "customerPasswordField");
        click("customerLoginButton");
        waiting();
    }

    // Common method to type text into an input field
    public void type(String text, String locator) throws InterruptedException {
        By xpath = constructElement(findElementRepo(locator));
        WebElement inputField = driver.findElement(xpath);
        inputField.clear();
        inputField.sendKeys(text);
        waiting();
    }


    // Common method to click an element
    public void click(String locator) throws InterruptedException {
        By xpath = constructElement(findElementRepo(locator));
        WebElement button =  driver.findElement(xpath);
        button.click();
        waiting();
    }

    // Common method to get text from an element
    public String getText(String locator) {
        By xpath = constructElement(findElementRepo(locator));
        return driver.findElement(xpath).getText();
    }

    // Common method to wait 1000ms
    public void waiting() throws InterruptedException {
        Thread.sleep(500);
    }

    public void select(String locator) throws InterruptedException, AWTException {
        By xpath = constructElement(findElementRepo(locator));
        click(locator);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        waiting();
    }

    public void select2(String locator) throws InterruptedException, AWTException {
        By xpath = constructElement(findElementRepo(locator));
        click(locator);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        waiting();
    }
    public String generateRandomProtocolName() {
        String randomProtocolName = "Protocol_T" + ThreadLocalRandom.current().nextInt(0, 100);
        return randomProtocolName;
    }
    public String generateRandomProtocolModelName() {
        String randomProtocolModelName = "Model_T" + ThreadLocalRandom.current().nextInt(0, 100);
        return randomProtocolModelName;
    }
    public String generateRandomProtocolProtocol() {
        String randomProtocolProtocol = "GT_T" + ThreadLocalRandom.current().nextInt(0, 100);
        return randomProtocolProtocol;
    }
}
