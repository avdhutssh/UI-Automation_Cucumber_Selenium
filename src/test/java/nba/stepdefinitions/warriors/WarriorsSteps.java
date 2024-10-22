package nba.stepdefinitions.warriors;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nba.context.context;
import nba.warriors.pageobjects.WarriorsHomePage;
import nba.warriors.pageobjects.WarriorsNewsFeaturesPage;
import nba.warriors.pageobjects.WarriorsShopPage;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class WarriorsSteps {

    private String outputFileName = "jackets-info.txt";
    private WebDriver driver;
    private Logger log;
    private Scenario scenario;
    private WarriorsHomePage warriorsHomePage;
    private WarriorsShopPage warriorsShopPage;
    private WarriorsNewsFeaturesPage warriorsNewsFeaturesPage;


    public WarriorsSteps(context testContext) {
        this.driver = testContext.getDriver();
        this.log = testContext.getLog();
        this.scenario = testContext.getScenario();
        warriorsHomePage = new WarriorsHomePage(driver);
    }


    @Given("User is on the warriors home page")
    public void user_is_on_the_warriors_home_page() {
        warriorsHomePage.NavigateToWarriorsHomePage();
        warriorsHomePage.handleTrustPolicyIfVisible();
        warriorsHomePage.handlePopUpIfPresent();
    }

    @When("User navigates to the Shop Menu")
    public void user_navigates_to_the_shop_menu() {
        warriorsShopPage = warriorsHomePage.goToMensShop();
    }

    @When("User selects Men's Jackets")
    public void user_selects_mens_jackets() {
        warriorsShopPage.SelectsALlJackets();
    }

    @Then("User finds and stores all Jackets information in a text file")
    public void user_finds_and_stores_all_jackets_information_in_a_text_file() throws IOException {
        byte[] fileContent = warriorsShopPage.extractAndStoreJacketData(outputFileName);
        scenario.attach(fileContent, ".txt", "JacketsInfo");
    }

    @When("User navigates to News Features sections")
    public void user_navigates_to_news_features_sections() {
        warriorsNewsFeaturesPage = warriorsHomePage.goToNewsFeature();
        warriorsNewsFeaturesPage.waitForNewsAndVideosHeader();
    }

    @Then("User counts the total number of video feeds")
    public void user_counts_the_total_number_of_video_feeds() {
        int totalVideos = warriorsNewsFeaturesPage.countTotalVideos();
        scenario.log("Total number of video feeds: " + totalVideos);
    }

    @Then("User counts the number of video feeds present for {int} or more days")
    public void user_counts_the_number_of_video_feeds_present_for_or_more_days(Integer days) {
        int videoCount = warriorsNewsFeaturesPage.countVideosForDaysOrMore(days);
        scenario.log("Number of video feeds present for " + days + " or more days: " + videoCount);
    }
}
