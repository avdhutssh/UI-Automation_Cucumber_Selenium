Feature: Derived Product 2 Footer

  @Regression
  Scenario: Find and validate footer hyperlinks
    Given User is on the bulls home page footer section
    Then User finds and stores all the footer hyperlinks in a CSV file
    And User reports the duplicate hyperlinks if present
