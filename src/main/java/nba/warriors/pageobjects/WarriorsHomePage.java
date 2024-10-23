package nba.warriors.pageobjects;

import nba.automationFramework.utilities.ElementUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WarriorsHomePage extends ElementUtils {

    @FindBy(css = "[role='alertdialog']")
    private WebElement trustPolicySection;

    @FindBy(xpath = "//button[normalize-space(text())='I Accept']")
    private WebElement acceptPolicyBtn;

    @FindBy(xpath = "//div[normalize-space(text())='x']")
    private WebElement closeSymbol;

    @FindBy(xpath = "//*[@role='menubar']//span[normalize-space(text())='Shop']")
    private WebElement shopMenuBar;

    @FindBy(xpath = "//a[@title=\"Men's\"]")
    private WebElement mensLink;

    @FindBy(xpath = "//li/a[normalize-space()='...']")
    private WebElement menuBarDropDown;

    @FindBy(xpath = "//li[@role='menuitem']//li/a[normalize-space()='News & Features']")
    private WebElement newsFeatureLink;

    public WarriorsHomePage(WebDriver driver) {
        super(driver);
    }

    public void NavigateToWarriorsHomePage() {
        openUrl(prop.getProperty("cp_url"));
    }

    public void handleTrustPolicyIfVisible() {
        if (isElementVisible(trustPolicySection, 15)) {
            log.info("Trust policy section is visible, clicking on Accept");
            clickOnElement(acceptPolicyBtn);
        } else {
            log.info("Trust policy section is not visible, skipping...");
        }
    }

    public void handlePopUpIfPresent() {
        if (isElementVisible(closeSymbol)) {
            log.info("Close symbol is visible, clicking on it");
            clickOnElement(closeSymbol);
        } else {
            log.info("No pop-up found, skipping...");
        }
    }

    public WarriorsShopPage goToMensShop() {
        log.info("Hovering over Shop menu");
        hoverOverElement(shopMenuBar);
        log.info("Clicking on Men's link");
        try {
            clickOnElement(mensLink);
        } catch (Exception e) {
            clickOnElementJS(mensLink);
        }


        log.info("Switching to new window for Warriors Shop page");
        switchToWindow(2);
        return new WarriorsShopPage(driver);
    }

    public WarriorsNewsFeaturesPage goToNewsFeature() {
        setZoomLevel(75);
        waitForElementVisible(menuBarDropDown);
        log.info("Hovering over menu bar dropdown and Click on News & Feature link");
        act.moveToElement(menuBarDropDown).click(newsFeatureLink).perform();
        return new WarriorsNewsFeaturesPage(driver);
    }

}
