package tests;

import base.TestBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;
import org.testng.Assert;
import pages.QAPage;

public class InsiderCaseTest extends TestBase {
    private static final Logger logger = LogManager.getLogger(CareersPage.class);
    @Test
    public void insiderJobTest() throws InterruptedException {
        HomePage home = new HomePage(driver);
        home.isHomePageOpened();

        home.acceptCookiesIfVisible();
        home.navigateToCareers();


        CareersPage careers = new CareersPage(driver);
        Assert.assertTrue(careers.isCareerPageVisible(), "Career blocks not visible");

        QAPage qaPage = new QAPage(driver);
        qaPage.goToQualityAssuranceLınk();

        qaPage.clickSeeAllJobs();
        System.out.println("See all QA jobs  : texti goruldu");
//        qaPage.acceptQACookie();
        Thread.sleep(10000);

        qaPage.filterJobs();
        Assert.assertTrue(qaPage.verifyJobList(), "Job listings don't match filters");

        qaPage.clickViewRole();
        qaPage.checkCurrentUrl();

        qaPage.closeDriver();
        logger.info("Test completed and driver closed.");
    }
}
