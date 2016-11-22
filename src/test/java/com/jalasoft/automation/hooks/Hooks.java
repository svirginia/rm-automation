package com.jalasoft.automation.hooks;

import com.jalasoft.automation.core.selenium.WebDriverManager;
import com.jalasoft.automation.core.test.setup.TestSetupManager;
import cucumber.api.Scenario;
import cucumber.api.java.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public class Hooks {

    private Logger log = Logger.getLogger(getClass());

    @Before
    public void BeforeScenario(Scenario scenario) {
        log.info(String.format("Before: %s", scenario.getId()));
        TestSetupManager.getInstance().processBeforeCucumberScenario(scenario);
    }

    @After
    public void AfterScenario(Scenario scenario) {
        log.info(String.format("After: %s", scenario.getId()));
        this.takeAndEmbedScreenshot(scenario);
        TestSetupManager.getInstance().processAfterCucumberScenario(scenario);
    }

    /**
     * Take the screenshot and embed into the failure scenario
     *
     * @param scenario
     */
    private void takeAndEmbedScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            this.log.info("Taking and embedding screenshot to scenario: " + scenario.getName());
            try {
                byte[] screenshot = ((TakesScreenshot) WebDriverManager.getInstance().getWebDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
                scenario.write("URL at failure: " + WebDriverManager.getInstance().getWebDriver().getCurrentUrl());
            } catch (WebDriverException e) {
                this.log.error("Failed to take and embed screenshot to failed scenario: " + scenario.getName());
            }
        }
    }
}