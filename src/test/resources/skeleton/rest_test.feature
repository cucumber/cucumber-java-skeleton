Feature: test through Rest: java.lang.String

  Scenario: math plus
    When I plus '11' and '12'
    Then sum must be 23

