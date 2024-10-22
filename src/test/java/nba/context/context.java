package nba.context;

import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class context {

    public WebDriver driver;
    public Logger log;
    public Scenario scenario;

    public context() {

    }

    public WebDriver getDriver() {
        return driver;
    }

    public Logger getLog() {
        return log;
    }

    public Scenario getScenario() {
        return scenario;
    }
}
