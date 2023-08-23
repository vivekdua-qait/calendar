Feature: Bonus Question

  Background: The User opens the application
    Given "Unauthenticated" User launches Chrome Browser
    When user launch the url "http://localhost:3000/"

  Scenario: As an unauthenticated user, I want to see the name 'Catendar' instead of Calendar in the header of the app, so that I am aware of the fact that this version of the app is feline-friendly
    When User changes the text Calendar to "Catendarx"
    Then User verify "Catendarx" is displayed

  Scenario: As an unauthenticated user, I want to be able to view a fun cat fact in the header of the calender, so that I can have a fun/light-hearted start to my day.
    When User fetch random Fun Cat Fact using API
    When User adds the fun cat fact in the header
    Then User verify header contains fun cat fact
