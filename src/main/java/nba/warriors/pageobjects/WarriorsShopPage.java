package nba.warriors.pageobjects;

import nba.automationFramework.utilities.ElementUtils;
import nba.automationFramework.utilities.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WarriorsShopPage extends ElementUtils {

    @FindBy(xpath = "//span[normalize-space(text())='Jackets']")
    private WebElement jacketsRadioBtn;

    @FindBy(xpath = "//div[@class='product-card row']")
    private List<WebElement> productCards;

    @FindBy(xpath = "(//li[@class='next-page']//i)[1]")
    private WebElement nextArrowSymbol;

    @FindBy(css = "li[class='next-page disabled']")
    private WebElement nextArrowSymbolDisabled;

    public WarriorsShopPage(WebDriver driver) {
        super(driver);
    }

    public void SelectsALlJackets() {
        clickOnElement(jacketsRadioBtn);
        log.info("Clicked on Jackets radio button");
    }

    public byte[] extractAndStoreJacketData(String fileName) {
        File filePath = new File(System.getProperty("user.dir") + File.separator + "test-output" + File.separator + prop.getProperty("browser") + File.separator + fileName);
        FileUtils.createNewDirectoryAndFile(filePath);

        int productCount = 1;
        boolean hasNextPage = true;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {

            while (hasNextPage) {
                if (isElementDisplayed(nextArrowSymbolDisabled)) {
                    log.info("Pagination completed or no more pages.");
                    hasNextPage = false;
                    break;
                }
                log.info("Extracting product information from the current page");
                for (WebElement productCard : productCards) {
                    String title = "";
                    String price = "";
                    String topSeller = "";

                    try {
                        title = productCard.findElement(By.xpath(".//div[@class='product-card-title']/a")).getText();
                    } catch (Exception e) {
                        log.error("Title not found for product: " + e.getMessage());
                    }

                    try {
                        price = productCard.findElement(By.xpath(".//div[@class='price-card']//span[@class='sr-only']")).getText();
                    } catch (Exception e) {
                        log.error("Price not found for product: " + e.getMessage());
                    }

                    try {
                        topSeller = productCard.findElement(By.xpath(".//div[@class='product-vibrancy top-seller-vibrancy']/span[@class='top-seller-vibrancy-message']")).getText();
                    } catch (Exception e) {
                        topSeller = "";
                    }

                    writer.write("Product count: " + productCount + "\n");
                    writer.write("Product title: " + title + "\n");
                    writer.write("Product price: " + price + "\n");
                    writer.write("Product topSeller: " + topSeller + "\n\n");

                    productCount++;
                }
                try {
                    if (isElementVisible(nextArrowSymbol)) {
                        clickOnElement(nextArrowSymbol);
                        waitForPageLoad();
                        waitForElementVisible(jacketsRadioBtn);
                        refreshProductList();
                    } else {
                        log.info("No more pages to navigate, reached the last page.");
                        hasNextPage = false;
                    }
                } catch (Exception e) {
                    log.warn("Pagination completed or no more pages.");
                    hasNextPage = false;
                }
            }

        } catch (IOException e) {
            log.error("Error writing to file: " + e.getMessage());
        }
        try {
            return Files.readAllBytes(Paths.get(filePath.getAbsolutePath()));
        } catch (IOException e) {
            log.error("Error reading file content: " + e.getMessage());
            return null;
        }
    }

    private void refreshProductList() {
        productCards = driver.findElements(By.xpath("//div[@class='product-card row']"));
    }
}