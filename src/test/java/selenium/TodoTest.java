package selenium;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static selenium.TodoUtil.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import support.Browser;

public class TodoTest {

  WebDriver driver = Browser.launch();

  @Before
  public void setup() {
    TodoUtil.driver = driver;
    driver.get("http://testdouble.github.io/todos/");
  }
  
  @After
  public void teardown() {
    driver.quit();
  }

  @Test
  public void createTodo() {
    addTodo("Mow the Lawn");
    
    assertThat(todoAt(0).getText(), is("Mow the Lawn"));
  }
  
  @Test
  public void createTwoTodos() {
    addTodo("Shower");
    addTodo("Get dressed");
    
    assertThat(todoAt(0).getText(), is("Shower"));
    assertThat(todoAt(1).getText(), is("Get dressed"));
  }
  
  @Test
  public void marksATodoDone() {
    addTodo("Eat Food");
    assertThat(isDone(todoAt(0)), is(false));
    
    markTodoDone(todoAt(0));
    
    assertThat(isDone(todoAt(0)), is(true));
  }
  
  @Test
  public void canDeleteATodo() {
    addTodo("Thing");
    
    deleteTodo(todoAt(0));
    
    assertThat(todoAt(0), is(nullValue()));    
  }  

  @Test
  public void clearCompletedTodos() {
    addTodo("Do Stuff");
    markTodoDone(todoAt(0));
    
    clearDoneTodos();
    
    assertThat(todoAt(0), is(nullValue()));
  }  

  
}