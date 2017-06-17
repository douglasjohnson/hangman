package douglasjohnson.hangman.ui;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class WebElementReadOnlyAttributeMatcher extends BaseMatcher<WebElement> {

    public static WebElementReadOnlyAttributeMatcher readonly() {
        return new WebElementReadOnlyAttributeMatcher();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("'readonly' attribute should be present");
    }

    @Override
    public boolean matches(Object item) {
        WebElement webElement = (WebElement) item;
        return webElement.getAttribute("readonly") != null;
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("was not found");
    }
}
