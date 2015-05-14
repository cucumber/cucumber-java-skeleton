Feature: test through API: java.lang.String
  
  Scenario: charAt
    Given I have a string "abc"
    Then char at 1 is 'a'
    
  
  Scenario: concat
    Given I have a string "xyz"
    When I concatenate it with string "def"
    Then result string is "xyzdef"
    
