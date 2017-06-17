package douglasjohnson.hangman.ui;

public class GuessCharacter {

    private String character;
    private GuessState guessState;

    public GuessCharacter(String character, GuessState guessState) {
        this.character = character;
        this.guessState = guessState;
    }

    public String getCharacter() {
        return character;
    }

    public boolean isGuessed() {
        return guessState != GuessState.NOT_GUESSED;
    }
    
    public boolean isIncorrectlyGuessed() {
        return guessState == GuessState.INCORRECTLY_GUESSED;
    }

}
