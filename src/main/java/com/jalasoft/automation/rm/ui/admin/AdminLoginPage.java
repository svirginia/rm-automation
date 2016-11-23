package com.jalasoft.automation.rm.ui.admin;

import com.jalasoft.automation.core.config.UserCredentials;
import com.jalasoft.automation.rm.RMAutomationApp;
import com.jalasoft.automation.rm.ui.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public class AdminLoginPage extends BasePage {
    @FindBy(id = "loginUsername")
    private WebElement usernameInput;

    @FindBy(id = "loginPassword")
    private WebElement passwordInput;

    @FindBy(xpath = "//span[text() = 'Sign In']")
    private WebElement signInLabel;

    @FindBy(xpath = "//button[text() = 'Sign In']")
    private WebElement signInButton;

    public AdminLoginPage() {
        this.waitForLoading();
    }

    @Override
    public boolean isOnPage() {
        return false;
    }

    @Override
    public void waitForLoading() {
        super.webDriverTools.waitUntilElementPresentAndVisible(this.signInButton);
    }

    public void login(String userName, String password) {
        super.webDriverTools.clearAndSendKeys(this.usernameInput, userName);
        super.webDriverTools.clearAndSendKeys(this.passwordInput, password);
        super.webDriverTools.clickOnElement(this.signInButton);
    }

    public void login(String credentialType) {
        UserCredentials credentials = RMAutomationApp.getInstance().getConfig().getCredentialsConfig().getCredentialsByType(credentialType);
        if (credentials != null) {
            this.login(credentials.userName, credentials.password);
        }
    }
}