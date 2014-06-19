package skeleton;

import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.fail;

// Cucumber creates a new instance of this class for every scenario
public class Stepdefs {

    WebDriver browser;

    @Before
    public void resetState() {
        browser = new FirefoxDriver();
        browser.get("http://squeaker.herokuapp.com/reset");
    }

    @After
    public void closeBrowser(Scenario scenario) {
        byte[] screenshot = ((TakesScreenshot) browser).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");
        browser.close();
    }

    @Given("^I'm not logged in$")
    public void i_m_not_logged_in() {
    }

    @When("^I visit the homepage$")
    public void i_visit_the_homepage() throws Throwable {
        browser.get("http://squeaker.herokuapp.com/");
    }

    @Then("^I should see \"(.*?)\"$")
    public void i_should_see(String expected) {
        shouldSee(expected);
    }

    @Given("^\"(.*?)\" is not registered$")
    public void is_not_registered(String userName) {
        // This is implicitly true since we reset the database in the Before hook
    }

    @When("^\"(.*?)\" registers$")
    public void registers(String userName) throws Throwable {
        registerAndLogIn(userName);
    }

    @Then("^\"(.*?)\" should be logged in$")
    public void should_be_logged_in(String userName) {
        shouldSee("Hello " + userName);
    }

    @Given("^\"(.*?)\" has logged in$")
    public void has_logged_in(String userName) throws Throwable {
        registerAndLogIn(userName);
    }

    @When("^\"(.*?)\" squeaks \"(.*?)\"$")
    public void squeaks(String username, String squeaks) {
        browser.findElement(By.id("message_content")).sendKeys(squeaks);
        browser.findElement(By.id("new_message")).submit();
    }

    @Then("^I should see:$")
    public void i_should_see(DataTable expectedSqueaks) {
        List<List<String>> actualTweets = findActualSqueaks();
        expectedSqueaks.diff(actualTweets);
    }

    // Helper methods

    private void shouldSee(String expectedText) {
        String textOnPage = browser.findElement(By.tagName("body")).getText();
        boolean contains = textOnPage.contains(expectedText);
        if (!contains) {
            fail(String.format("Couldn't find [%s] on page:\n%s", expectedText, textOnPage));
        }
    }

    private void registerAndLogIn(String userName) {
        browser.findElement(By.linkText("create an account")).click();
        browser.findElement(By.id("user_username")).sendKeys(userName);
        // There are two buttons named "commit" on the page ("Search" and "Create My Account")
        // Therefore, looking up the button by name won't work. We have to resort to xpath
        // browser.findElement(By.name("commit")).click();
        browser.findElement(By.xpath("//input[@value='Create My Account']")).click();
    }

    private List<List<String>> findActualSqueaks() {
        List<List<String>> actualTweets = new ArrayList<List<String>>();
        List<WebElement> lis = browser.findElements(By.cssSelector("ul#messages li"));
        for (WebElement li : lis) {
            String[] nameAndMessage = li.getText().split(":");
            actualTweets.add(asList(nameAndMessage));
        }
        return actualTweets;
    }

}
