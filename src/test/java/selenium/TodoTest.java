package selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TodoTest {

  WebDriver driver = new ChromeDriver();

  @Before
  public void setup() {
    driver.get("http://testdouble.github.io/todos/");
  }

  @Test
  public void test() {

  }
}