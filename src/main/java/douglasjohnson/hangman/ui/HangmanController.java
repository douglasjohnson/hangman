package douglasjohnson.hangman.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import douglasjohnson.hangman.Hangman;

@Controller
public class HangmanController {

    private static final String PLAY_GAME = "play-game";
    private static final String NEW_GAME = "new-game";

    @Autowired
    private HangmanService hangmanservice;

    @RequestMapping("/")
    public String newGame() {
        return NEW_GAME;
    }

    @RequestMapping("/play-game")
    public String playGame(@RequestParam("submittedAnswer") String answer, Model model) {
        hangmanservice.setAnswer(answer);
        String view = PLAY_GAME;

        try {
            setHangman(new Hangman(answer));
            addModelAttributes(model);
        } catch (IllegalArgumentException e) {
            model.addAttribute("validationMessageList", createValidationList());
            view = NEW_GAME;
        }
        return view;
    }

    @RequestMapping("/guess-character")
    public String guessCharacter(@RequestParam("guessCharacter") String guessCharacter, Model model) {
        getHangman().guessLetter(guessCharacter);
        addModelAttributes(model);
        return PLAY_GAME;
    }

    @RequestMapping("/guess-answer")
    public String guessAnswer(@RequestParam Map<String, String> parameters, Model model) {
        StringBuilder guess = new StringBuilder();
        for (int i = 0; i < getHangman().getRevealedLetters().length(); i++) {
            String key = "revealedAnswerCharacter" + i;

            String guessedCharacter = parameters.get(key);
            if ("/".equals(guessedCharacter)) {
                guessedCharacter = " ";
            }
            guess.append(guessedCharacter);
        }
        getHangman().guessAnswer(guess.toString());

        addModelAttributes(model);

        return PLAY_GAME;
    }

    private void addModelAttributes(Model model) {
        model.addAttribute("revealedAnswerCharacters", getRevealedAnswerCharacters());
        model.addAttribute("guessCharacters", getGuessCharacters());
        model.addAttribute("hangman", getHangman());
        model.addAttribute("message", getGameMessage());
    }

    private String getGameMessage() {
        String message = "";
        if (getHangman().isGameWon()) {
            message = "You have won - congratulations!";
        } else if (getHangman().isGameLost()) {
            message = "You have lost - too bad.";
        }
        return message;
    }

    public List<GuessCharacter> getGuessCharacters() {
        List<GuessCharacter> guessCharacters = new ArrayList<>();
        for (char guessCharacter = 'A'; guessCharacter <= 'Z'; guessCharacter++) {
            String character = String.valueOf(guessCharacter);
            guessCharacters.add(new GuessCharacter(character, getGuessState(character)));
        }

        for (char guessCharacter = '0'; guessCharacter <= '9'; guessCharacter++) {
            String character = String.valueOf(guessCharacter);
            guessCharacters.add(new GuessCharacter(String.valueOf(guessCharacter), getGuessState(character)));
        }

        return guessCharacters;
    }

    private GuessState getGuessState(String character) {
        String lowerCaseCharacter = character.toLowerCase();

        GuessState guessState = GuessState.NOT_GUESSED;

        if (getHangman().getRevealedLetters().contains(lowerCaseCharacter)) {
            guessState = GuessState.CORRECTLY_GUESSED;
        } else if (getHangman().getIncorrectLetters().contains(lowerCaseCharacter)) {
            guessState = GuessState.INCORRECTLY_GUESSED;
        }
        return guessState;
    }

    public List<RevealedAnswerCharacter> getRevealedAnswerCharacters() {
        String revealedLetters;
        if (getHangman().isGameLost()) {
            revealedLetters = hangmanservice.getAnswer();
        } else {
            revealedLetters = getHangman().getRevealedLetters();
        }

        List<RevealedAnswerCharacter> revealedAnswerCharacters = new ArrayList<>();
        for (int i = 0; i < revealedLetters.length(); i++) {
            char revealedLetter = revealedLetters.charAt(i);
            RevealedAnswerCharacter revealedAnswerCharacter = new RevealedAnswerCharacter(revealedLetter);
            revealedAnswerCharacters.add(revealedAnswerCharacter);
        }
        return revealedAnswerCharacters;
    }

    private List<String> createValidationList() {
        return Arrays.asList("Invalid answer", "Answer must be alpha-numeric and may contain the following symbols:",
                "['-:,!;?]");
    }

    private Hangman getHangman() {
        return hangmanservice.getHangman();
    }

    private void setHangman(Hangman hangman) {
        hangmanservice.setHangman(hangman);
    }

}
