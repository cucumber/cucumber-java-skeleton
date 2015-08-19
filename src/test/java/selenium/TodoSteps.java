package selenium;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static selenium.TodoUtil.addTodo;
import static selenium.TodoUtil.todoAt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TodoSteps {

  WebDriver driver;
  
  @After
  public void quitBrowser() {
    driver.quit();
  }
  
  @Given("^am I on the todo page$")
  public void am_I_on_the_todo_page() throws Throwable {
    TodoUtil.driver = driver = new ChromeDriver();     
    driver.get("http://testdouble.github.io/todos/");
  }

  @When("^I type the todo \"([^\"]*)\"$")
  public void i_type_the_todo(String todoText) throws Throwable {
    addTodo(todoText);
  }

  @Then("^todo list item (\\d+) has text \"([^\"]*)\"$")
  public void todo_list_item_has_text(int ordinal, String expected) throws Throwable {
    assertThat(todoAt(ordinal - 1).getText(), is(expected));
  }

}
