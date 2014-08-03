package skeleton;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, format = {"pretty", "html:target/cucumber", "rerun:target/rerun.txt"})
public class RunCuke {
	
}
