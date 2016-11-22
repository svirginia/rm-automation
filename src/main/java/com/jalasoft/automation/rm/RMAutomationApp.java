package com.jalasoft.automation.rm;

import com.jalasoft.automation.core.app.AutomationApp;
import com.jalasoft.automation.core.selenium.WebDriverManager;
import com.jalasoft.automation.core.test.setup.TestSetupManager;
import com.jalasoft.automation.rm.config.RMConfig;
import com.jalasoft.automation.rm.ui.PageTransporter;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by virginia sanabria on 11/21/2016.
 */
public class RMAutomationApp implements AutomationApp {
    private static AutomationApp instance = null;
    private Logger log = Logger.getLogger(getClass());
    private RMConfig config;
    private TestSetupManager setupManager;
//    private boolean initialized; //TODO why

    private RMAutomationApp() {
//        this.initialized = false; //TODO why
        this.initialize();
    }

    private void initialize() {
        PropertyConfigurator.configure("log.properties"); //TODO review the file
        this.config = RMConfig.getInstance(); //TODO implement
        this.setupManager = TestSetupManager.getInstance(); //TODO implement
        PageTransporter.baseTabletURL = this.config.getTableUrl(); //TODO implement
    }

    public static AutomationApp getInstance() {
        if (instance == null) {
            instance = new RMAutomationApp();
        }
        return instance;
    }

    @Override
    public void startUp() throws Exception {
        WebDriverManager.getInstance().initializeWebDriver(this.config.getWebDriverConfig());
//        this.initialized = true;
    }

    @Override
    public void shutDown() throws Exception {
        this.setupManager.finalizeExecution();
        WebDriverManager.getInstance().quitDriver();
//        this.initialized = false;
    }
}