Feature: Add new address

  Scenario Outline: User can add new address
    Given User is logged in to the shop
    When User goes to ADDRESSES
    Then User enters alias "<alias>", address "<address>", city "<city>", zip or postal code "<code>" and phone "<phone>"
    And User deletes new address
    And User closes the browser
    Examples:
      | alias     | address       | city          | code  | phone     |
      | Address1  | Street 12/34  | New York      | 82876 | 789465126 |
#      | Address2  | Alley 56/78   | Chicago       | 76143 | 321548765 |
#      | Address3  | Avenue 90/12  | Los Angeles   | 23156 | 123648984 |
#      | Address4  | Street 9/45   | San Francisco | 13883 | 201534631 |
#      | Address5  | Alley 54/98   | Dallas        | 48655 | 846153286 |