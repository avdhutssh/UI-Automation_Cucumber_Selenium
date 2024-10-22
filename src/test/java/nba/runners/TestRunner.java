package nba.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"nba.stepdefinitions", "nba.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-html-report.html"},
        monochrome = true
//        tags = "@Regression"
)

public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void setUp(ITestContext context) {
        String tags = context.getCurrentXmlTest().getParameter("cucumber.filter.tags");
        System.out.println("Tag set is:  " + tags);
        if (tags != null && !tags.isEmpty()) {
            System.out.println("Tag set is:  " + tags);
            System.setProperty("cucumber.filter.tags", tags);
        }
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}