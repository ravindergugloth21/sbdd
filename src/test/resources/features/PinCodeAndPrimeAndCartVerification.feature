@Amazon
Feature: Pincode change and prime benefits and cart verification

  Background: User is on amazon home page
    Given User is on home page

  @PinCode
  Scenario Outline: Add PinCode To The Address
    When User add "<pinCode>" for the address
    Then Verify the pincode is added or not
    Examples:
      | pinCode |
      | 500081  |
      | 500041  |
