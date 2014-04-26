package skeleton;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Stepdefs {
    Checkout checkout = new Checkout();

    @Given("^the following products:$")
    public void the_following_products(Map<String,Integer> products) throws Throwable {
        checkout.setProducts(products);
    }

    @When("^I sell (\\d+) (.*)?$")
    public void i_sell_espresso(int quantity, String product) throws Throwable {
        checkout.setQuantity(quantity, product);
    }

    @Then("^the total should be £(\\d+)$")
    public void the_total_should_be_£(int expectedTotal) throws Throwable {
        assertEquals("The total is incorrect", expectedTotal, checkout.getTotal());
    }
}
