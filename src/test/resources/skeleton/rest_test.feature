Feature: test through Rest

  Scenario: math plus
    When I plus '11' and '12'
    Then sum must be 23

  Scenario: get users
    Given I have no users
    Then there are 0 users
    When I add users
      | email  | firstName | lastName |
      | u1@a.b | aa        | bb       |
      | u2@c.d | cc        | dd       |
    Then there are 2 users

#Homework test scenarios
  @my
  Scenario: delete a user
    Given I have no users
    And I add users
      | email          | firstName            | lastName            |
      | test1@mail.com | test1FirstName       | test1LastName       |
      | test2@mail.com | test2FirstName       | test2LastName       |
    When I delete user test1@mail.com
    Then there are 1 users
  @my
  Scenario: delete all users
    Given I add users
      | email          | firstName            | lastName            |
      | test3@mail.com | test3FirstName       | test3LastName       |
      | test4@mail.com | test4FirstName       | test4LastName       |
    When I delete all users
    Then there are 0 users

  @my
  Scenario Outline: editing users
    Given I have no users
    And I add users
      | email          | firstName            | lastName            |
      | <email>        | <oldFirstname>       | <oldLastname>       |
    When I edit user <email> with <newFirstname> and <newLastname>
    Then User <email> has firstname <newFirstname> and lastname <newLastname>

  #test valid editing, test editing with empty fields
  Examples:
    | email          | oldFirstname  | oldLastname  | newFirstname     | newLastname |
    | test1@mail.com | oldfirstname1 | oldlastname1 | newf1            | newl1       |
    | test1@mail.com | oldfirstname1 | oldlastname1 |                  |             |


