Feature: Register new user

  Scenario: User is logged in after registering
    Given "per" is not registered
    When "per" registers
    Then "per" should be logged in