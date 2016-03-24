@UI
Feature: Hangman New Game Page

Scenario: Is initial page
   When I navigate to Hangman game
   Then the "New Game" page is displayed

Scenario: Contains instructions
  Given I navigate to Hangman game
   Then page contains instructions
    And instruction text is "To start a new game, please enter the answer."

Scenario: Contains answer input
  Given I navigate to Hangman game
   Then page contains answer input
    And answer button label is "Play!"

Scenario: Answer input is empty
  Given I navigate to Hangman game
   Then answer input text is ""

Scenario: Initial focus is on answer input
  Given I navigate to Hangman game
   Then focus is on answer input

Scenario: Tab order is answer input then button
  Given I navigate to Hangman game
   When I tab
   Then focus is on answer button

Scenario: Clicking play button with valid answer navigates to play game
  Given I navigate to Hangman game
   When I enter answer "diesel"
    And I click the answer button
   Then the "Play Game" page is displayed

Scenario: Submitting valid answer navigates to play game
  Given I navigate to Hangman game
   When I submit answer "diesel"
   Then the "Play Game" page is displayed

Scenario Outline: Clicking play button with invalid answer presents validation message
  Given I navigate to Hangman game
   When I enter answer "<answer>"
    And I click the answer button
   Then the "New Game" page is displayed
    And the validation message displayed is: 
        | Invalid answer                                                      |
        | Answer must be alpha-numeric and may contain the following symbols: |
        | ['-:,!;?]                                                           |

   Examples:
   | answer  |
   | *diesel |
   | <diesel |
   | diesel/ |
   | die\sel |
   |         |

Scenario Outline: Submitting invalid answer presents validation message
  Given I navigate to Hangman game
   When I submit answer "<answer>"
   Then the "New Game" page is displayed
    And the validation message displayed is: 
        | Invalid answer                                                      |
        | Answer must be alpha-numeric and may contain the following symbols: |
        | ['-:,!;?]                                                           |

   Examples:
   | answer  |
   | *diesel |
   | <diesel |
   | diesel/ |
   | die\sel |
   |         |