package com.jalasoft.automation.core.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by virginia sanabria on 11/21/2016.
 */
public class WebDriverManager {
    private static WebDriverManager instance = null;
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    public static WebDriverManager getInstance() {
        if (instance == null || instance.webDriver == null) { //TODO why the or
            instance = new WebDriverManager();
        }
        return instance;
    }

    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    public WebDriverWait getWebDriverWait() {
        return this.webDriverWait;
    }
}
