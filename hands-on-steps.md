##1. Begining

1.1. Clone project
  
  ```
  git clone https://github.com/budabum/cucumber-java-skeleton 
  cd cucumber-java-skeleton
  mvn -e test
  ```
  
  Expect 1 scenario to be run, 1 step to pass and 2 to be pending.
  
1.2. Run idea
  
  1.2.1. Import project from maven

  1.2.2. Optional. Do it if Idea cucumber plugin has not been installed
  ```
  Settings -> Plugins -> Browse plugin repository and type cucumber in search.
  Install Cucumber for Java and agree to install dependant plugin.
  ```
  1.2.3. Run belly.feature file (should fail - has pending steps)

  1.2.4. Run RunCukesTest file (1 step passed and 2 ignored)
  
## 2. Evolution 1. Make test works

2.1. Copy autogenerated steps from cnsole into Stepdefs.java

2.2. Run

2.3. Add code to make test passed

2.4. Change something in test scenario to make it failed (this is important!)

## 3. Evolution 2. API testing

3.1. Get first version from git
  ```
  git checkout tag/step1
  ```
  
  3.1.1. If it does not work run *git checkout -- .*
  
3.2. Open file api_test.feature

3.3. Run it and then RunCukeTests

3.4. Insert features and tags to run only needed scenarios
  ```
  Replace
    @CucumberOptions(plugin = {"pretty"})
  with
    @CucumberOptions(plugin = {"pretty"}, features = {"src/test/resources/skeleton/api_test.feature"})
  
  Then add tag ‘@my’ to one of scenarios and into runner
    @CucumberOptions(plugin = {"pretty"}, features = {"src/test/resources/skeleton/api_test.feature"}, tags=”@my”)
  ```

3.5.  **TASK:** Create a couple more tests for one of these methods (see StringSteps)
          Use http://docs.oracle.com/javase/7/docs/api/java/lang/String.html as requirements
          
## 4. Running test server
  Go to https://github.com/budabum/eduke-server and follow instructions
  
  Run test server and keep it running.

## 5. Evolution 3. Rest testing.

5.1. Get new version from git
  ```
  git checkout tag/step3
  ```
  
5.2. Look at file rest_test.feature - you can run it or better run RunCukeTests

5.3. Let’s look at steps: RestSteps

5.4. TASK: Create one more test for route /plus
http://localhost:9944/math/plus?a=2&b=3 -url spec for instance
TASK*: Create one more test for route /divide
http://localhost:9944/math/divide?a=9&b=3 -url spec for instance
Evolution 4. Rest testing continued.
git checkout tag/step4
restart test server
update maven dependencies in idea (if autoupdate is off)
let’s install postman (plugin for chrome/chromium)
run in postman
GET http://localhost:9944/users 
POST http://localhost:9944/users/add 
Content-Type: application/json
{"email":"user11@email.com", "firstName":"autotest", "lastName":"user"}
GET http://localhost:9944/users?email=user12@email.com 
DELETE http://localhost:9944/users 
look at file rest_test.feature - new scenario - get users with data table
look at file RestSteps - it has changed.
TASK: create a couple more tests for /users routes
Evolution 5. Web testing
git checkout tag/step5
restart test server
update maven dependencies in dea (if autoupdate is off)
look at file selenium_test.feature - run it (expected to fail)
look at SeleniumSteps.
Uncomment lines 74,75 and comment 73.
run test again - should pass
TASK: create one more test for any page







Rest testing
---------------
http://unirest.io/java.html

Postman rest client for chrome/chromium

Work with cucumber datatables
https://thomassundberg.wordpress.com/2014/06/30/cucumber-data-tables/

Scenario outlines
https://github.com/cucumber/cucumber/wiki/Scenario-Outlines


Other cucumber examples
https://github.com/cucumber/cucumber-jvm/tree/master/examples



