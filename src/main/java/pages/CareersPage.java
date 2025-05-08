package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CareersPage {

    private static final Logger logger = LogManager.getLogger(CareersPage.class);  // L
    WebDriver driver;
    WebDriverWait wait;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private By locations = By.id("career-our-location");
    private By teams = By.id("career-find-our-calling");
    private By lifeAtInsider = By.xpath("//h2[contains(text(),'Life at Insider')]");

    public boolean isCareerPageVisible() {
        logger.info("Checking if the career page is visible...");
        try {
            WebElement locationsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locations));
            WebElement teamsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(teams));
            WebElement lifeAtInsiderElement = wait.until(ExpectedConditions.visibilityOfElementLocated(lifeAtInsider));

            boolean isVisible = locationsElement.isDisplayed() && teamsElement.isDisplayed() && lifeAtInsiderElement.isDisplayed();

            if (isVisible) {
                logger.info("Career page elements are visible.");
            } else {
                logger.warn("Some career page elements are not visible.");
            }

            return isVisible;
        } catch (Exception e) {
            logger.error("Error occurred while checking career page visibility: " + e.getMessage());
            return false;
        }
    }
}
