package com.jalasoft.automation.rm.ui;

import com.jalasoft.automation.core.selenium.WebDriverManager;
import com.jalasoft.automation.rm.RMAutomationApp;
import com.jalasoft.automation.rm.ui.admin.AdminLoginPage;
import org.openqa.selenium.WebDriver;


/**
 * Created by virginia sanabria on 11/21/2016.
 */
public class PageTransporter {
    private static PageTransporter instance;
    public static String baseTabletURL;
    public static String adminURL;
    private WebDriver webDriver;

    private PageTransporter() {
        RMAutomationApp.setupFramework();
        baseTabletURL = RMAutomationApp.getInstance().getConfig().getTableUrl();
        adminURL = RMAutomationApp.getInstance().getConfig().getAdminUrl();
        this.webDriver = WebDriverManager.getInstance().getWebDriver();
    }

    public static PageTransporter getInstance() {
        if (instance == null) {
            instance = new PageTransporter();
        }
        return instance;
    }

    public AdminLoginPage navigateToAdminLoginPage() {
        this.navigateToUrl(getAdminLoginPageUrl());
        return new AdminLoginPage();
    }

    private void navigateToUrl(String url) {
        this.webDriver.navigate().to(url);
    }

    public String getAdminLoginPageUrl() {
        return String.format("%s/%s", this.adminURL, "#/login");
    }

    public String getCurrentUrl() {
        return this.webDriver.getCurrentUrl();
    }

    public String getAdminBaseUrl() {
        return adminURL;
    }

    public String getEmailServersPageUrl() {
        return String.format("%s/%s", this.adminURL, "#/admin/servers");
    }
}