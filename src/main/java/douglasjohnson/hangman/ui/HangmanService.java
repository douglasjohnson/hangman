package douglasjohnson.hangman.ui;

import douglasjohnson.hangman.Hangman;

public interface HangmanService {

    public Hangman getHangman();

    public void setHangman(Hangman hangman);

    public void setAnswer(String answer);

    public String getAnswer();
}
