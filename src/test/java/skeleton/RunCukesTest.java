package skeleton;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//@CucumberOptions(monochrome = true, tags = "@focus")
@CucumberOptions(monochrome = true, format = "html:target/cucumber")
public class RunCukesTest {
}
