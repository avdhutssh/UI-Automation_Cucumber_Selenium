package nba.stepdefinitions.bulls;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import nba.bulls.pageobjects.BullsHomePage;
import nba.context.context;
import org.openqa.selenium.WebDriver;

public class BullsStepDef {
    private BullsHomePage bullsHomePage;
    private String filename = "footerLinks.csv";
    private WebDriver driver;
    private Scenario scenario;

    public BullsStepDef(context testContext) {
        this.driver = testContext.getDriver();
        this.scenario = testContext.getScenario();
        this.bullsHomePage = new BullsHomePage(driver);
    }

    @Given("User is on the bulls home page footer section")
    public void userIsOnTheBullsHomePageFooterSection() {
        bullsHomePage.NavigateToBullsHomePage();
        bullsHomePage.scrollToFooter();
    }

    @Then("User finds and stores all the footer hyperlinks in a CSV file")
    public void userFindsAndStoresAllTheFooterHyperlinksInACSVFile() {
        bullsHomePage.storeFooterLinksInCSV(filename);
    }

    @And("User reports the duplicate hyperlinks if present")
    public void userReportsTheDuplicateHyperlinksIfPresent() {
        String footerDuplicateLinks = bullsHomePage.findDuplicateLinks();
        scenario.attach(footerDuplicateLinks.getBytes(), "text/plain", "Duplicate Hyperlinks");
    }
}
