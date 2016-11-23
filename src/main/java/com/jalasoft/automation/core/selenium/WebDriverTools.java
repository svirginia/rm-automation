package com.jalasoft.automation.core.selenium;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by virginia sanabria on 11/21/2016.
 */
public class WebDriverTools {
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private Logger log = Logger.getLogger(getClass());

    public WebDriverTools(WebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
    }

    /**
     * Wait until an element is presented DOM and it is visible
     * @param webElement
     */
    public void waitUntilElementPresentAndVisible(WebElement webElement) {
        try {
            this.webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        } catch (WebDriverException e) {
            log.error(String.format("Element is not present and visible: %s", e.getMessage()));
            throw new WebDriverException("Element is not present and visible: ", e.getCause());
        }
    }

    /**
     * Clear and send keys
     * @param webElement
     * @param text
     * */
    public void clearAndSendKeys(WebElement webElement, String text) {
        webElement.clear();
        webElement.sendKeys(text);
    }

    /**
     * Click on a web element
     * @param webElement
     */
    public void clickOnElement(WebElement webElement) {
        try {
            webElement.click();
            log.info(String.format("The click action was performed on web element: %s", webElement));
        } catch (WebDriverException e) {
            log.error(String.format("Error when trying to click on web element: %s, error message: %s", webElement, e.getCause()));
            throw new WebDriverException(String.format("Error when trying to click on web element: %s, error message: %s", webElement, e.getCause()));
        }
    }

    public WebElement waitUntilElementPresentAndVisible(By locator) {
        try {
            return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (WebDriverException e) {
            this.log.error(String.format("Element is not present and visible: %s, %s", locator, e.getMessage()));
            throw new WebDriverException(String.format("Element is not present and visible: %s, %s", locator, e.getMessage()));
        }
    }
}