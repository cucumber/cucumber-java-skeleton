package io.cucumber.skeleton;

import io.cucumber.java.en.Given;

public class Steps {
    @Given("I have {int} cukes in my belly")
    public void I_have_cukes_in_my_belly(int cukes) throws Throwable {
        Belly belly = new Belly();
        belly.eat(cukes);
    }
}
