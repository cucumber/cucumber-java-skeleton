package skeleton;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Lyanguzov
 */
public class StringSteps {
    private String myString;

    @Given("^I have a string \"(.*?)\"$")
    public void i_have_a_string(String str) {
        myString = str;
    }

    @Then("^char at (\\d+) is '(.)'$")
    public void char_at_is_a(int index, char expectedChar) {
        assertEquals(expectedChar, myString.charAt(index));
    }

    @When("^I concatenate it with string \"(.*?)\"$")
    public void i_concat_it_with_string(String substring) {
        myString = myString.concat(substring);
    }

    @Then("^result string is \"(.*?)\"$")
    public void result_string_is(String expectedString) {
        assertEquals(expectedString, myString);
    }


}
