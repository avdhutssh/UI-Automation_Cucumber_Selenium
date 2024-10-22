package nba.sixers.pageobjects;

import nba.automationFramework.utilities.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SixersHomePage extends ElementUtils {
    @FindBy(xpath = "//div[contains(@class,'tileHeroStoriesButtonTitle')]")
    private List<WebElement> slidesTitle;

    @FindBy(xpath = "//div[contains(@class,'tileHeroStoriesMeta')]//span")
    private List<WebElement> slidesDuration;

    @FindBy(xpath = "(//div[contains(@class,'tileImageOverlay')])[1]")
    private WebElement tileSection;


    public SixersHomePage(WebDriver driver) {
        super(driver);
    }

    public void NavigateToSixersHomePage() {
        openUrl(prop.getProperty("dp1_url"));
        waitForElementVisible(tileSection);
    }

    public int countSlides() {
        return slidesTitle.size();
    }

    public List<String> getSlideTitles() {
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < slidesTitle.size() - 1; i++) {
            String title = slidesTitle.get(i).getText();
            log.info("Title for slide " + (i + 1) + " :" + title);
            titles.add(title);
        }
        return titles;
    }

    public List<String> getSlideDurations() {
        List<String> durations = new ArrayList<>();

        for (int i = 0; i < slidesTitle.size() - 1; i++) {
            String title = slidesTitle.get(i).getText();
            log.info("Processing slide: " + title);
            try {
                WebElement durationElement = slidesTitle.get(i).findElement(By.xpath(".//following-sibling::div//time"));
                String duration = durationElement.getText();
                log.info("Duration found for slide '" + title + "': " + duration);
                durations.add(duration);
            } catch (NoSuchElementException e) {
                log.warn("Duration not mentioned for slide: " + title);
                durations.add("");
            }
        }
        return durations;
    }

    public boolean validateSlideTitleWithExpected(HashMap<String, String> expectedTitles) {
        List<String> actualTitles = getSlideTitles();

        for (int i = 0; i < actualTitles.size(); i++) {
            String expectedTitle = expectedTitles.get("slide" + (i + 1));
            String actualTitle = actualTitles.get(i);

            if (!actualTitle.equals(expectedTitle)) {
                log.error("Mismatch found in slide " + (i + 1) + ": Expected [" + expectedTitle + "], but found [" + actualTitle + "]");
                return false;
            } else {
                log.info("Slide " + (i + 1) + " matches: " + actualTitle);
            }
        }

        log.info("All slide titles match the expected data.");
        return true;
    }

    public boolean validateSlideDurationWithExpected(HashMap<String, String> expectedDurations) {
        List<String> actualDurations = getSlideDurations();
        log.info("Actual Duration List: " + actualDurations + " : " + actualDurations.size());
        for (int i = 0; i < actualDurations.size(); i++) {
            String expectedDurationKey = "slide" + (i + 1) + "Duration";
            String expectedDuration = expectedDurations.get(expectedDurationKey);
            String actualDuration = actualDurations.get(i);

            if (!actualDuration.equals(expectedDuration)) {
                log.error("Mismatch found in slide " + (i + 3) + " duration: Expected [" + expectedDuration + "], but found [" + actualDuration + "]");
                return false;
            } else {
                log.info("Slide " + (i + 3) + " duration matches: " + actualDuration);
            }
        }

        log.info("All slide durations match the expected data.");
        return true;
    }

}
