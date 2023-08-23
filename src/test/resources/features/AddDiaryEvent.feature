Feature: Add an Event

  Background: The User opens the application
    Given "Authenticated" User launches Chrome Browser
    When user launch the url "http://localhost:3000/"

  Scenario: Validating the Login functionality
    When User selects a month "August"
    Then User adds an event and verify newly added event
      | date | title                              | color  |
      |   15 | Vivek Dua Interview DONE           | indigo |
      |   24 | Final Interview Slot 12:00 - 13:00 | red    |
      |   25 | Final Interview Slot 12:00 - 13:00 | red    |
      |   26 | Final Interview Slot 12:00 - 13:00 | red    |
