Feature: Search Yelp

  Background:

    Given user navigates to https://www.yelp.com/
    And selects find Restaurants

  Scenario Outline: Detail of the search of sushi restaurants

    Given User search restaurant <type>
    When filters by <distance>
    And selects the first search sushi result
    Then User views sushi restaurant information

    Examples:
      |type|distance|
      |Sushi|Walking (1 mi.)|