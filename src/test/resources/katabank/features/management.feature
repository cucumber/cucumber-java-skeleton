Feature: Bank Management

  Background:
    Given there is a bank with account
    Given there is a customer

  Scenario: list accounts
    Given a customer has two accounts open
    When he lists his accounts
    Then only those accounts are on the list

  Scenario: open account
    Given a customer wants to open an account
    When his account is created
    Then there is a new account on his account list
    And the balance on this account is 0

  Scenario: deposit money
    Given balance on the account is 100
    When customer deposits 10 to this account
    Then balance on the account is 110

  Scenario: withdraw money
    Given balance on the account is 100
    When customer withdraws 90 from this account
    Then balance on the account is 10