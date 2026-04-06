Feature: Validate error in login

  @ErrorValidation
  Scenario Outline: Test of validating login error message
    Given I landed on Ecommerce page
    When Logged in with username <name> and password <password>
    Then "Incorrect email or password." error message is displayed in login page

    Examples:
      | name                   | password  |
      | asanirban298@gmail.com | sA2!09195 |
