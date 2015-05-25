Feature: Belly

  Scenario: a few cukes
    Given I have 42 cukes in my belly
    When I eat 4 more cukes
    Then it should be 46 cukes in my belly
