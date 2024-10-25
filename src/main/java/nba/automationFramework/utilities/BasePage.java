package nba.automationFramework.utilities;

import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BasePage {
    public static PropertyReader prop;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger log;
    protected JavascriptExecutor js;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.log = LogManager.getLogger(this);
        this.js = (JavascriptExecutor) driver;
        prop = PropertyReader.getInstance();
        PageFactory.initElements(driver, this);
    }

    public static Set<String> findDuplicates(List<String> data) {
        Set<String> uniqueItems = new HashSet<>();
        Set<String> duplicates = new HashSet<>();
        for (String item : data) {
            if (!uniqueItems.add(item)) {
                duplicates.add(item);
            }
        }
        return duplicates;
    }

    protected WebElement waitForElementClickable(WebElement ele) {
        WebElement webElement = null;
        try {
            webElement = wait.until(ExpectedConditions.elementToBeClickable(ele));
        } catch (TimeoutException e) {
            log.error("Timeout occurred while waiting for element to be clickable: " + e.getMessage());
            throw new NoSuchElementException("Element not found: " + ele);
        }
        return webElement;
    }

    protected WebElement waitForElementVisible(WebElement ele) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(ele));
        } catch (TimeoutException e) {
            log.error("Timeout occurred while waiting for element to be visible: " + e.getMessage());
            throw new NoSuchElementException("Element not found or not visible within the timeout: " + ele);
        }
    }

    protected boolean isElementVisible(WebElement element) {
        try {
            return waitForElementVisible(element) != null;
        } catch (Exception e) {
            log.warn("Element not visible: " + element.toString());
            return false;
        }
    }

    protected WebElement waitForElementVisible(WebElement ele, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        try {
            return customWait.until(ExpectedConditions.visibilityOf(ele));
        } catch (TimeoutException e) {
            log.error("Timeout occurred while waiting for element to be visible for " + timeoutInSeconds + " seconds: " + e.getMessage());
            throw new NoSuchElementException("Element not found or not visible within the timeout: " + ele);
        }
    }

    protected boolean isElementVisible(WebElement element, int timeoutInSeconds) {
        try {
            return waitForElementVisible(element, timeoutInSeconds) != null;
        } catch (Exception e) {
            log.warn("Element not visible within " + timeoutInSeconds + " seconds: " + element.toString());
            return false;
        }
    }

    protected Boolean waitForElementInvisible(WebElement ele) {
        Boolean webElement = null;
        try {
            webElement = wait.until(ExpectedConditions.invisibilityOf(ele));
        } catch (TimeoutException e) {
            log.error("Timeout occurred while waiting for element to be invisible: " + e.getMessage());
        }
        return webElement;
    }

    protected boolean waitForTextToBePresentInElement(WebElement element, String text) {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException e) {
            System.err.println("Timeout occurred while waiting for text to be present in element: " + e.getMessage());
            return false;
        }
    }

    protected boolean waitForElementsToAppearWithPolling(WebElement ele, int maxRetries, int pollingSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(maxRetries * pollingSeconds))
                .pollingEvery(Duration.ofSeconds(pollingSeconds))
                .ignoring(NoSuchElementException.class, IndexOutOfBoundsException.class);

        return wait.until(driver -> {
            try {
                return isElementVisible(ele);
            } catch (Exception e) {
                log.warn("Retrying... " + ele + " not found.");
                driver.navigate().refresh();
                return false;
            }
        });
    }

    protected boolean waitForElementToBeDisplayed(WebElement ele, int timeoutInSeconds) {
        log.info("Waiting for element to be displayed: " + ele.toString());
        int attempts = 0;
        while (attempts < timeoutInSeconds) {
            try {
                if (ele.isDisplayed()) {
                    log.info("Element is displayed: " + ele.toString());
                    return true;
                }
            } catch (NoSuchElementException e) {
                log.debug("Element not found: " + ele + ", attempt: " + attempts);
            }
            try {
                Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.error("InterruptedException during wait", e);
            }
            attempts++;
        }
        log.error("Element not displayed after " + timeoutInSeconds + " seconds: " + ele.toString());
        return false;
    }

    protected void waitForElementToBePresent(WebElement ele, int timeoutInSeconds) {
        log.info("Waiting for element to be displayed: " + ele.toString());
        long endTime = System.nanoTime() + timeoutInSeconds * 1_000_000_000L;
        boolean isDisplayed = false;
        while (System.nanoTime() < endTime) {
            isDisplayed = ele.isDisplayed();
        }
        if (!isDisplayed) {
            throw new NoSuchElementException("No such element is present");
        }
    }

    protected void waitForPageLoad() {
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
        log.info("Page fully loaded");
    }

    protected void waitForChildWindow(int windowNumber) {
        try {
            wait.until(ExpectedConditions.numberOfWindowsToBe(windowNumber));
        } catch (TimeoutException e) {
            System.err.println("Timeout occurred while waiting for the child window: " + e.getMessage());
        }

    }

    protected void waitForAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException e) {
            System.err.println("Timeout occurred while waiting for the alert: " + e.getMessage());
        }

    }

    protected boolean isElementDisplayed(WebElement ele) {
        try {
            return ele.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            log.info("Element not visible or does not exist: " + ele.toString());
            return false;
        }
    }

    protected void openUrl(String url) {
        driver.get(url);
    }

    protected String getCurrentPageTitle() {
        return driver.getTitle();
    }

}
