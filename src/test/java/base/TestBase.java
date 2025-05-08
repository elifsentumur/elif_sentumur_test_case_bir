package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.ScreenshotUtil;

import java.io.FileInputStream;
import java.util.Properties;

public class TestBase {
    protected WebDriver driver;
    protected Properties config;

    @BeforeClass
    public void setupClass() throws Exception {
        config = new Properties();
        FileInputStream fis = new FileInputStream("resources/config.properties");
        config.load(fis);
    }

    @BeforeMethod
    public void setup() {
        String browser = config.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.get(config.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown(org.testng.ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.takeScreenshot(driver, result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

}
