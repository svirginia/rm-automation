package com.jalasoft.automation.core.config;

import java.util.Map;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public interface WebDriverConfig {
    String getDriverType();

    String getBrowser();

    long getImplicitWaitTime();

    long getExplicitWaitTime();

    long getWaitSleepTime();

    Map<String, String> getDesiredCapabilities();

    Map<String, String> getRemoteDriverSettings();
}