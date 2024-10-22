package nba.automationFramework.utilities;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserDriverFactory {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private String browser;
    private Logger log;

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
    }

    public WebDriver createDriver() {
        log.info("Starting the browser: " + browser);

        switch (browser) {
            case "chrome":
                log.info("Launching Chrome browser...");
                driver.set(new ChromeDriver());
                break;

            case "firefox":
                log.info("Launching Firefox browser...");
                driver.set(new FirefoxDriver());
                break;

            case "chromeheadless":
                log.info("Launching Chrome in Headless mode...");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080", "--remote-allow-origins=*");
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefoxheadless":
                log.info("Launching Firefox in Headless mode...");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            default:
                log.info("Unknown browser specified: " + browser + ", defaulting to Chrome.");
                driver.set(new ChromeDriver());
                break;
        }

        driver.get().manage().window().maximize();
        return driver.get();
    }

//    public WebDriver getDriver() {
//        return driver.get();
//    }
}
