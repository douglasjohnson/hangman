package com.verint.ui;

import static com.verint.ui.RevealedAnswerCharacterHiddenMatcher.hidden;
import static com.verint.ui.WebElementReadOnlyAttributeMatcher.readonly;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HangmanUIPlayGameStepDefinitions extends AbstractStepDefinitions {

    private static final String GUESS_CHARACTERS_CONTAINER_ID = "guessCharacters";
    private static final String GALLOWS_CONTAINER_ID = "gallows";
    private static final int NUMBER_OF_LIVES = 6;
    private static final String GUESS_ANSWER_BUTTON_ID = "btnGuessAnswer";
    private static final String PLAY_AGAIN_BUTTON_ID = "btnPlayAgain";
    private static final String REVEALED_ANSWER_CHARACTERS_CONTAINER_ID = "revealedAnswer";
    private static final String MESSAGE_ID = "message";
    private static final String GAME_LOST_MESSAGE = "You have lost - too bad.";
    private static final String GAME_WON_MESSAGE = "You have won - congratulations!";

    private HangmanUIStepDefinitions sharedStepDefinitions;
    private HangmanUINewGameStepDefinitions newGameStepDefinitions;

    private WebDriver driver;

    private String answer;

    @Autowired
    public HangmanUIPlayGameStepDefinitions(HangmanUIStepDefinitions sharedStepDefinitions,
            HangmanUINewGameStepDefinitions newGameStepDefinitions) {
        this.sharedStepDefinitions = sharedStepDefinitions;
        this.newGameStepDefinitions = newGameStepDefinitions;
        this.driver = sharedStepDefinitions.getDriver();
    }

    @When("^I start a game of hangman with answer \"([^\"]*)\"$")
    public void startGameOfHangman(String answer) throws Throwable {
        sharedStepDefinitions.navigateToHangmanGame(driver);
        newGameStepDefinitions.submitAnswer(driver, answer);
        this.answer = answer;
    }

    @When("^a second game of hangman is started with answer \"([^\"]*)\"$")
    public void secondGameStartedWithAnswer(String answer) {
        PhantomJSDriver driver2 = new PhantomJSDriver();
        sharedStepDefinitions.navigateToHangmanGame(driver2);
        newGameStepDefinitions.submitAnswer(driver2, answer);
        driver2.quit();
    }

    @Then("^guess characters are \"([^\"]*)\"$")
    public void guessCharactersAre(String guessCharacters) throws Throwable {
        WebElement guessCharacterDiv = driver.findElement(By.id(GUESS_CHARACTERS_CONTAINER_ID));
        List<WebElement> guessCharacterButtonList = guessCharacterDiv.findElements(By.tagName("input"));
        assertThat(guessCharacterButtonList.size(), is(guessCharacters.length()));
        for (int i = 0; i < guessCharacterButtonList.size(); i++) {
            WebElement guessCharacterButton = guessCharacterButtonList.get(i);
            assertThat(guessCharacterButton.getAttribute("value"), is(String.valueOf(guessCharacters.charAt(i))));
        }
    }

    @Then("^all guess characters are enabled$")
    public void allGuessCharactersEnabled() throws Throwable {
        WebElement guessCharacterDiv = driver.findElement(By.id(GUESS_CHARACTERS_CONTAINER_ID));
        List<WebElement> guessCharacterButtonList = guessCharacterDiv.findElements(By.tagName("input"));
        for (int i = 0; i < guessCharacterButtonList.size(); i++) {
            WebElement guessCharacterButton = guessCharacterButtonList.get(i);
            assertThat(guessCharacterButton.isEnabled(), is(true));
        }
    }

    @Then("^all guess characters are disabled$")
    public void allGuessCharactersDisabled() throws Throwable {
        WebElement guessCharacterDiv = driver.findElement(By.id(GUESS_CHARACTERS_CONTAINER_ID));
        List<WebElement> guessCharacterButtonList = guessCharacterDiv.findElements(By.tagName("input"));
        for (int i = 0; i < guessCharacterButtonList.size(); i++) {
            WebElement guessCharacterButton = guessCharacterButtonList.get(i);
            assertThat(guessCharacterButton.isEnabled(), is(false));
        }
    }

    @Then("^the guess answer button is displayed$")
    public void guessAnswerButtonIsDisplayed() throws Throwable {
        checkButtonDisplayed(GUESS_ANSWER_BUTTON_ID, "Guess Answer");
    }

    @Then("^the play again button is displayed$")
    public void playAgainButtonIsDisplayed() throws Throwable {
        checkButtonDisplayed(PLAY_AGAIN_BUTTON_ID, "Play Again");
    }

    private void checkButtonDisplayed(String buttonId, String buttonText) {
        WebElement guessAnswerButton = driver.findElement(By.id(buttonId));
        assertThat(guessAnswerButton.getAttribute("value"), is(buttonText));
    }

    @Then("^the guess answer button is disabled$")
    public void guessAnswerButtonIsDisabled() {
        WebElement guessAnswerButton = driver.findElement(By.id(GUESS_ANSWER_BUTTON_ID));
        assertThat(guessAnswerButton.isEnabled(), is(false));
    }

    @Then("^all of the revealed answer characters are editable$")
    public void allRevealedAnswerCharactersEditable() throws Throwable {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        assertThat(revealedAnswerCharacters.size(), is(answer.length()));
        for (WebElement revealedAnswerCharacter : revealedAnswerCharacters) {
            assertThat(revealedAnswerCharacter.isEnabled(), is(true));
        }
    }

    @Then("^all of the revealed answer characters are hidden$")
    public void allRevealedAnswerCharactersHidden() throws Throwable {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        assertThat(revealedAnswerCharacters, are(all(hidden())));
    }

    @When("^I guess character \"([^\"]*)\"$")
    public void guessCharacter(String guessCharacter) throws Throwable {
        driver.findElement(By.id("guessCharacter" + guessCharacter.toUpperCase())).click();
    }

    @Then("^the revealed answer characters at positions \"([^\"]*)\" are editable$")
    public void revealedAnswerCharactersAtPositionsEditable(String positionsString) throws Throwable {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        assertThat(revealedAnswerCharacters, atPositions(positionsString, are(not(readonly()))));
    }

    private Matcher<List<WebElement>> atPositions(final String positionsString, final Matcher<WebElement> matcher) {
        return new BaseMatcher<List<WebElement>>() {

            @Override
            public boolean matches(Object item) {
                @SuppressWarnings("unchecked")
                List<WebElement> revealedAnswerCharacters = (List<WebElement>) item;
                String[] positions = positionsString.split(",");
                for (String position : positions) {
                    WebElement revealedAnswerCharacter = revealedAnswerCharacters
                            .get(Integer.parseInt(position, 10) - 1);
                    assertThat(revealedAnswerCharacter, matcher);
                }

                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("characters in positions ").appendValue(positionsString);
            }
        };
    }

    private Matcher<List<WebElement>> all(final Matcher<WebElement> matcher) {
        return new BaseMatcher<List<WebElement>>() {

            @Override
            public boolean matches(Object item) {
                @SuppressWarnings("unchecked")
                List<WebElement> revealedAnswerCharacters = (List<WebElement>) item;
                for (WebElement revealedAnswerCharacter : revealedAnswerCharacters) {
                    assertThat(revealedAnswerCharacter, matcher);
                }

                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("all revealed answer characters ");
            }
        };
    }

    @Then("^the revealed answer characters at positions \"([^\"]*)\" are hidden$")
    public void revealedAnswerCharactersAtPositionsAreHidden(String positionsString) throws Throwable {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        assertThat(revealedAnswerCharacters, atPositions(positionsString, are(hidden())));
    }

    public static <T> Matcher<T> are(Matcher<T> value) {
        return is(value);
    }

    @Then("^the revealed answer characters at positions \"([^\"]*)\" are non-editable$")
    public void revealedAnswerCharactersAtPositionsNonEditable(String positionsString) throws Throwable {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        assertThat(revealedAnswerCharacters, atPositions(positionsString, are(readonly())));
    }

    @Then("^the revealed answer characters at positions \"([^\"]*)\" are displayed$")
    public void revealedAnswerCharactersAtPositionsAreDisplayed(String positionsString) throws Throwable {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        assertThat(revealedAnswerCharacters.size(), is(answer.length()));
        String[] positions = positionsString.split(",");
        for (String position : positions) {
            int index = Integer.parseInt(position, 10) - 1;
            WebElement revealedAnswerCharacter = revealedAnswerCharacters.get(index);
            assertThat(revealedAnswerCharacter.getAttribute("value"), is(String.valueOf(answer.charAt(index))));
        }
    }

    @Then("^guess character \"([^\"]*)\" is disabled$")
    public void guessedCharacterIsDisabled(String character) {
        WebElement characterButton = driver.findElement(By.id("guessCharacter" + character));
        assertThat(characterButton.isEnabled(), is(false));
    }

    @Then("^guess character \"([^\"]*)\" is strike-through$")
    public void guessCharacterIsStrikeThrough(String character) {
        WebElement characterButton = driver.findElement(By.id("guessCharacter" + character));
        guessCharacterIsStrikeThrough(characterButton);
    }

    private void guessCharacterIsStrikeThrough(WebElement characterButton) {
        assertThat(characterButton.getCssValue("text-decoration"), is("line-through"));
    }

    @Then("^guess character \"([^\"]*)\" is not strike-through$")
    public void guessCharacterIsNotStrikeThrough(String character) {
        WebElement characterButton = driver.findElement(By.id("guessCharacter" + character));
        guessCharacterIsNotStrikeThrough(characterButton);
    }

    private void guessCharacterIsNotStrikeThrough(WebElement characterButton) {
        assertThat(characterButton.getCssValue("text-decoration"), is("none"));
    }

    @Then("^all guess characters except \"([^\"]*)\" are enabled$")
    public void allGuessCharactersExceptGivenAreEnabled(String given) {
        WebElement guessCharacterDiv = driver.findElement(By.id(GUESS_CHARACTERS_CONTAINER_ID));
        List<WebElement> guessCharacterButtonList = guessCharacterDiv.findElements(By.tagName("input"));
        for (int i = 0; i < guessCharacterButtonList.size(); i++) {
            WebElement guessCharacterButton = guessCharacterButtonList.get(i);
            if (guessCharacterButton.getAttribute("id").equals("guessCharacter" + given)) {
                assertThat(guessCharacterButton.isEnabled(), is(false));
            } else {
                assertThat(guessCharacterButton.isEnabled(), is(true));
            }
        }
    }

    @Given("^I enter \"([^\"]*)\" in revealed answer at position \"([^\"]*)\"$")
    public void enterRevealedAnswerCharacter(String character, String position) throws Throwable {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        WebElement revealedAnswerCharacterElement = revealedAnswerCharacters.get(Integer.valueOf(position) - 1);
        revealedAnswerCharacterElement.sendKeys(character);
    }

    @Then("^all revealed answer characters are editable$")
    public void allRevealedAnswerCharactersAreEditable() throws Throwable {
        allRevealedAnswerCharactersEditable();
    }

    @Then("^all revealed answer characters are hidden$")
    public void allRevealedAnswerCharactersAreHidden() throws Throwable {
        allRevealedAnswerCharactersHidden();
    }

    @When("^I win the game$")
    public void winTheGame() throws Throwable {
        guessAnswer(answer);
    }

    @When("^I lose the game$")
    public void loseTheGame() throws Throwable {
        String incorrectAnswer = String.valueOf((char) (answer.charAt(0) + 1)) + answer.substring(1);
        for (int i = 0; i < NUMBER_OF_LIVES; i++) {
            guessAnswer(incorrectAnswer);
        }
    }

    private void guessAnswer(String answer) {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));

        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) != ' ') {
                WebElement revealedAnswerCharacterElement = revealedAnswerCharacters.get(i);
                revealedAnswerCharacterElement.sendKeys(String.valueOf(answer.charAt(i)));
            }
        }

        pressGuessAnswerButton();
    }

    @Then("^the message displayed has text \"([^\"]*)\"$")
    public void messageDisplayedHasText(String messageText) {
        checkMessageText(messageText);
    }

    private void checkMessageText(String messageText) {
        WebElement gameMessageElement = driver.findElement(By.id(MESSAGE_ID));
        assertThat(gameMessageElement.getText(), is(messageText));
    }

    @Then("^all revealed answer characters are non-editable$")
    public void allRevealedAnswerCharactersNonEditable() {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        assertThat(revealedAnswerCharacters, are(all(readonly())));
    }

    @Then("^all revealed answer characters are displayed$")
    public void allRevealedAnswerCharactersAreDisplayed() throws Throwable {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        assertThat(revealedAnswerCharacters.size(), is(answer.length()));
        for (int i = 0; i < revealedAnswerCharacters.size(); ++i) {
            WebElement revealedAnswerCharacter = revealedAnswerCharacters.get(i);
            assertThat(revealedAnswerCharacter.getAttribute("value"), is(String.valueOf(answer.charAt(i))));
        }
    }

    @Then("^no message is displayed$")
    public void noMessageIsDisplayed() {
        checkMessageText("");
    }

    @When("^I click play again$")
    public void clickPlayAgain() {
        driver.findElement(By.id(PLAY_AGAIN_BUTTON_ID)).click();
    }

    @Then("^dialog is displayed with text \"([^\"]*)\"$")
    public void dialogIsDisplayedWithText(String text) throws Throwable {
        WebElement dialogElement = driver.findElement(By.className("ui-dialog"));
        WebElement dialogMessageElement = dialogElement.findElement(By.id("dialog-confirm"));

        String dialogText = dialogMessageElement.getText();
        assertThat(dialogText, is(text));

        assertThat(dialogElement.getCssValue("display"), is(not("none")));
    }

    @Then("^dialog has \"([^\"]*)\" button$")
    public void dialogHasButton(String buttonText) throws Throwable {
        WebElement button = findDialogButton(buttonText);
        assertThat(button, is(notNullValue()));
    }

    private WebElement findDialogButton(String buttonText) {
        WebElement dialogElement = driver.findElement(By.className("ui-dialog"));
        List<WebElement> buttons = dialogElement.findElements(By.tagName("button"));

        WebElement expectedButton = null;
        for (WebElement button : buttons) {
            if (buttonText.equals(button.getText())) {
                expectedButton = button;
                break;
            }
        }
        return expectedButton;
    }

    @When("^I click \"([^\"]*)\" on the are you sure dialog$")
    public void clickButtonOnDialog(String buttonText) {
        findDialogButton(buttonText).click();
    }

    @Then("^the are you sure dialog is closed$")
    public void areYouSureDialogIsClosed() {
        WebElement dialogElement = driver.findElement(By.className("ui-dialog"));

        assertThat(dialogElement.getCssValue("display"), is("none"));
    }

    @When("^I press the guess answer button$")
    public void pressGuessAnswerButton() {
        driver.findElement(By.id(GUESS_ANSWER_BUTTON_ID)).click();
    }

    @Then("^the revealed answer character at position \"([^\"]*)\" is \"([^\"]*)\"$")
    public void checkRevealedAnswerCharacter(String position, String character) throws Throwable {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        WebElement revealedAnswerCharacter = revealedAnswerCharacters.get(Integer.valueOf(position) - 1);
        assertThat(revealedAnswerCharacter.getAttribute("value"), is(character));
    }

    @Then("^characters revealed are \"([^\"]*)\"$")
    public void charactersRevealedAre(String characters) {
        WebElement revealedAnswerCharactersContainer = driver
                .findElement(By.id(REVEALED_ANSWER_CHARACTERS_CONTAINER_ID));
        List<WebElement> revealedAnswerCharacters = revealedAnswerCharactersContainer.findElements(By.tagName("input"));
        for (int i = 0; i < characters.length(); ++i) {
            char character = characters.charAt(i);
            WebElement revealedAnswerCharacter = revealedAnswerCharacters.get(i);
            if (character == '*') {
                assertThat(revealedAnswerCharacter.getAttribute("value"), is(""));
            } else {
                assertThat(revealedAnswerCharacter.getAttribute("value"), is(String.valueOf(character)));
            }
        }
    }

    @Then("^\"([^\"]*)\" lives are lost$")
    public void checkLivesLost(String lives) {
        WebElement gallowsContainer = driver.findElement(By.id(GALLOWS_CONTAINER_ID));
        assertThat(gallowsContainer.getText(), is(getAsciiArt(lives)));
    }

    //@formatter:off
    String[] asciiArtLives = {
"  ______" + "\n" + 
" |      |" + "\n" + 
" |" + "\n" + 
" |" + "\n" + 
" |" + "\n" + 
"/_\\",
"  ______" + "\n" + 
" |      |" + "\n" + 
" |      O" + "\n" + 
" |" + "\n" + 
" |" + "\n" + 
"/_\\",
"  ______" + "\n" + 
" |      |" + "\n" + 
" |      O" + "\n" + 
" |      |" + "\n" + 
" |" + "\n" + 
"/_\\",
"  ______" + "\n" + 
" |      |" + "\n" + 
" |      O" + "\n" + 
" |     /|" + "\n" + 
" |" + "\n" + 
"/_\\",
"  ______" + "\n" + 
" |      |"   + "\n" + 
" |      O"   + "\n" + 
" |     /|\\" + "\n" + 
" |"          + "\n" + 
"/_\\",
"  ______" + "\n" + 
" |      |" + "\n" + 
" |      O" + "\n" + 
" |     /|\\" + "\n" + 
" |     /" + "\n" + 
"/_\\",
"  ______" + "\n" + 
" |      |" + "\n" + 
" |      O" + "\n" + 
" |     /|\\" + "" + "\n" + 
" |     / \\" + "\n" + 
"/_\\"
};
    //@formatter:on

    private String getAsciiArt(String lives) {
        return asciiArtLives[Integer.valueOf(lives)];
    }

    @Then("^incorrect characters guessed are \"([^\"]*)\"$")
    public void checkIncorrectlyGuessedCharacters(String characters) {
        WebElement guessCharacterDiv = driver.findElement(By.id(GUESS_CHARACTERS_CONTAINER_ID));
        List<WebElement> guessCharacterButtonList = guessCharacterDiv.findElements(By.tagName("input"));
        for (int i = 0; i < guessCharacterButtonList.size(); i++) {
            WebElement guessCharacterButton = guessCharacterButtonList.get(i);
            String guessCharacter = guessCharacterButton.getAttribute("value");
            if (characters.toUpperCase().contains(guessCharacter)) {
                guessCharacterIsStrikeThrough(guessCharacterButton);
            } else {
                guessCharacterIsNotStrikeThrough(guessCharacterButton);
            }
        }
    }

    @Then("^game is in progress$")
    public void gameInProgress() {
        assertThat(driver.findElement(By.id(GUESS_ANSWER_BUTTON_ID)).isEnabled(), is(true));
    }

    @Then("^game is lost$")
    public void gameIsLost() {
        checkMessageText(GAME_LOST_MESSAGE);
    }

    @Then("^game is won$")
    public void gameIsWon() {
        checkMessageText(GAME_WON_MESSAGE);
    }

    @When("^I guess the answer \"([^\"]*)\"$")
    public void guessTheAnswer(String answer) {
        guessAnswer(answer);
    }
}
