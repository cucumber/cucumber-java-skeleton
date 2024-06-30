Feature: Hear shout
  Scenario: Listener is within range
    Given Lucy is located 15 meters from Sean
    When Sean shouts "Free bagels at Sean's"
    Then Lucy hears Sean's message

  Scenario: Listener hears different message
    Given Lucy is located 15 meters from Sean
    When Sean shouts "Free Coffee!"
    Then Lucy should hear Sean's message