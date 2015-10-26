package skeleton;

import cucumber.api.java.en.Given;
import cucumber.api.java.zh_cn.假如;

public class Stepdefs {
    @Given("^I have (\\d+) cukes in my belly$")
    public void I_have_cukes_in_my_belly(int cukes) throws Throwable {
        Belly belly = new Belly();
        belly.eat(cukes);
    }

    @假如("^一世 开放 \"(.*?)\" 网址 在 该 浏览器$")
    public void 一世_开放_网址_在_该_浏览器(String url) throws Throwable {
        System.out.println("url = " + url);
    }
}
