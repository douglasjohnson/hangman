package douglasjohnson.hangman.engine;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Set;

import douglasjohnson.hangman.Hangman;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HangmanStepDefinitions {

    private Hangman hangman;
    private String answer;

    @Given("^I start a game of hangman with answer \"([^\"]*)\"$")
    public void startGameOfHangman(String answer) throws Throwable {
        this.answer = answer;
        hangman = new Hangman(answer);
    }

    @When("^I guess character \"([^\"]*)\"$")
    public void guessCharacter(String character) throws Throwable {
        hangman.guessLetter(character);
    }

    @When("^I guess the answer \"([^\"]*)\"$")
    public void guessAnswer(String guessedAnswer) throws Throwable {
        hangman.guessAnswer(guessedAnswer);
    }

    @When("^I win the game$")
    public void winGame() throws Throwable {
        hangman.guessAnswer(answer);
    }

    @When("^I lose the game$")
    public void loseGame() throws Throwable {
        String wrongAnswer = answer + "z";
        while (hangman.isGameInProgress()) {
            hangman.guessAnswer(wrongAnswer);
        }
    }

    @Then("^characters revealed are \"([^\"]*)\"$")
    public void verifyCharactersRevealed(String expectedRevealedCharacters) throws Throwable {
        String revealedLetters = hangman.getRevealedLetters();
        assertThat(revealedLetters, is(expectedRevealedCharacters));
    }

    @Then("^\"([^\"]*)\" lives are lost$")
    public void verifyNumberOfLivesLost(String expectedLivesLost) throws Throwable {
        int livesLost = hangman.getLivesLost();
        assertThat(livesLost, is(Integer.valueOf(expectedLivesLost)));
    }

    @Then("^incorrect characters guessed are \"([^\"]*)\"$")
    public void verifyIncorrectCharactersGuessed(String expectedIncorrectCharacters) throws Throwable {
        Set<String> incorrectLetters = hangman.getIncorrectLetters();

        if (expectedIncorrectCharacters.isEmpty()) {
            assertThat(incorrectLetters, is(empty()));
        } else {
            String[] expectedIncorrectLettersArray = expectedIncorrectCharacters.split(",");

            assertThat(incorrectLetters, contains(expectedIncorrectLettersArray));
        }
    }

    @Then("^game is in progress$")
    public void verifyGameIsInProgress() throws Throwable {
        assertThat(hangman.isGameInProgress(), is(true));
        assertThat(hangman.isGameWon(), is(false));
        assertThat(hangman.isGameLost(), is(false));
    }

    @Then("^game is won$")
    public void verifyGameIsWon() throws Throwable {
        assertThat(hangman.isGameInProgress(), is(false));
        assertThat(hangman.isGameWon(), is(true));
        assertThat(hangman.isGameLost(), is(false));
    }

    @Then("^game is lost$")
    public void verifyGameIsLost() throws Throwable {
        assertThat(hangman.isGameInProgress(), is(false));
        assertThat(hangman.isGameWon(), is(false));
        assertThat(hangman.isGameLost(), is(true));
    }

    @Then("^I cannot start a game of hangman with answer \"([^\"]*)\"$")
    public void cannotStartGame(String answer) {
        try {
            new Hangman(answer);
            fail("Should not be able to start a game with answer [" + answer + "]");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Cannot start a game with answer " + answer));
        }
    }
}
