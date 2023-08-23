Feature: Diary View

  Background: The User opens the application
    Given "Unauthenticated" User launches Chrome Browser
    When user launch the url "http://localhost:3000/"

  Scenario: Validating the Login functionality
    When User selects a month "August"
    Then User validate the days of the month "August"
