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
    
