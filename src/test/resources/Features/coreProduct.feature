@Regression
Feature: Core Product Shop and News & Features

  @Smoke
  Scenario: Find and store all Men's Jackets
    Given User is on the warriors home page
    When User navigates to the Shop Menu
    And  User selects Men's Jackets
    Then User finds and stores all Jackets information in a text file

  @Sanity
  Scenario: Count video feeds in News & Features section
    Given User is on the warriors home page
    When User navigates to News Features sections
    Then User counts the total number of video feeds
    And User counts the number of video feeds present for 3 or more days
