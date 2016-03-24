@UI
Feature: Hangman Play Game Page

Scenario: Contains instructions
   When I start a game of hangman with answer "diesel"
   Then page contains instructions
    And instruction text is "Guess a letter. When you're ready, guess the answer."

Scenario: Contains all possible guess characters
   When I start a game of hangman with answer "diesel"
   Then guess characters are "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    And all guess characters are enabled

Scenario: Guess answer button is displayed
   When I start a game of hangman with answer "diesel"
   Then the guess answer button is displayed

Scenario: Hidden revealed answer characters are editable
   When I start a game of hangman with answer "diesel"
   Then all of the revealed answer characters are editable
    And all of the revealed answer characters are hidden

Scenario: There is no message displayed
   When I start a game of hangman with answer "diesel"
   Then no message is displayed

Scenario: Play again button is displayed
   When I start a game of hangman with answer "diesel"
   Then the play again button is displayed

Scenario: Matching revealed answer characters are non-editable and displayed
  Given I start a game of hangman with answer "diesel"
   When I guess character "E"
   Then the revealed answer characters at positions "1,2,4,6" are editable
    And the revealed answer characters at positions "1,2,4,6" are hidden
    And the revealed answer characters at positions "3,5" are non-editable
    And the revealed answer characters at positions "3,5" are displayed

Scenario: Guessing a correct character from possible guess characters disables it
  Given I start a game of hangman with answer "diesel"
   When I guess character "I"
   Then guess character "I" is disabled
    And guess character "I" is not strike-through
    And all guess characters except "I" are enabled

Scenario: Guessing an incorrect character from possible guess characters marks it as strike-through
  Given I start a game of hangman with answer "diesel"
   When I guess character "A"
   Then guess character "A" is disabled
    And guess character "A" is strike-through
    And all guess characters except "A" are enabled

Scenario: Guessing a correct character part way through guessing an answer clears the hidden revealed characters
  Given I start a game of hangman with answer "diesel"
    And I enter "D" in revealed answer at position "1"
    And I enter "Z" in revealed answer at position "2"
   When I guess character "I"
   Then the revealed answer characters at positions "1,3,4,5,6" are editable
    And the revealed answer characters at positions "1,3,4,5,6" are hidden
    And the revealed answer characters at positions "2" are non-editable
    And the revealed answer characters at positions "2" are displayed

Scenario: Guessing an incorrect character part way through guessing an answer clears the hidden revealed characters
  Given I start a game of hangman with answer "diesel"
    And I enter "D" in revealed answer at position "1"
    And I enter "Z" in revealed answer at position "2"
   When I guess character "Z"
   Then all revealed answer characters are editable
    And all revealed answer characters are hidden

Scenario: Winning the game displays game won message
  Given I start a game of hangman with answer "diesel"
   When I win the game
   Then the message displayed has text "You have won - congratulations!"
    And all revealed answer characters are non-editable
    And all revealed answer characters are displayed
    And all guess characters are disabled
    And the guess answer button is disabled

Scenario: Losing the game displays game lost message
  Given I start a game of hangman with answer "diesel"
   When I lose the game
   Then the message displayed has text "You have lost - too bad."
    And all revealed answer characters are non-editable
    And all revealed answer characters are displayed
    And all guess characters are disabled
    And the guess answer button is disabled

Scenario: Play again displays are you sure dialog when game is in progress
  Given I start a game of hangman with answer "diesel"
   When I click play again
   Then dialog is displayed with text "Are you sure you want to abandon this game?"
    And dialog has "Yes" button
    And dialog has "No" button

Scenario: Play again are you sure dialog yes response returns to the new game page
  Given I start a game of hangman with answer "diesel"
    And I click play again
   When I click "Yes" on the are you sure dialog
   Then the "New Game" page is displayed

Scenario: Play again are you sure dialog no response remains on the play game page
  Given I start a game of hangman with answer "diesel"
    And I click play again
   When I click "No" on the are you sure dialog
   Then the "Play Game" page is displayed
    And the are you sure dialog is closed

Scenario: Play again returns to the new game page with no dialog when games is lost
  Given I start a game of hangman with answer "diesel"
    And I lose the game
   When I click play again
   Then the "New Game" page is displayed

Scenario: Play again returns to the new game page with no dialog when games is won
  Given I start a game of hangman with answer "diesel"
    And I win the game
   When I click play again
   Then the "New Game" page is displayed

Scenario: Guess incomplete answer shows error message
  Given I start a game of hangman with answer "diesel"
    And I enter "D" in revealed answer at position "1"
    And I enter "Z" in revealed answer at position "2"
   When I press the guess answer button
   Then the message displayed has text "Guessed answer is incomplete"
    And all revealed answer characters are editable
    And the revealed answer character at position "1" is "D"
    And the revealed answer character at position "2" is "Z"

Scenario: Multiple games do not interfere with each other
  Given I start a game of hangman with answer "diesel"
   When a second game of hangman is started with answer "gametwo"
    And I guess the answer "diesel"
   Then game is won

Scenario: Multiple games do not interfere with each other when game is lost
  Given I start a game of hangman with answer "diesel"
   When a second game of hangman is started with answer "gametwo"
    And I lose the game
   Then all revealed answer characters are displayed