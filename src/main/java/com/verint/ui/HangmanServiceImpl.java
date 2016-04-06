package com.verint.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.verint.Hangman;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class HangmanServiceImpl implements HangmanService {

    private Hangman hangman;
    private String answer;

    public HangmanServiceImpl() {
        //
    }

    @Override
    public Hangman getHangman() {
        return hangman;
    }

    @Override
    public void setHangman(Hangman hangman) {
        this.hangman = hangman;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
