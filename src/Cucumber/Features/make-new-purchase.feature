Feature: Make new purchase

  Scenario Outline: User can make a new purchase
    Given User is logged in the shop
    When User searches for Hummingbird Printed Sweater
    And User picks size "<size>" and number "<number>" of items he wants to purchase
    Then User adds the item to the cart and goes to checkout
    And User confirms address, picks shipping and payment method and confirms order
    And User makes a screenshot with the confirmation of the order and the paid amount
    And User checks if the order is available in the order history
    And User quits the browser
    Examples:
      | size | number |
      | M    | 5      |
#      | S    | 1      |
#      | M    | 2      |
#      | L    | 3      |
#      | XL   | 4      |