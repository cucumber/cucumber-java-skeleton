package skeleton;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by korobitsyna on 5/24/15.
 */
public class ListSteps {

    private ArrayList<String> testList;
    private String testString = "String";

    @Given("I have an empty list")
    public void i_have_an_empty_list() {
        testList = new ArrayList<String>();
    }

    @Given("I have a list with (\\d+) elements")
    public void i_have_a_list_with_elements(int quantity) {
        testList = new ArrayList<String>();
        for(int i = 0; i < quantity; i++) {
            testList.add(testString);
        }
    }

    @When("I add string \"(.*?)\" to a list")
    public void i_add_string_to_list(String s) {
        testList.add(s);
    }

    @When("I remove last element")
    public void i_remove_element() {
        testList.remove(testList.size()-1);
    }

    @Then("List length is (\\d+)")
    public void list_length(int length) {
        assertEquals(length, testList.size());
    }

    @Then("List contains string \"(.*?)\"$")
    public void list_contains_string(String s) {
        assertTrue(testList.contains(s));
    }

}
