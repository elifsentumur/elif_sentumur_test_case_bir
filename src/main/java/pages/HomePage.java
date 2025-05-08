package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private By acceptButton = By.id("wt-cli-accept-all-btn");
    private By companyMenu = By.cssSelector("[class=\"nav-item dropdown\"]:nth-of-type(6) > a:nth-of-type(1)");
    private By careersLink = By.cssSelector("#navbarNavDropdown > ul:nth-child(1) > li:nth-child(6) > div > div.new-menu-dropdown-layout-6-mid-container > a:nth-child(2)");


    // Ana sayfanın yüklendiğini doğrulamak için
    public boolean isHomePageOpened() {
        logger.info("Checking if home page is opened...");
        boolean isOpened = driver.getTitle().contains("Insider");
        if (isOpened) {
            logger.info("Home page opened successfully.");
        } else {
            logger.error("Home page failed to open.");
        }
        return isOpened;
    }

    public void acceptCookiesIfVisible() {
        try {
            WebElement acceptButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(acceptButton));
            acceptButtonElement.click();
            logger.info("Cookies accepted.");
        } catch (NoSuchElementException ignored) {
            // Çerez butonu yoksa görmezden gel
            logger.warn("Cookies accept button not found, skipping.");
        }
    }

    public void navigateToCareers() {
        logger.info("Navigating to Careers page...");
        try {
            WebElement companyMenuElement = wait.until(ExpectedConditions.elementToBeClickable(companyMenu));
            companyMenuElement.click();

            WebElement careersLinkElement = wait.until(ExpectedConditions.elementToBeClickable(careersLink));
            careersLinkElement.click();
            logger.info("Navigated to Careers page.");
        } catch (NoSuchElementException e) {
            // Hataları logla veya uygun bir işlem yap,
            logger.warn("Error while navigating to careers page...");
        }
    }
}
