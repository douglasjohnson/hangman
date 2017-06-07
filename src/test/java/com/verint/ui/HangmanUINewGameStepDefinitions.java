package com.verint.ui;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HangmanUINewGameStepDefinitions {

    private static final String ANSWER_BUTTON_ID = "btnAnswer";
    private static final String ANSWER_INPUT_ID = "txtAnswerInput";

    private WebDriver driver;

    @Autowired
    public HangmanUINewGameStepDefinitions(HangmanUIStepDefinitions sharedStepDefinitions) {
        this.driver = sharedStepDefinitions.getDriver();
    }

    @When("^I enter answer \"([^\"]*)\"$")
    public void enterAnswer(String answer) throws Throwable {
        driver.findElement(By.id(ANSWER_INPUT_ID)).sendKeys(answer);
    }

    @When("^I click the answer button$")
    public void clickAnswerButton() throws Throwable {
        driver.findElement(By.id(ANSWER_BUTTON_ID)).click();
    }

    @When("^I submit answer \"([^\"]*)\"$")
    public void submitAnswer(String answer) {
        submitAnswer(driver, answer);
    }

    public void submitAnswer(WebDriver webDriver, String answer) {
        WebElement answerInput = webDriver.findElement(By.id(ANSWER_INPUT_ID));
        answerInput.sendKeys(answer);
        answerInput.submit();
    }

    @Then("^focus is on answer button$")
    public void focusIsOnAnswerButton() throws Throwable {
        assertThat(driver.switchTo().activeElement(), is(driver.findElement(By.id(ANSWER_BUTTON_ID))));
    }

    @Then("^page contains answer input$")
    public void pageContainsAnswerInput() throws Throwable {
        driver.findElement(By.id(ANSWER_INPUT_ID));
    }

    @Then("^answer button label is \"([^\"]*)\"$")
    public void answerButtonLabelIs(String answerButtonLabel) throws Throwable {
        WebElement answerButtonElement = driver.findElement(By.id(ANSWER_BUTTON_ID));
        assertThat(answerButtonElement.getAttribute("value"), is(answerButtonLabel));
    }

    @Then("^answer input text is \"([^\"]*)\"$")
    public void answerInputTextIs(String text) throws Throwable {
        assertThat(driver.findElement(By.id(ANSWER_INPUT_ID)).getAttribute("value"), is(text));
    }

    @Then("^focus is on answer input$")
    public void focusIsOnAnswerInput() throws Throwable {
        assertThat(driver.switchTo().activeElement(), is(driver.findElement(By.id(ANSWER_INPUT_ID))));
    }
}
