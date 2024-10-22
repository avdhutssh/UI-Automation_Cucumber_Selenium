package nba.automationFramework.utilities;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.Iterator;
import java.util.Set;

public class ElementUtils extends BasePage {

    protected WebDriver driver;
    protected Actions act;

    protected ElementUtils(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.act = new Actions(driver);
    }

    protected void clickOnElement(WebElement element) {
        WebElement webElement = waitForElementClickable(element);
        try {
            webElement.click();
            log.info("Clicked on element: " + element.toString());
        } catch (Exception e) {
            log.error("Failed to click on element: " + element.toString() + ". Error: " + e.getMessage());
        }
    }

    protected void clickOnElementJS(WebElement ele) {
        try {
            js.executeScript("arguments[0].click()", ele);
            log.info("Clicked on element using JavaScript: " + ele.toString());
        } catch (Exception e) {
            log.error("Failed to click on element using JavaScript: " + ele.toString() + ". Error: " + e.getMessage());
        }
    }

    protected void clickOnElementAction(WebElement ele) {
        try {
            act.moveToElement(ele).click().perform();
            log.info("Clicked on element using Actions class: " + ele.toString());
        } catch (Exception e) {
            log.error("Failed to click on element using Actions: " + ele.toString() + ". Error: " + e.getMessage());
        }
    }

    protected void switchToWindow(int windowNumber) {
        waitForChildWindow(windowNumber);
        String[] windowHandles = driver.getWindowHandles().toArray(new String[0]);
        try {
            if (windowNumber > windowHandles.length) {
                throw new RuntimeException(String.format(
                        "The specified window number (%s) is greater than the number of windows created (%s).",
                        windowNumber, windowHandles.length));
            }
            driver.switchTo().window(windowHandles[windowNumber - 1]);
            log.info("Switched to window number: " + windowNumber);
        } catch (Exception ex) {
            log.error(String.format("Failed to switch to window number %s. Total open windows: %s.",
                    windowNumber, windowHandles.length), ex);
        }
    }

    protected void switchToWindow(String expectedTitle) {
        String parentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        Iterator<String> windowsIterator = allWindows.iterator();

        while (windowsIterator.hasNext()) {
            String windowHandle = windowsIterator.next();
            if (!windowHandle.equals(parentWindow)) {
                driver.switchTo().window(windowHandle);
                if (getCurrentPageTitle().equals(expectedTitle)) {
                    log.info("Switched to window with title: " + expectedTitle);
                    break;
                }
            }
        }
    }

    protected void enterText(WebElement ele, String text, boolean clear, boolean pressEnter) {
        try {
            if (clear) {
                ele.clear();
            }
            ele.sendKeys(text);
            log.info("Entered text: '" + text + "' in element: " + ele.toString());
            if (pressEnter) {
                ele.sendKeys(Keys.ENTER);
                log.info("Pressed ENTER key after entering text: '" + text + "'");
            }
        } catch (Exception e) {
            log.error("Failed to enter text in element: " + ele.toString() + ". Error: " + e.getMessage());
        }
    }

    protected void enterText(WebElement ele, String text) {
        enterText(ele, text, false, false);  // Reuses the optimized method
    }

    protected void selectDropdownOption(WebElement ele, String type, String selectValue) {
        Select dropdown = new Select(ele);
        try {
            switch (type) {
                case "text":
                    dropdown.selectByVisibleText(selectValue);
                    log.info("Selected dropdown option by text: " + selectValue);
                    break;
                case "value":
                    dropdown.selectByValue(selectValue);
                    log.info("Selected dropdown option by value: " + selectValue);
                    break;
                case "index":
                    dropdown.selectByIndex(Integer.parseInt(selectValue));
                    log.info("Selected dropdown option by index: " + selectValue);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid dropdown selection type");
            }
        } catch (Exception e) {
            log.error("Failed to select dropdown option. Error: " + e.getMessage());
        }
    }

    protected Alert switchToAlert() {
        waitForAlert();
        log.info("Switched to alert");
        return driver.switchTo().alert();
    }

    protected void handleAlert(String expectedText, String prompt) {
        try {
            Alert alert = switchToAlert();
            String actualText = alert.getText();
            Assert.assertEquals(actualText, expectedText);
            log.info("Alert text matched: " + expectedText);
            if ("accept".equalsIgnoreCase(prompt)) {
                alert.accept();
                log.info("Alert accepted");
            } else {
                alert.dismiss();
                log.info("Alert dismissed");
            }
        } catch (Exception e) {
            log.error("Failed to handle alert. Error: " + e.getMessage());
        }
    }

    protected void switchToFrame(WebElement ele) {
        driver.switchTo().frame(ele);
        log.info("Switched to frame: " + ele.toString());
    }

    protected void pressKey(WebElement ele, Keys key) {
        try {
            ele.sendKeys(key);
            log.info("Pressed key: " + key.name() + " on element: " + ele.toString());
        } catch (Exception e) {
            log.error("Failed to press key on element: " + ele.toString() + ". Error: " + e.getMessage());
        }
    }

    protected void pressKeyWithActions(Keys key) {
        try {
            log.info("Pressing " + key.name() + " using Actions class");
            act.sendKeys(key).perform();
        } catch (Exception e) {
            log.error("Failed to press key using Actions class. Error: " + e.getMessage());
        }
    }

    protected void scrollToBottom() {
        try {
            js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
            log.info("Scrolled to bottom of the page");
        } catch (Exception e) {
            log.error("Failed to scroll to bottom. Error: " + e.getMessage());
        }
    }

    protected void hoverOverElement(WebElement element) {
        try {
            act.moveToElement(element).perform();
            log.info("Hovered over element: " + element.toString());
        } catch (Exception e) {
            log.error("Failed to hover over element: " + element.toString() + ". Error: " + e.getMessage());
        }
    }

    public void setZoomLevel(int percentage) {
        js.executeScript("document.body.style.zoom='" + percentage + "%'");
    }
}
