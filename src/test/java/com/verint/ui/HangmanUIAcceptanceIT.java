package com.verint.ui;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", tags = "@UI", glue = "com.verint.ui", strict = true)
public class HangmanUIAcceptanceIT {
    // This is a runner for Cucumber acceptance tests
}
