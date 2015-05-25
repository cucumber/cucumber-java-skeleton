package skeleton;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.Body;
import com.mashape.unirest.request.body.RequestBodyEntity;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.IOUtils;

import java.io.*;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        HttpRequest request = Unirest.get(mathBaseUrl + "/plus")
                .queryString("a", arg1)
                .queryString("b", arg2);
        doRequest(request);
    }

    @Then("^sum must be (\\d+)$")
    public void sum_must_be(int sum) {
        assertEquals("AAAA!", sum, response.getBody().getObject().getInt("result"));
    }

    @Given("^I have no users$")
    public void i_have_no_users() {
        i_delete_all_users();
    }

    @When("^I add users$")
    public void i_add_users(List<User> users) {
        for(User user : users){
            doRequest(
                    Unirest.post(usersBaseUrl + "/add")
                            .body(new JSONObject(user).toString())
                            .getHttpRequest()
            );
        }
    }

    @When("I delete user (.*?)$")
    public void i_delete_user(String email) {
        doRequest(
                Unirest.delete(usersBaseUrl).queryString("email", email)
        );

    }

    @When("I delete all users")
    public void i_delete_all_users() {
        doRequest(Unirest.delete(usersBaseUrl));
    }

    @When("I edit user (.*?) with (.*?) and (.*?)$")
    public void i_edit_user(String email, String newFirstname, String newLastname) {

        doRequest(
                Unirest.post(usersBaseUrl + "/edit")
                        .queryString("email", email)
                        .body("{\"firstName\":\"" + newFirstname + "\",\"lastName\":\"" + newLastname + "\"}")
                        .getHttpRequest()
        );

    }

    @Then("^there are (\\d+) users$")
    public void there_are_users(int expectedUsersCount) {
        doRequest(Unirest.get(usersBaseUrl));
        int actualUsersCount = response.getBody().getObject().getJSONArray("users").length();
        assertEquals("BBB!", expectedUsersCount, actualUsersCount);
    }

    @Then("User (.*?) has firstname (.*?) and lastname (.*?)$")
    public void user_has_credentials(String email, String firstname, String lastname) {
        doRequest(Unirest.get(usersBaseUrl));
        JSONArray users = response.getBody().getObject().getJSONArray("users");
        int index;
        for(index = 0; index < users.length(); index++) {
            JSONObject userRecord = users.getJSONObject(index);
            if (userRecord.getString("email").equals(email)) break;

        }
        String actualFirstname = users.getJSONObject(index).getString("first_name");
        String actualLastname = users.getJSONObject(index).getString("last_name");

        assertTrue(actualFirstname.equals(firstname) && actualLastname.equals(lastname));
    }
    
    private void doRequest(HttpRequest request){
        response = null;
        try {
            Body body = request.getBody();
            String strBody = null;
            if(body != null){
                RequestBodyEntity rbe = (RequestBodyEntity)body;
                strBody = (String)rbe.getBody();
            }
            System.err.println(String.format("Request: %s\n\tMethod:%s\n\tHeaders:%s\n\tBody:%s", request.getUrl(),
                    request.getHttpMethod(), request.getHeaders(), strBody));
            response = request.asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if(response != null){
            System.err.println("Response: " + response.getBody().toString());
        }
    }

}
