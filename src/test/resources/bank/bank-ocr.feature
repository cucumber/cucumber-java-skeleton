Feature: Bank OCR Kata

  See details: https://github.com/testdouble/contributing-tests/wiki/Bank-OCR-Kata-in-Cucumber

  Scenario: read account numbers
    Given I have an account file with contents:
      """
        _  _     _  _  _  _  _
      | _| _||_||_ |_   ||_||_|
      ||_  _|  | _||_|  ||_| _|

      """
    When I parse the file
    Then the first account number is "123456789"
