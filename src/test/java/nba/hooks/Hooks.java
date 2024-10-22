package nba.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import nba.automationFramework.utilities.BrowserDriverFactory;
import nba.automationFramework.utilities.PropertyReader;
import nba.context.context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    public static PropertyReader prop;
    public final context testContext;
    public WebDriver driver;
    public Logger log;

    public Hooks(context testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp(Scenario scenario) {
        prop = PropertyReader.getInstance();
        log = LogManager.getLogger(this);

        String browser = System.getenv("browserName");
        if (browser == null || browser.isEmpty()) {
            browser = prop.getProperty("browser");
        }
        String isHeadlessEnv = System.getenv("isHeadless");
        boolean isHeadless = (isHeadlessEnv != null && !isHeadlessEnv.isEmpty())
                ? Boolean.parseBoolean(isHeadlessEnv)
                : Boolean.parseBoolean(prop.getProperty("headless"));

        if (isHeadless) {
            browser += "headless";
        }

        BrowserDriverFactory factory = new BrowserDriverFactory(browser, log);
        driver = factory.createDriver();

        testContext.driver = driver;
        testContext.log = log;
        testContext.scenario = scenario;
    }

    @After
    public void tearDown(Scenario scenario) {

        String scenarioName = scenario.getName().replaceAll(" ", "_");
        if (scenario.isFailed()) {
            byte[] srcScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(srcScreenshot, "image/png", scenarioName);
        }

        log.info("Close all browser instance");
        if (driver != null) {
            driver.quit();
        }
    }
}
