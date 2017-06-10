package com.verint.ui;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HangmanUIStepDefinitions extends AbstractStepDefinitions {

    private static final String INSTRUCTIONS_LABEL_ID = "lblInstructions";
    private static final String HOST = System.getProperty("host", "localhost");
    private static final String PORT = System.getProperty("port", "8080");

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new PhantomJSDriver();
    }

    @After
    public void cleanUp() {
        driver.quit();
    }

    @When("^I navigate to Hangman game$")
    public void navigateToHangmanGame() {
        navigateToHangmanGame(driver);
    }

    public void navigateToHangmanGame(WebDriver webDriver) {
        webDriver.get("http://" + HOST + ":" + PORT + "/Hangman");
    }

    @When("^I tab$")
    public void tab() throws Throwable {
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
    }

    @Then("^the \"([^\"]*)\" page is displayed$")
    public void pageIsDisplayed(String pageTitle) {
        assertThat(driver.getTitle(), is(pageTitle));
    }

    @Then("^page contains instructions$")
    public void pageContainsInstructions() throws Throwable {
        driver.findElement(By.id(INSTRUCTIONS_LABEL_ID));
    }

    @Then("^instruction text is \"([^\"]*)\"$")
    public void instructionTextIs(String instructionText) throws Throwable {
        WebElement instructionsElement = driver.findElement(By.id(INSTRUCTIONS_LABEL_ID));
        assertThat(instructionsElement.getText(), is(instructionText));
    }

    @Then("^the validation message displayed is:$")
    public void validationMessageDisplayedIs(List<String> validationMessageList) {
        WebElement validationMessageElement = driver.findElement(By.id("lblValidationMessage"));
        List<WebElement> messageList = validationMessageElement.findElements(By.tagName("li"));
        assertThat(messageList.size(), is(validationMessageList.size()));
        for (int i = 0; i < messageList.size(); i++) {
            WebElement message = messageList.get(i);
            assertThat(message.getText(), is(validationMessageList.get(i)));
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

}
