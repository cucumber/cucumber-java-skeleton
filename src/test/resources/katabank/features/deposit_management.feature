Feature: Bank Management - Deposit

  Background:
    Given there is a bank with account and customer
    Given there is a default deposit type

  Scenario: Opening deposit
    Given a customer has an account with balance 100
    When he opens a deposit with balance 90
    Then he owns a deposit with balance 90
    And the account has balance 10

  Scenario: Termination date
    Given a customer opened a deposit for a period of one year
    When one year has passed
    Then the money is transferred back to the account the funds were taken from

  Scenario: Interest rate
    Given bank offers a deposit for a period of 6 months with yearly interest rate 10%
    And customer opens that deposit with funds 100
    When a termination date has passed
    Then 105 is transferred back to his account