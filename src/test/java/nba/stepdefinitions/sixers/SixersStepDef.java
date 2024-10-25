package nba.stepdefinitions.sixers;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import nba.automationFramework.utilities.JsonUtils;
import nba.context.context;
import nba.sixers.pageobjects.SixersHomePage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;

public class SixersStepDef {

    private static final Logger log = LoggerFactory.getLogger(SixersStepDef.class);
    SoftAssert sa;
    private SixersHomePage sixersHomePage;
    private WebDriver driver;
    private Scenario scenario;
    private String fileName = "SixersApp.json";
    private String objectName = "Homepage_01_001";

    public SixersStepDef(context testContext) {
        this.driver = testContext.getDriver();
        this.scenario = testContext.getScenario();
        this.sixersHomePage = new SixersHomePage(driver);
        sa = new SoftAssert();
    }

    @Given("User is on the sixers home page slide section")
    public void userIsOnTheSixersHomePageSlideSection() {
        sixersHomePage.NavigateToSixersHomePage();
    }

    @Then("User counts the number of slides present below the Tickets Menu")
    public void userCountsTheNumberOfSlidesPresentBelowTheTicketsMenu() {
        int slideCount = sixersHomePage.countSlides();
        System.out.println("Total number of slides: " + slideCount);
        sa.assertEquals(slideCount, 4, "Slides are present on the page.");
    }


    @And("User retrieves the title of each slide and validates with expected data")
    public void userRetrievesTheTitleOfEachSlideAndValidatesWithExpectedData() {
        HashMap<String, String> expectedTitles = JsonUtils.getJsonObject(fileName, objectName);
        boolean result = sixersHomePage.validateSlideTitleWithExpected(expectedTitles);
        sa.assertTrue(result, "Slide titles do not match expected values");
    }

    @And("User counts the duration each slide is playing and validates with the expected duration")
    public void userCountsTheDurationEachSlideIsPlayingAndValidatesWithTheExpectedDuration() {
        HashMap<String, String> expectedDurations = JsonUtils.getJsonObject(fileName, objectName);
        boolean result = sixersHomePage.validateSlideDurationWithExpected(expectedDurations);
        sa.assertTrue(result, "Slide durations do not match expected values");
    }
}
