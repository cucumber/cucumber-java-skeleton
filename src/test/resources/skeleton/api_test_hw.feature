Feature: test java.util.ArrayList

  @my
  Scenario: empty list length
    Given I have an empty list
    Then List length is 0
  @my
  Scenario: add element to list
    Given I have an empty list
    When I add string "abc" to a list
    Then List contains string "abc"
  @my
  Scenario: remove element from list
    Given I have a list with 5 elements
    When I remove last element
    Then List length is 4

