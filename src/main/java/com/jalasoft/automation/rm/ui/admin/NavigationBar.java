package com.jalasoft.automation.rm.ui.admin;

import com.jalasoft.automation.rm.ui.RMUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public class NavigationBar extends RMUIElement {
    private By rootElement = By.cssSelector("ul[class *= 'nav-stacked']");

    @FindBy(xpath = "//ul[contains(@class, 'nav-stacked')]//a[text() = 'Email Servers']") //TODO improve map
    private WebElement emailServersMenu;

    @FindBy(xpath = "//ul[contains(@class, 'nav-stacked')]//a[text() = 'Locations']")//TODO improve map
    private WebElement locationsMenu;

    public NavigationBar() {
        this.waitForLoading();
    }

    @Override
    public void waitForLoading() {
        super.webDriverTools.waitUntilElementPresentAndVisible(this.rootElement);
    }

    public void clickEmailServersMenu() {
        super.webDriverTools.clickOnElement(this.emailServersMenu);
    }

    public void clickLocationsMenu() {
        super.webDriverTools.clickOnElement(this.locationsMenu);
    }
}
