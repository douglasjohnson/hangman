package com.verint.ui;

import com.verint.Hangman;

public interface HangmanService {

    public Hangman getHangman();

    public void setHangman(Hangman hangman);

    public void setAnswer(String answer);

    public String getAnswer();
}
