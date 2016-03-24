Feature: Hangman

  @UI
  Scenario: Guess a correct letter
    Given I start a game of hangman with answer "diesel"
     When I guess character "d"
     Then characters revealed are "d*****"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  @UI
  Scenario: Guess a correct letter which appears twice
    Given I start a game of hangman with answer "diesel"
     When I guess character "e"
     Then characters revealed are "**e*e*"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  @UI
  Scenario: Guess a second correct letter
    Given I start a game of hangman with answer "diesel"
      And I guess character "d"
     When I guess character "i"
     Then characters revealed are "di****"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  @UI
  Scenario: Guess an incorrect letter
    Given I start a game of hangman with answer "diesel"
     When I guess character "x"
     Then characters revealed are "******"
      And "1" lives are lost
      And incorrect characters guessed are "x"
      And game is in progress

  @UI
  Scenario: Guess a second incorrect letter
    Given I start a game of hangman with answer "diesel"
      And I guess character "z"
     When I guess character "x"
     Then characters revealed are "******"
      And "2" lives are lost
      And incorrect characters guessed are "x,z"
      And game is in progress

  @UI
  Scenario: Guess a third incorrect letter
    Given I start a game of hangman with answer "diesel"
      And I guess character "z"
      And I guess character "x"
     When I guess character "y"
     Then characters revealed are "******"
      And "3" lives are lost
      And incorrect characters guessed are "x,y,z"
      And game is in progress

  @UI
  Scenario: Guess a fourth incorrect letter
    Given I start a game of hangman with answer "diesel"
      And I guess character "z"
      And I guess character "x"
      And I guess character "y"
     When I guess character "a"
     Then characters revealed are "******"
      And "4" lives are lost
      And incorrect characters guessed are "a,x,y,z"
      And game is in progress

  @UI
  Scenario: Guess a fifth incorrect letter
    Given I start a game of hangman with answer "diesel"
      And I guess character "z"
      And I guess character "x"
      And I guess character "y"
      And I guess character "a"
     When I guess character "b"
     Then characters revealed are "******"
      And "5" lives are lost
      And incorrect characters guessed are "a,b,x,y,z"
      And game is in progress

  @UI
  Scenario: Guess a sixth incorrect letter
    Given I start a game of hangman with answer "diesel"
      And I guess character "z"
      And I guess character "x"
      And I guess character "y"
      And I guess character "a"
      And I guess character "b"
     When I guess character "c"
     Then "6" lives are lost
      And incorrect characters guessed are "a,b,c,x,y,z"
      And game is lost

  Scenario: Guess a non letter
    Given I start a game of hangman with answer "diesel"
     When I guess character "."
     Then characters revealed are "******"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  @UI
  Scenario: Guess a correct letter and then an incorrect letter
    Given I start a game of hangman with answer "diesel"
     When I guess character "d"
      And I guess character "z"
     Then characters revealed are "d*****"
      And "1" lives are lost
      And incorrect characters guessed are "z"
      And game is in progress

  @UI
  Scenario: Guess an incorrect letter and then a correct letter
    Given I start a game of hangman with answer "diesel"
     When I guess character "z"
      And I guess character "d"
     Then characters revealed are "d*****"
      And "1" lives are lost
      And incorrect characters guessed are "z"
      And game is in progress

  Scenario: Guess a correct letter that has already been guessed
    Given I start a game of hangman with answer "diesel"
      And I guess character "d"
     When I guess character "d"
     Then characters revealed are "d*****"
      And "1" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  Scenario: Guess an incorrect letter that has already been guessed
    Given I start a game of hangman with answer "diesel"
      And I guess character "x"
     When I guess character "x"
     Then characters revealed are "******"
      And "2" lives are lost
      And incorrect characters guessed are "x"
      And game is in progress

  @UI
  Scenario: Guess a correct letter to win the game
    Given I start a game of hangman with answer "diesel"
      And I guess character "d"
      And I guess character "i"
      And I guess character "e"
      And I guess character "s"
     When I guess character "l"
     Then characters revealed are "diesel"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is won

  Scenario: Guess an incorrect letter to lose the game
    Given I start a game of hangman with answer "diesel"
      And I guess character "a"
      And I guess character "b"
      And I guess character "c"
      And I guess character "x"
      And I guess character "y"
     When I guess character "z"
     Then characters revealed are "******"
      And "6" lives are lost
      And incorrect characters guessed are "a,b,c,x,y,z"
      And game is lost

  @UI
  Scenario: Guess the correct word
    Given I start a game of hangman with answer "diesel"
     When I guess the answer "diesel"
     Then characters revealed are "diesel"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is won

  @UI
  Scenario: Guess an incorrect word
    Given I start a game of hangman with answer "diesel"
     When I guess the answer "diezel"
     Then characters revealed are "******"
      And "1" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  @UI
  Scenario: Guess an incorrect word to lose the game
    Given I start a game of hangman with answer "diesel"
      And I guess character "a"
      And I guess character "b"
      And I guess character "c"
      And I guess character "x"
      And I guess character "y"
     When I guess the answer "diezel"
     Then game is lost

  @UI
  Scenario: Guess multiple incorrect words to lose the game
    Given I start a game of hangman with answer "diesel"
      And I guess the answer "coloss"
      And I guess the answer "corezz"
      And I guess the answer "suppor"
      And I guess the answer "teslaz"
      And I guess the answer "jedizz"
     When I guess the answer "diezel"
     Then game is lost

  @UI
  Scenario: Guess correct word with no lives remaining
    Given I start a game of hangman with answer "diesel"
      And I guess the answer "coloss"
      And I guess the answer "corezz"
      And I guess the answer "suppor"
      And I guess the answer "teslaz"
      And I guess the answer "jedizz"
     When I guess the answer "diesel"
     Then game is won

  @UI
  Scenario: Guess a correct letter with different case
    Given I start a game of hangman with answer "diesel"
     When I guess character "D"
     Then characters revealed are "d*****"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  @UI
  Scenario: Guess a correct letter when answer is provided in mixed*case
    Given I start a game of hangman with answer "dIeSeL"
     When I guess character "s"
     Then characters revealed are "***s**"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  @UI
  Scenario: Guess correct word with all letters in different case
    Given I start a game of hangman with answer "dIeSeL"
     When I guess the answer "DiEsEl"
     Then characters revealed are "diesel"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is won

  @UI
  Scenario: Guess an incorrect letter with upper case
    Given I start a game of hangman with answer "diesel"
     When I guess character "X"
     Then characters revealed are "******"
      And "1" lives are lost
      And incorrect characters guessed are "x"
      And game is in progress

  Scenario: Guess a correct letter when game is won
    Given I start a game of hangman with answer "diesel"
      And I win the game
     When I guess character "d"
     Then characters revealed are "diesel"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is won

  Scenario: Guess an incorrect letter when game is won
    Given I start a game of hangman with answer "diesel"
      And I win the game
     When I guess character "z"
     Then characters revealed are "diesel"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is won

  Scenario: Guess correct word when game is won
    Given I start a game of hangman with answer "diesel"
      And I win the game
     When I guess the answer "diesel"
     Then characters revealed are "diesel"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is won

  Scenario: Guess an incorrect word when game is won
    Given I start a game of hangman with answer "diesel"
      And I win the game
     When I guess the answer "tesla"
     Then characters revealed are "diesel"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is won

  Scenario: Guess a correct letter when game is lost
    Given I start a game of hangman with answer "diesel"
      And I lose the game
     When I guess character "d"
     Then characters revealed are "******"
      And "6" lives are lost
      And incorrect characters guessed are ""
      And game is lost

  Scenario: Guess an incorrect letter when game is lost
    Given I start a game of hangman with answer "diesel"
      And I lose the game
     When I guess character "f"
     Then characters revealed are "******"
      And "6" lives are lost
      And incorrect characters guessed are ""
      And game is lost

  Scenario: Guess correct word when game is lost
    Given I start a game of hangman with answer "diesel"
      And I lose the game
     When I guess the answer "diesel"
     Then characters revealed are "******"
      And "6" lives are lost
      And game is lost

  Scenario: Guess an incorrect word when game is lost
    Given I start a game of hangman with answer "diesel"
      And I lose the game
     When I guess the answer "tesla"
     Then characters revealed are "******"
      And "6" lives are lost
      And game is lost

  Scenario: Guess a diacritical letter with matching diacritic in the answer
    Given I start a game of hangman with answer "naïve"
     When I guess character "ï"
     Then characters revealed are "**ï**"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  @UI
  Scenario: Guess a regular letter with matching diacritic in the answer
    Given I start a game of hangman with answer "naïve"
     When I guess character "i"
     Then characters revealed are "**ï**"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  Scenario: Guess a diacritical letter with equivalent regular letter in the answer
    Given I start a game of hangman with answer "naive"
     When I guess character "ï"
     Then characters revealed are "*****"
      And "1" lives are lost
      And incorrect characters guessed are "ï"
      And game is in progress

  @UI
  Scenario: Answer is a phrase
    When I start a game of hangman with answer "the lord of the rings the fellowship of the ring"
    Then characters revealed are "***/****/**/***/*****/***/**********/**/***/****"
     And "0" lives are lost
     And incorrect characters guessed are ""
     And game is in progress

  @UI
  Scenario: Guess a correct letter in a phrase
    Given I start a game of hangman with answer "the lord of the rings the fellowship of the ring"
     When I guess character "e"
     Then characters revealed are "**e/****/**/**e/*****/**e/*e********/**/**e/****"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  @UI
  Scenario: Guess a phrase correctly
    Given I start a game of hangman with answer "the lord of the rings the fellowship of the ring"
     When I guess the answer "the lord of the rings the fellowship of the ring"
     Then characters revealed are "the/lord/of/the/rings/the/fellowship/of/the/ring"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is won

  @UI
  Scenario Outline: Answer contains a valid non-alpha character
    When I start a game of hangman with answer "<answer>"
    Then characters revealed are "<revealed>"

    Examples: 
      | answer             | revealed           |
      | co-operation       | **-*********       |
      | Diesel's           | ******'*           |
      | Diesel: The return | ******:/***/****** |
      | I, Diesel          | *,/******          |
      | Diesel!            | ******!            |
      | Semicolon;         | *********;         |
      | Who?               | ***?               |

  Scenario Outline: Answer cannot contain an invalid character
    Then I cannot start a game of hangman with answer "<answer>"

    Examples: 
      | answer   |
      | *diesel  |
      | <diesel  |
      | diesel/  |
      | die\\sel |
      |          |

  @UI
  Scenario: Guess a correct digit
    Given I start a game of hangman with answer "jaws 3"
     When I guess character "3"
     Then characters revealed are "****/3"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  @UI
  Scenario: Guess an incorrect digit
    Given I start a game of hangman with answer "diesel"
     When I guess character "3"
     Then characters revealed are "******"
      And "1" lives are lost
      And incorrect characters guessed are "3"
      And game is in progress

  Scenario: Guess an upper case diacritic letter with matching lower case diacritic letter in the answer
    Given I start a game of hangman with answer "naïve"
     When I guess character "Ï"
     Then characters revealed are "**ï**"
      And "0" lives are lost
      And incorrect characters guessed are ""
      And game is in progress

  Scenario: Guess an upper case diacritic letter with equivalent regular letter and non matching diacritic in the answer
    Given I start a game of hangman with answer "naïve"
     When I guess character "Î"
     Then characters revealed are "*****"
      And "1" lives are lost
      And incorrect characters guessed are "î"
      And game is in progress
