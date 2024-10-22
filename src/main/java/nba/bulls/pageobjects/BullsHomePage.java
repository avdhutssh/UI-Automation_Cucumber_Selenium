package nba.bulls.pageobjects;

import nba.automationFramework.utilities.CsvUtils;
import nba.automationFramework.utilities.ElementUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BullsHomePage extends ElementUtils {

    @FindBy(css = "footer a")
    private List<WebElement> footerLinks;

    @FindBy(xpath = "//footer//h2/..//a")
    private List<WebElement> footerSectionLinks;


    public BullsHomePage(WebDriver driver) {
        super(driver);
    }

    public void NavigateToBullsHomePage() {
        openUrl(prop.getProperty("dp2_url"));
    }

    public void scrollToFooter() {
        scrollToBottom();
    }

    public List<String> getAllFooterLinks() {
        List<String> links = new ArrayList<>();
        for (WebElement link : footerLinks) {
            links.add(link.getAttribute("href"));
        }
        return links;
    }

    public String storeFooterLinksInCSV(String fileName) {
        List<String> links = getAllFooterLinks();
        log.info("Total hyperlinks found on footer section: " + links.size());
        File filePath = new File(System.getProperty("user.dir") + File.separator + "test-output" + File.separator + prop.getProperty("browser") + File.separator + fileName);
        return CsvUtils.writeToCsv(links, String.valueOf(filePath));
    }

    public String findDuplicateLinks() {
        Set<String> duplicateLinks = findDuplicates(getAllFooterLinks());
        if (!duplicateLinks.isEmpty()) {
            log.info("Duplicate hyperlinks found: " + duplicateLinks);
        } else {
            log.info("No duplicate hyperlinks found.");
        }
        return String.join("\n", duplicateLinks);
    }
}
