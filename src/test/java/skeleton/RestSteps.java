package skeleton;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Lyanguzov
 */
public class RestSteps {
    private static final String baseUrl = "http://localhost:9944";
    private static final String mathBaseUrl = baseUrl + "/math";
    private static final String usersBaseUrl = baseUrl + "/users";
    
    private HttpResponse<JsonNode> response = null;

    @When("^I plus '([^']+)' and '([^']+)'$")
    public void doPlus(String arg1, String arg2){
        response = null;
        try {
            HttpRequest request = Unirest.get(mathBaseUrl + "/plus")
                    .queryString("a", arg1)
                    .queryString("b", arg2);
            System.err.printf("Request: %s\n\tMethod:%s\n\tHeaders:\n", request.getUrl(),
                    request.getHttpMethod(), request.getHeaders());
            response = request.asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        System.err.println("Response: " + response.getBody().toString());
    }

    @Then("^sum must be (\\d+)$")
    public void sum_must_be(int sum) throws Throwable {
        assertEquals("AAAA!", sum, response.getBody().getObject().getInt("result"));
    }

}
