package skeleton;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

public class Stepdefs {
	@Given("^I have (\\d+) cukes in my belly$")
	public void I_have_cukes_in_my_belly(int cukes) throws Throwable {
		System.out.println("In I have (\\d+) cukes in my belly");
		return;
	}
	@When("^I wait (\\d+) hour$")
	public void i_wait_hour(int arg1) throws Throwable {
		System.out.println("In I wait (\\d+) hour");
		return;
	}

	@Then("^my belly should growl$")
	public void my_belly_should_growl() throws Throwable {
		System.out.println("In my belly should growl");
		return;
	}
}
