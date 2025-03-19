package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class baseTest {
    protected WebDriver driver;
    protected String baseUrl;
    protected webSteps webSteps;


    public void loadUrl() throws InterruptedException, IOException {
        // Load the properties file
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        properties.load(fis);
        baseUrl = properties.getProperty("baseUrl");

        // Set up WebDriver and open the URL
        driver = new ChromeDriver();
        webSteps = new webSteps(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(baseUrl);
        Thread.sleep(2000);
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
