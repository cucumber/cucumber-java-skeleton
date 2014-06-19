Feature: Greet User

  Scenario: Greet visitors who are not logged in
    Given I'm not logged in
    When I visit the homepage
    Then I should see "Hello stranger"
