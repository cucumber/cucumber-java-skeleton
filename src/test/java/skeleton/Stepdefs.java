package skeleton;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

public class Stepdefs {
    @Given("^I have (\\d+) cukes in my belly$")
    public void I_have_cukes_in_my_belly(int cukes) throws Throwable {
        Belly belly = new Belly();
        belly.eat(cukes);
    }
	
	@When("^I wait (\\d+) hour$")
	public void i_wait_hour(int hour) throws Throwable {
	    Belly belly = new Belly();
        belly.wait(hour);    
	}

	@Then("^my belly should growl$")
	public void my_belly_should_growl() throws Throwable {
		Belly belly = new Belly();
		belly.growl();
	}

	@Then("^I will get fat$")
	public void getting_fat() throws Throwable {
		Belly belly = new Belly();
		belly.gettingFat();
	}		
}
