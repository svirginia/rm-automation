package com.jalasoft.automation.core.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by virginia sanabria on 11/21/2016.
 */
public class WebDriverTools {
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    public WebDriverTools(WebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
    }
}
