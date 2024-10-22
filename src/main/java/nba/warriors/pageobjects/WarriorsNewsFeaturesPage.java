package nba.warriors.pageobjects;

import nba.automationFramework.utilities.ElementUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WarriorsNewsFeaturesPage extends ElementUtils {

    @FindBy(xpath = "//h3[normalize-space()='NEWS']")
    private WebElement newsHeader;

    @FindBy(xpath = "//h3[normalize-space()='VIDEOS']")
    private WebElement videosHeader;

    @FindBy(xpath = "//a[contains(@href,'videos') and contains(@class,'Tile')]")
    private List<WebElement> nonFeatureVideoTiles;

    @FindBy(xpath = "//a[contains(@href,'videos') and contains(@data-testid,'featured')]")
    private List<WebElement> featureVideoTiles;

    @FindBy(xpath = "//h3[normalize-space(text())='VIDEOS']/../..//div[@data-testid='tile-meta']//span")
    private List<WebElement> videoDays;

    @FindBy(xpath = "(//a[contains(@href,'videos') and contains(@class,'Tile')])[1]")
    private WebElement nonFeatureVideoFirst;

    public WarriorsNewsFeaturesPage(WebDriver driver) {
        super(driver);
    }

    public void waitForNewsAndVideosHeader() {
        waitForElementVisible(newsHeader);
        waitForElementVisible(videosHeader);
        log.info("News and Videos headers are visible.");
        scrollToBottom();
        act.moveToElement(videosHeader).perform();
        waitForElementToBePresent(nonFeatureVideoFirst, 5);
        log.info("Wait till all Videos are visible.");
    }

    public int countTotalVideos() {
        int totalVideos = nonFeatureVideoTiles.size() + featureVideoTiles.size();
        log.info("Total number of videos: " + totalVideos);
        return totalVideos;
    }

    public int countVideosForDaysOrMore(int numDays) {
        int count = 0;
        for (WebElement dayElement : videoDays) {
            String daysText = dayElement.getText().replace("d", "").trim();
            try {
                int day = Integer.parseInt(daysText);
                if (day >= numDays) {
                    count++;
                }
            } catch (NumberFormatException e) {
                log.warn("Unable to parse day text: " + daysText);
            }
        }
        log.info("Number of videos for " + numDays + " or more days: " + count);
        return count;
    }
}
