package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class TodoUtil {
  public static WebDriver driver;
  
  public static WebElement any(String css) {
    return any(driver.findElement(By.cssSelector("html")), css);
  }
  
  public static WebElement any(WebElement parent, String css) {
    try {
      return parent.findElement(By.cssSelector(css));
    } catch(NoSuchElementException e) {
      return null;
    }
  }
  
  public static void addTodo(String text) {
    any("#new-todo").sendKeys(text + "\n");
  }

  public static WebElement todoAt(int index) {
    return any("#todo-list li:nth-child("+(index + 1)+")");
  }
  
  public static void markTodoDone(WebElement todo) {
    any(todo, "input.check").click();    
  }
  
  public static boolean isDone(WebElement todo) {
    return any(todo, ".todo.done") != null; 
  }
  
  public static void deleteTodo(WebElement todo) {
    new Actions(driver)
      .moveToElement(todo)
      .moveToElement(any(todo, ".todo-destroy"))
      .click().build().perform();
  }
  
  public static void clearDoneTodos() {
    any(".todo-clear").click();    
  }
}
