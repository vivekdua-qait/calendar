Feature: Delete an Event

  Background: The User opens the application
    Given "Authenticated" User launches Chrome Browser
    When user launch the url "http://localhost:3000/"

  Scenario: Validating the Login functionality
    When User selects a month "August"
    Then User deletes an event and verify deleted event is not displayed
      | date | title                              |
      |   25 | Final Interview Slot 12:00 - 13:00 |
      |   26 | Final Interview Slot 12:00 - 13:00 |
    Then User validate the event
      | date | title                              | color |
      |   24 | Final Interview Slot 12:00 - 13:00 | red   |
