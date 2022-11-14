Feature: Login Attempts

  In order to help me with my login attempts
  As a user of the marketalertuom website, i want to be able to log in accordingly
  I want to be able to login (or otherwise)

  Scenario: Correct Login Attempt
    Given I am a user of marketalertum
    When I login using valid credentials
    Then I should see my alerts
  Scenario: Incorrect Login Attempt
    Given I am a user of marketalertum
    When I login using invalid credentials
    Then I should see the login screen again
  Scenario: 3 Alerts Showing
    Given I am an administrator of the website and I upload 3 alerts
    Given I am a user of marketalertum
    When I view a list of alerts
    Then each alert should contain an icon
    And each alert should contain a heading
    And each alert should contain a description
    And each alert should contain an image
    And each alert should contain a price
    And each alert should contain a link to the original product website
  Scenario: Alert Limit
    Given I am an administrator of the website and I upload more than 5 alerts
    Given I am a user of marketalertum
    When I view a list of alerts
    Then I should see 5 alerts

  Scenario: Icon Check
    Given I am an administrator of the website and I upload an alert of type 3
    Given I am a user of marketalertum
    When I view a list of alerts
    Then I should see 1 alerts
    And the icon displayed should be 3




