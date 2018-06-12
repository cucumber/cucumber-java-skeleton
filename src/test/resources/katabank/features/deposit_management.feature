Feature: Bank Management - Deposit

  Background:
    Given there is a bank with account and customer
    Given there is a default interest policy and deposit duration

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

  Scenario: Deposit new funds
    Given there is a customer with a deposit opened for period of 12 months with interest rate of 10% and 100 amount
    When after 3 months he transfers new funds of amount 100 to the existing deposit with interest higher by 0.5%
    Then the interest rate for these funds is 0.5% greater than the original interest rate
    And the interest for this funds is proportional to the deposit time left and equals 217.87

  Scenario: Deposit insurance - cost
    Given there is a customer who is about to open a new deposit of any kind
    And he decided to add the insurance to the deposit, the deposit cost is 0.05% of thr amount
    When he opens a deposit
    Then the deposited amount is 0.05% lower than the original amount