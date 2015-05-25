Feature: Selenium WebDriver

  #Test initially failed on step Then text ... - code should be uncommented in step

  Scenario: press button
    Given I am on home page
    When I go to About page
    Then About page is opened
    And Back Button is shown
    When I try to return back
    Then text 'Wow! You have clicked me!' is shown
    And About page is still opened


  #Homework test scenarios
  @my
  Scenario: list users
    Given I have no users
    And I add users
      | email          | firstName            | lastName            |
      | test1@mail.com | test1FirstName       | test1LastName       |
      | test2@mail.com | test2FirstName       | test2LastName       |
    And I am on home page
    When I go to List User page
    Then List User page is opened
    And User table contains 2 records

  @my
  Scenario: list empty user list
    Given I have no users
    And I am on home page
    When I go to List User page
    Then List User page is opened
    And User table contains 0 records
    When I add users
      | email          | firstName            | lastName            |
      | test1@mail.com | test1FirstName       | test1LastName       |
    And I reload the page
    Then User table contains 1 records
    And User test1@mail.com is on the list

