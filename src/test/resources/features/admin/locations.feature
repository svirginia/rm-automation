Feature: CRUD locations
  Scenario: Create a location
    Given I logged with "valid" account in admin rm site
      And I go to email servers page
      And I add "dev" email server
    When I go to locations page
      And I click on add button
      And I fill the location info form
      | Name           | auto_location1         |
      | Display Name   | auto_location1_display |
      And I click on save button
    Then The notification message "Location successfully added" should be displayed in locations page
      And The "auto_location1" location is displayed in locations grid