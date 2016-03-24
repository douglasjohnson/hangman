package com.verint;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Set;
import java.util.TreeSet;

public class Hangman {

    private static final String WORD_SEPARATOR = "/";
    private static final int MAXIMUM_LIVES = 6;
    private static final char HIDDEN_LETTER_SYMBOL = '*';
    private String answer;
    private String normalisedAnswer;
    private Set<String> guessedLetters = new TreeSet<>();
    private Set<String> incorrectLetters = new TreeSet<>();
    private int livesLost = 0;

    private enum GAME_STATUS {
        IN_PROGRESS, WON, LOST
    }

    private GAME_STATUS status;
    private static final String VALID_NON_ALPHA_CHARACTERS = "-':,!;?";
    private static final String VALID_ALPHANUMERIC_REGEX = "a-z0-9";

    public Hangman(String answer) {
        this.answer = answer.toLowerCase();
        normalisedAnswer = stripDiacritic(this.answer);
        if (!normalisedAnswer.matches("^[" + VALID_ALPHANUMERIC_REGEX + VALID_NON_ALPHA_CHARACTERS + "\\s]+$")) {
            throw new IllegalArgumentException("Cannot start a game with answer " + answer);
        }
        status = GAME_STATUS.IN_PROGRESS;
    }

    public void guessLetter(String letter) {
        if (isGameInProgress()) {
            String lowerCaseLetter = letter.toLowerCase();
            if (isInvalidGuess(lowerCaseLetter)) {
                return;
            }
            if (!guessedLetters.add(lowerCaseLetter)) {
                loseLife();
            } else if (!answer.contains(lowerCaseLetter) && !normalisedAnswer.contains(lowerCaseLetter)) {
                incorrectLetters.add(lowerCaseLetter);
                loseLife();
            } else {
                updateGameStatus();
            }
        }
    }

    public void guessAnswer(String guessedAnswer) {
        if (isGameInProgress()) {
            if (guessedAnswer.toLowerCase().equals(this.answer)) {
                status = GAME_STATUS.WON;
            } else {
                loseLife();
            }
        }
    }

    private void loseLife() {
        livesLost++;
        updateGameStatus();
    }

    private void updateGameStatus() {
        if (livesLost == MAXIMUM_LIVES) {
            status = GAME_STATUS.LOST;
        } else if (allLettersRevealed()) {
            status = GAME_STATUS.WON;
        }
    }

    private boolean allLettersRevealed() {
        return getRevealedLetters().indexOf(HIDDEN_LETTER_SYMBOL) == -1;
    }

    private boolean isInvalidGuess(String letter) {
        String normalisedLetter = stripDiacritic(letter);
        return !normalisedLetter.matches("[" + VALID_ALPHANUMERIC_REGEX + "]");
    }

    private String stripDiacritic(String originalString) {
        return Normalizer.normalize(originalString, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public String getRevealedLetters() {
        String revealedLetters;
        if (isGameWon()) {
            revealedLetters = answer.replaceAll(" ", WORD_SEPARATOR);
        } else {
            StringBuilder revealedString = new StringBuilder(answer.length());
            for (int i = 0; i < answer.length(); i++) {
                String currentCharacter = String.valueOf(answer.charAt(i));
                if (" ".equals(currentCharacter)) {
                    revealedString.append(WORD_SEPARATOR);
                } else if (VALID_NON_ALPHA_CHARACTERS.contains(currentCharacter)) {
                    revealedString.append(currentCharacter);
                } else if (guessedLetters.contains(currentCharacter)
                        || guessedLetters.contains(stripDiacritic(currentCharacter))) {
                    revealedString.append(currentCharacter);
                } else {
                    revealedString.append(HIDDEN_LETTER_SYMBOL);
                }
            }
            revealedLetters = revealedString.toString();
        }
        return revealedLetters;
    }

    public int getLivesLost() {
        return livesLost;
    }

    public Set<String> getIncorrectLetters() {
        return incorrectLetters;
    }

    public boolean isGameInProgress() {
        return status == GAME_STATUS.IN_PROGRESS;
    }

    public boolean isGameWon() {
        return status == GAME_STATUS.WON;
    }

    public boolean isGameLost() {
        return status == GAME_STATUS.LOST;
    }

}
