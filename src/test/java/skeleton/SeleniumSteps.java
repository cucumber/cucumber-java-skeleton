package skeleton;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexey Lyanguzov
 */
public class SeleniumSteps {
    private static final String BASE_URL = "http://localhost:9944/web";
    private static final String HOME_PAGE_URL = BASE_URL + "/home";
    private static final String ABOUT_PAGE_URL = BASE_URL + "/about";
    private static final int LOAD_TIMEOUT = 5;
    private static final int WAIT_TIMEOUT = 2;
    public static final int DEMO_TIMEOUT = 1000;
    private static final boolean DO_DEMO_DELAY = true;
    private WebDriver driver;
    
    @Before
    public void doBefore(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(WAIT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(LOAD_TIMEOUT, TimeUnit.SECONDS);
    }
    
    @After
    public void doAfter(){
        driver.quit();
    }
    
    @Given("^I am on home page$")
    public void i_am_on_home_page() throws Throwable {
        driver.get(HOME_PAGE_URL);
        demo_delay();
    }

    @When("^I go to About page$")
    public void i_go_to_About_page() throws Throwable {
        WebElement aboutLink = driver.findElement(By.linkText("about"));
        aboutLink.click();
    }

    @Then("^About page is(?: still)? opened$")
    public void about_page_is_opened() throws Throwable {
        String actualUrl = driver.getCurrentUrl();
        assertEquals(ABOUT_PAGE_URL, actualUrl);
        demo_delay();
    }

    @Then("^Back Button is shown$")
    public void back_Button_is_shown() throws Throwable {
        WebElement btn1 = driver.findElement(By.id("btn1"));
        assertTrue(btn1.isDisplayed() && btn1.isEnabled());
        demo_delay();
    }

    @When("^I try to return back$")
    public void i_try_to_return_back() throws Throwable {
        WebElement btn1 = driver.findElement(By.id("btn1"));
        btn1.click(); //comment this line and uncomment next two
//        btn1.sendKeys(Keys.RETURN);
//        demo_delay();
    }

    @Then("^text '([^']+)' is shown$")
    public void text_Wow_You_have_clicked_me_is_shown(String expectedText) throws Throwable {
        WebElement div1 = driver.findElement(By.id("div1"));
        assertEquals("WTF?", expectedText, div1.getText());
    }

    private void demo_delay(){
        if(DO_DEMO_DELAY){
            try {
                Thread.sleep(DEMO_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
