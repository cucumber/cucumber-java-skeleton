Feature: test through Rest

  Scenario: math plus
    When I plus '11' and '12'
    Then sum must be 23

  @my
  Scenario: get users
    Given I have no users
    Then there are 0 users
    When I add users
      | email  | firstName | lastName |
      | u1@a.b | aa        | bb       |
      | u2@c.d | cc        | dd       |
    Then there are 2 users
