Feature: Belly

  Scenario: a few cukes
    Given I have 42 cukes in my belly
    When I wait 1 hour
    Then my belly should growl
	
  Scenario: a lot of cukes
    Given I have 100 cukes in my belly
    When I wait 1 hour
    Then I will get fat