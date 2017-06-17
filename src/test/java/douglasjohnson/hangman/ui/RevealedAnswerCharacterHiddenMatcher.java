package douglasjohnson.hangman.ui;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

class RevealedAnswerCharacterHiddenMatcher extends BaseMatcher<WebElement> {
    @Override
    public boolean matches(Object item) {
        WebElement webElement = (WebElement) item;
        return webElement.getAttribute("value").isEmpty();
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        WebElement webElement = (WebElement) item;
        description.appendText("was ").appendValue(webElement.getAttribute("value"));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("input should be empty");
    }

    public static Matcher<WebElement> hidden() {
        return new RevealedAnswerCharacterHiddenMatcher();
    }
}