Feature: Bank Management

  Background:
    Given there is a bank with account
    Given there is a customer

  Scenario: list accounts
    Given a customer has two accounts open
    When he lists his accounts
    Then only those accounts are on the list