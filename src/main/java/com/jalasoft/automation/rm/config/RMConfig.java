package com.jalasoft.automation.rm.config;

import com.jalasoft.automation.core.config.AutomationAppSettings;
import com.jalasoft.automation.core.config.DefaultWebDriverConfig;
import com.jalasoft.automation.core.config.JsonConfigFileReader;
import com.jalasoft.automation.core.config.WebDriverConfig;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public class RMConfig implements AutomationAppSettings {
    private static final String configFilePath = System.getProperty("config.path");
    private JsonConfigFileReader jsonConfigFileReader;
    private WebDriverConfig webDriverConfig;
    private UserCredentialsConfig credentialsConfig;
    private static RMConfig instance = null;

    public RMConfig() {
        this.jsonConfigFileReader = new JsonConfigFileReader(configFilePath);
        this.webDriverConfig = new DefaultWebDriverConfig(jsonConfigFileReader);
        this.credentialsConfig = new UserCredentialsConfig(jsonConfigFileReader);
    }

    @Override
    public String getTableUrl() {
        return (jsonConfigFileReader.getConfigValue("GeneralSettings", "tableURL")).toString();
    }

    public static RMConfig getInstance() {
        if (instance == null) {
            instance = new RMConfig();
        }
        return instance;
    }

    public WebDriverConfig getWebDriverConfig() {
        return this.webDriverConfig;
    }
}