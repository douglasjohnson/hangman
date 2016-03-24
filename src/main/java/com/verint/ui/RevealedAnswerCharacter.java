package com.verint.ui;

public class RevealedAnswerCharacter {

    private static final char HIDDEN_LETTER_SYMBOL = '*';
    private char answerCharacter;

    public RevealedAnswerCharacter(char revealedLetter) {
        this.answerCharacter = revealedLetter;
    }

    public char getAnswerCharacter() {
        return answerCharacter;
    }

    public boolean isRevealed() {
        return answerCharacter != HIDDEN_LETTER_SYMBOL;
    }

}
