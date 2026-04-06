Feature: Purchase the order from Ecommerce Website

  Background:
    Given I landed on Ecommerce page

  @Regression
  Scenario Outline: Positive Test of Submitting the order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on Confirmation page

    Examples:
      | name                   | password   | productName |
      | asanirban298@gmail.com | sA2!091959 | ZARA COAT 3 |
