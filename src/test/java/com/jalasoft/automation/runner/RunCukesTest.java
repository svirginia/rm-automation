package com.jalasoft.automation.runner;

import com.jalasoft.automation.rm.RMAutomationApp;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.rmi.RemoteException;

/**
 * Created by virginia sanabria on 11/21/2016.
 */

@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
        glue = {"com.jalasoft.automation.steps", "com.jalasoft.automation.hooks"},
        features = {"src/test/resources/features"},
        monochrome = true)
public class RunCukesTest extends AbstractTestNGCucumberTests {
    private static Logger log = Logger.getLogger("RunCukesTest");

    @BeforeTest
    public void beforeAll() {
        try {
            RMAutomationApp.getInstance().startUp();
        } catch (Exception ex) {
            log.error("Exception executing before all", ex);
        }
    }

    @AfterTest
    public void afterAll() throws RemoteException {
        try {
            RMAutomationApp.getInstance().shutDown();
        } catch (Exception ex) {
            log.error("Exception executing after all", ex);
        }
    }
}