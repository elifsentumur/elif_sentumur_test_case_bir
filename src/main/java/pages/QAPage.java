package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class QAPage {
    private static final Logger logger = LogManager.getLogger(QAPage.class);
    private WebDriver driver;
    private String originalWindow;
    private WebDriverWait wait;

    public QAPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private By seeAllQAJobsLink = By.linkText("See all QA jobs");
    private By acceptQACookie = By.cssSelector("[id=\"wt-cli-accept-all-btn\"]");
    private By locationCloseIcon = By.cssSelector("[data-select2-id=\"3\"]");
    private By locationIstanbulItem = By.cssSelector("[class='select2-results'] > ul > li:nth-of-type(2)");
    private By closeDepartmanIcon = By.cssSelector("[data-select2-id='8']");
    private By qAAssuranceItem = By.cssSelector("[class='select2-results'] > ul > li:nth-of-type(16)");
    private List<WebElement> jobs;
    private By searchResultCardview = By.xpath("//*[@id='jobs-list']/div[2]/div/a");

    public void goToQualityAssuranceLınk() {
        logger.info("Navigating to Quality Assurance page...");
        driver.get("https://useinsider.com/careers/quality-assurance/");
        logger.info("Navigated to Quality Assurance page.");
    }

    public void clickSeeAllJobs() {
        logger.info("Clicking 'See all QA jobs' link...");
        wait.until(ExpectedConditions.elementToBeClickable(seeAllQAJobsLink)).click();
        logger.info("Clicked 'See all QA jobs' link.");
    }

    public void acceptQACookie() {
        logger.info("Accepting QA cookies...");
        wait.until(ExpectedConditions.elementToBeClickable(acceptQACookie)).click();
        logger.info("QA cookies accepted.");
    }

    public void filterJobs() throws InterruptedException {
        try {
            logger.info("Click action started: Clicking location close icon.");
            wait.until(ExpectedConditions.elementToBeClickable(locationCloseIcon)).click();
            logger.info("Location close icon clicked.");

            logger.info("Click action started: Selecting Istanbul location.");
            wait.until(ExpectedConditions.elementToBeClickable(locationIstanbulItem)).click();
            logger.info("Istanbul location selected.");

            logger.info("Click action started: Clicking department close icon.");
            wait.until(ExpectedConditions.elementToBeClickable(closeDepartmanIcon)).click();

            logger.info("Click action started: Selecting Quality Assurance department.");
            wait.until(ExpectedConditions.elementToBeClickable(qAAssuranceItem)).click();
            logger.info("Quality Assurance department selected.");
        } catch (Exception e) {
            logger.error("Error occurred while filtering jobs: " + e.getMessage());
        }
    }

    public boolean verifyJobList() {
        logger.info("Verifying job list...");
        jobs = driver.findElements(By.className("position-list-item"));
        for (WebElement job : jobs) {
            String text = job.getText();
            logger.info("Job text: " + text);
            if (!(text.contains("Quality Assurance") && text.contains("Istanbul, Turkiye"))) {
                logger.warn("Job listing does not match filter: " + text);
                return false;
            }
        }
        logger.info("All job listings match the filter.");
        return true;
    }

    public void clickViewRole() throws InterruptedException {

        WebElement viewRoleButton = wait.until(ExpectedConditions.elementToBeClickable(searchResultCardview));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", viewRoleButton);
        // Önce tıklamadan önce mevcut sekme ID'sini al
        logger.info("Scroll to the View Role button.");
        originalWindow = driver.getWindowHandle();
        logger.info("Original window handle retrieved: " + originalWindow);
        wait.until(ExpectedConditions.elementToBeClickable(viewRoleButton)).click();
        logger.info("View Role button clicked.");
    }

    public void checkCurrentUrl() {
        logger.info("Waiting for the new tab to open...");
// Yeni sekmenin açıldığından emin olmak için bekle
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        logger.info("Switching to the new tab...");
// Tüm sekmeleri al ve yeni sekmeye geç
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                logger.info("Switched to the new tab.");
                break;
            }
        }
    }

    public void closeDriver() {
// (İsteğe bağlı) İşlem bittiğinde orijinal sekmeye geri dön
        try {
            logger.info("Process completed, closing the tab.");
            driver.close();
            driver.switchTo().window(originalWindow);
            logger.info("Switched back to the original tab.");
        } catch (Exception e) {
            logger.error("Error occurred while closing the tab: " + e.getMessage());
        }
    }
}
