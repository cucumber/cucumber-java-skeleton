Feature: Checkout

  Background:
    Given the following products:
      | espresso  | 2     |
      | croissant | 1     |

  Scenario: Sell only espresso
    When I sell 3 espresso
    Then the total should be £6

  Scenario: Sell espresso and croissants
    When I sell 2 espresso
    And I sell 3 croissant
    Then the total should be £7

  #Scenario: Espresso costs 1.80