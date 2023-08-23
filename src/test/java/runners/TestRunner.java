package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/features" }, glue = "stepDefinitions",
plugin = {"pretty", "html:target/cucumber-reports.html"} // Define the output plugin
)

public class TestRunner {
}