package com.jalasoft.automation.rm.ui;

import com.jalasoft.automation.core.selenium.WebDriverManager;
import com.jalasoft.automation.core.selenium.WebDriverTools;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created by virginia sanabria on 11/21/2016.
 */
public abstract class RMUIElement {
    protected WebDriver webDriver;
    protected WebDriverWait webDriverWait;
    protected WebDriverTools webDriverTools;

    public RMUIElement() {
        this.webDriver = WebDriverManager.getInstance().getWebDriver();
        this.webDriverWait = WebDriverManager.getInstance().getWebDriverWait();
        this.webDriverTools = new WebDriverTools(this.webDriver, this.webDriverWait);
        PageFactory.initElements(this.webDriver, this); //initialize page factory elements
    }

    public abstract void waitForLoading();
}
