@Amazon
Feature: Search product in amazon and language change option

  Background: User is on amazon home page
    Given User is on home page

  @SearchItem
  Scenario Outline: Search for an Item
    When Search for an item with "<productName>"
    Then Verify the items displayed
    Examples:
      | productName |
      | Samsung     |
      | OnePlus     |

  @LanguageChanging
  Scenario: Language Settings in amazon
    When User navigate to language settings
    Then Verify the items in language settings

















