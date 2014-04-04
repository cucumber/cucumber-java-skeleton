package skeleton;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class Stepdefs {
    Checkout checkout = new Checkout();

    @Given("^an espresso costs £(\\d+)$")
    public void an_espresso_costs_£(int price) throws Throwable {
        checkout.setPriceOfEspresso(price);
    }

    @Given("^a croissant costs £(\\d+)$")
    public void a_croissant_costs_£(int price) throws Throwable {
        checkout.setPriceOfCroissant(price);
    }

    @When("^I sell (\\d+) espresso$")
    public void i_sell_espresso(int quantity) throws Throwable {
        checkout.setQuantityOfEspresso(quantity);
    }

    @When("^I sell (\\d+) croissants$")
    public void i_sell_croissants(int quantity) throws Throwable {
        checkout.setQuantityOfCroissant(quantity);
    }

    @Then("^the total should be £(\\d+)$")
    public void the_total_should_be_£(int expectedTotal) throws Throwable {
        assertEquals("The total is incorrect", expectedTotal, checkout.getTotal());
    }
}
