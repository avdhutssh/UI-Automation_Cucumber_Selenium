package nba.automationFramework.utilities;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BrowserDriverFactory {

    private static final Map<String, Supplier<WebDriver>> BROWSER_MAP = new HashMap<>();

    static {
        BROWSER_MAP.put("chrome", () -> {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            return new ChromeDriver(chromeOptions);
        });
        BROWSER_MAP.put("firefox", () -> {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--start-maximized");
            return new FirefoxDriver(firefoxOptions);
        });
        BROWSER_MAP.put("chromeheadless", () -> {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage", "--remote-allow-origins=*", "--start-maximized");
            return new ChromeDriver(chromeOptions);
        });
        BROWSER_MAP.put("firefoxheadless", () -> {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--headless", "--start-maximized");
            return new FirefoxDriver(firefoxOptions);
        });
    }

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private String browser;
    private Logger log;

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
    }

    public WebDriver createDriver() {
        log.info("Starting the browser: " + browser);

        Supplier<WebDriver> driverSupplier = BROWSER_MAP.getOrDefault(browser, ChromeDriver::new);

        driver.set(driverSupplier.get());
        log.info(browser + " browser launched.");

        return driver.get();
    }
}
