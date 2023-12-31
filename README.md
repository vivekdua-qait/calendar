**Java Cucumber JUnit Framework**
This repository contains a Java-based test automation framework using Cucumber and JUnit for behavior-driven development and testing.


**Introduction**
The Java Cucumber JUnit Framework is designed to facilitate the collaboration between developers, testers, and business stakeholders by providing a common language for writing and executing tests. It utilizes Cucumber for writing feature files in a human-readable format and JUnit for executing the test scenarios.

**Setup**
Clone this repository to your local machine.
Ensure you have Java JDK and Maven installed.
Install the required dependencies by running **mvn clean install**.

**Features**
This framework supports the following features:

**1. Behavior-Driven Development :** Write test scenarios in Gherkin language using feature files.

**2. Step Definitions :** Map Gherkin steps to Java code using step definition classes.

**3. Hooks :** Set up pre and post-test actions using Cucumber hooks.

**4. Assertions :** Utilize assertion libraries to validate expected outcomes.

**5. Reporting :** Generate detailed test reports for easy analysis.

**Running test :**
Go to your project directory from terminal and hit following commands **mvn test** 


**Step Definitions**
Step definitions are Java methods that match the steps in your feature files. They execute the actual code for your test scenarios.

**Example step definition:**

import io.cucumber.java.en.When;

public class ExampleStepDefinitions {

    @When("the user enters {string} in the search field")
    public void enterTextInSearchField(String searchText) {
        // Code to interact with the application
    }
}

**Runner**
The test runner class is used to configure and execute your Cucumber tests.

Example test runner:
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "stepdefinitions",
    tags = "@smoke",
    plugin = {"pretty", "html:target/cucumber-reports"}
)
public class TestRunner {
}


**Reports**
After running the tests, you can find the test reports in the target/cucumber-reports directory. These reports provide insights into test execution and results.
