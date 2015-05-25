package skeleton;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class Stepdefs {
    private Belly belly;

    @Before
    public void doBefore(){
        belly = new Belly();
    }

    @Given("^I have (\\d+) cukes in my belly$")
    public void I_have_cukes_in_my_belly(int cukes) {
        belly.eat(cukes);
    }

    @When("^I eat (\\d+) more cukes$")
    public void eatCukes(int moreCukes) {
        belly.eat(moreCukes);
    }

    @Then("^it should be (\\d+) cukes in my belly$")
    public void checkCukes(int expectedCukes) {
        assertEquals("Where is my cukes?", expectedCukes, belly.getCukes());
    }

}
