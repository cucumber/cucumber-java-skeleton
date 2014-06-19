@focus
Feature: Squeaking

  Rules:
  - Must be logged in to squeak
  - Squeaks are displayed chroologically (oldest on top)
  - Squeak author is displayed along with squeak

  @focus
  Scenario: aslak squeaks twice
    Given "aslak" has logged in
    When "aslak" squeaks "utepils?"
    And "aslak" squeaks "selenium er fint"
    Then I should see:
      | aslak    | utepils?         |
      | aslak    | selenium er fint |
